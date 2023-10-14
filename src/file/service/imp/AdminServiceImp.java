package file.service.imp;

import file.dao.AdminDAO;
import file.dao.imp.AdminDAOImp;
import file.entity.Admin;
import file.service.AdminService;

/**
 * Karl Rules!
 * 2023/9/22
 * now File Encoding is UTF-8
 */
public class AdminServiceImp implements AdminService {
    AdminDAO adminDAO = new AdminDAOImp();
    @Override
    public Admin isAdmin(String username, String password) {
        return adminDAO.isAdmin(username, password) ;
    }
}
