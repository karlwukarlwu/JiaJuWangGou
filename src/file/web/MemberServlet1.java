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
//这个是我自己写的 他的是MemberServlet2
public class MemberServlet1 extends HttpServlet {
    private MemberService memberService = new MemberServiceImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这里的parameter是前端传过来的参数,当你不设置value的时候就没有默认值
//        当你设置value的时候就有默认值
//     <input type="hidden" name = "action" value="register">
        String parameter = req.getParameter("action");
        if("login".equals(parameter)) {
            System.out.println("doLogin is called");
            doLogin(req,resp);
        } else if("register".equals(parameter)) {
            System.out.println("doRegister is called");
            doRegister(req,resp);
        }
    }

    protected void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    protected void doRegister(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("RegisterServlet is called");
        //getParameter()方法是获取前端传过来的参数 对应的是前端的标签里面的name属性
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        System.out.println(email);

        //判断这个用户名是否可用
        if (!memberService.isExistsUsername(username)) {
            //用户名不存在 可以注册
            //把数据封装成一个对象
            Member member = new Member(null, username, password, email);
//            System.out.println(email);
            //调用service层的方法
            if (memberService.register(member)) {
                //请求转发和重定向
//                https://chat.openai.com/share/1503756c-d22c-4edb-857c-6122b7f24a7d
                System.out.println("register success");
                request.getRequestDispatcher("/views/member/register_ok.jsp").forward(request, response);
            } else {
                System.out.println("register fail1");
                request.getRequestDispatcher("/views/member/register_fail.jsp").forward(request, response);

            }
        } else {
            System.out.println("register fail2");
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);

        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
