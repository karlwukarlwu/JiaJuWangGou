package file.dao.imp;

import file.dao.AdminDAO;
import file.dao.BasicDao;
import file.entity.Admin;

/**
 * Karl Rules!
 * 2023/9/22
 * now File Encoding is UTF-8
 */
public class AdminDAOImp extends BasicDao<Admin> implements AdminDAO {
    @Override
    public Admin isAdmin(String username, String password) {
        String sql = "select * from admin where username=? and password=?";
        Admin admin = querySingle(sql, Admin.class, username, password);
        return admin;
    }
}
