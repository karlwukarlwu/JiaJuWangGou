package file.test;

import file.dao.AdminDAO;
import file.dao.imp.AdminDAOImp;
import file.entity.Admin;
import file.service.AdminService;
import file.service.imp.AdminServiceImp;
import org.junit.Test;

/**
 * Karl Rules!
 * 2023/9/22
 * now File Encoding is UTF-8
 */
public class AdminDAOImpTest {
    @Test
    public void testIsExistsUser() {
        AdminDAO adminDAO = new AdminDAOImp();
        Admin existsUser = adminDAO.isAdmin("admin", "123");
        System.out.println(existsUser);
    }
    @Test
    public void test2() {
        AdminService adminService = new AdminServiceImp();
        Admin admin01 = adminService.isAdmin("admin2", "123");
        System.out.println(admin01);
    }


}
