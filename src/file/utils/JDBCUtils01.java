package file.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Karl Rules!
 * 2023/9/5
 * now File Encoding is UTF-8
 */
public class JDBCUtils01 {
    private static DataSource ds;
    static{
        Properties properties = new Properties();
        try{
            //这一行就是区别于JavaSE的写法
            properties.load(JDBCUtils01.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
