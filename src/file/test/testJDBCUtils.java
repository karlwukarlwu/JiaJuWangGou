package file.test;

import file.utils.JDBCUtils;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * Karl Rules!
 * 2023/9/5
 * now File Encoding is UTF-8
 */
public class testJDBCUtils {
    @Test
    public void t1() throws SQLException {
        Connection con = JDBCUtils.getConnection();
        System.out.println(con);
        JDBCUtils.close(null,con,null);

    }
}
