package file.web;

import file.entity.Member;
import file.service.MemberService;
import file.service.imp.MemberServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Karl Rules!
 * 2023/9/17
 * now File Encoding is UTF-8
 */
public class LoginServlet extends HttpServlet {
    private MemberService memberService = new MemberServiceImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Member member = new Member(null, username, password, null);
        member = memberService.login(member);
        if (member == null) {
            //四个域对象 在jsp/servlet这里
            //这个是用来展示错误信息的
            req.setAttribute("msg", "用户名或密码错误");
            //这个是用来展示用户名的
            req.setAttribute("username", username);
            System.out.println(req.getContextPath());

            System.out.println("login fail222");
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
        } else {
            System.out.println("login success22");
            req.getRequestDispatcher("/views/member/login_ok.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
