package file.dao;

import file.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Karl Rules!
 * 2023/9/5
 * now File Encoding is UTF-8
 */
public class BasicDao<T> {
    private QueryRunner queryRunner = new QueryRunner();
    public int update(String sql,Object...objects){
        Connection connection = null;
        try{
            connection = JDBCUtils.getConnection();
            //这里的connection是从threadlocal中拿的
//            因此可以保证是在同一个线程中进行的操作
            int update = queryRunner.update(connection, sql, objects);
            return update;
    }catch (Exception e){
            throw new RuntimeException(e);
        }
//        这里一定要注销finally 因为我们的connection是从threadlocal中拿的
//        如果这里关闭 接下来的其他sql语句就没法运行了 因为流关闭了
//        finally {
//            JDBCUtils.close(null,connection,null);
//        }
    }
    //查一群
    public List<T> queryList(String sql,Class<T> tClass,Object...objects){
        Connection connection = JDBCUtils.getConnection();
        List<T> queryS = null;
        try {
            queryS = queryRunner.query(connection, sql, new BeanListHandler<>(tClass), objects);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return queryS;
    }
    //查一行
    public T querySingle(String sql,Class<T> tClass,Object...objects){
        Connection connection = JDBCUtils.getConnection();
        T query = null;
        try {
            query = queryRunner.query(connection, sql, new BeanHandler<>(tClass), objects);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return query;
    }
    //查一个值
    public Object queryScalar(String sql,Object... objects){
        Connection connection = JDBCUtils.getConnection();
        Object query = null;
        try {
            query = queryRunner.query(connection, sql, new ScalarHandler(), objects);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return query;
    }

}
