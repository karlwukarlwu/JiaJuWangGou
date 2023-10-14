package file.web;

import file.entity.Admin;
import file.service.AdminService;
import file.service.imp.AdminServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Karl Rules!
 * 2023/9/22
 * now File Encoding is UTF-8
 */
public class AdminServlet extends BasicServlet {
    AdminService adminService = new AdminServiceImp();

    protected void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(" admin servlet login method is called");
        String username = request.getParameter("user-name");
        String password = request.getParameter("user-password");
        Admin admin = adminService.isAdmin(username, password);
        if (null == admin) {
            System.out.println("wrong username or password");
            request.setAttribute("msg", "用户名或密码错误");
            request.getRequestDispatcher("/views/manage/manage_login.jsp").forward(request, response);

        } else {
            System.out.println("success");
            request.getSession().setAttribute("admin", admin);
            request.getRequestDispatcher("/views/manage/manage_menu.jsp").forward(request, response);
        }
    }

        @Override
        protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doPost(req, resp);
        }

        @Override
        protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doGet(req, resp);
        }
    }
