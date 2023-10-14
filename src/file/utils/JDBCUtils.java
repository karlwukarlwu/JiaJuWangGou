package file.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 这个是针对事务的优化
 * Karl Rules!
 * 2023/9/5
 * now File Encoding is UTF-8
 */
public class JDBCUtils {
    private static DataSource ds;
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();
    static{
        Properties properties = new Properties();
        try{
            //这一行就是区别于JavaSE的写法
            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
//    public static Connection getConnection(){
//        try {
//            return ds.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    从ThreadLocal中获取连接
//    一个thread local只能对一个连接 你要多个连接自己造多个thread local去
    public static Connection getConnection(){
//        从threadlocal中拿链接
        Connection con = threadLocalConn.get();
        if(con==null){
//            第一次调用 没有连接 造一个
            try {
                con = ds.getConnection();
//                这里需要设置手动提交
//                当设置了手动提交 设置回滚rollback和提交commit
                con.setAutoCommit(false);
//                以后就有了

            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocalConn.set(con);
        }
        return con;
    }
//    提交事务
    public static void commitAndClose(){
        Connection con = threadLocalConn.get();
        if(con!=null){//确保该连接非空
            try {
//                提交事务
                con.commit();

            } catch (SQLException e) {
                e.printStackTrace();

//                要记得关闭连接
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
//        一定要执行remove操作,不然内存泄露
        threadLocalConn.remove();
    }

//    回滚事务
    public static void rollbackAndClose(){
        Connection con = threadLocalConn.get();
        if(con!=null){//确保该连接非空
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
//        一定要执行remove操作,不然内存泄露
        threadLocalConn.remove();
    }

    public static void close(ResultSet rs, Connection con, Statement s){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(s!=null){
            try {
                s.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
