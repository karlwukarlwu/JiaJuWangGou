package file.web;

import file.entity.Member;
import file.service.MemberService;
import file.service.imp.MemberServiceImp;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Karl Rules!
 * 2023/9/17
 * now File Encoding is UTF-8
 */
public class RegisterServlet extends HttpServlet {
    //每当你要调用一个方法的时候 需要写一个实现类
    private MemberService memberService = new MemberServiceImp();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("RegisterServlet is called");
        //getParameter()方法是获取前端传过来的参数 对应的是前端的标签里面的name属性
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //判断这个用户名是否可用
        if(!memberService.isExistsUsername(username)) {
            //用户名不存在 可以注册
            //把数据封装成一个对象
            Member member = new Member(null,username,password,email);
//            System.out.println(email);
            //调用service层的方法
            if (memberService.register(member)) {
                //请求转发和重定向
//                https://chat.openai.com/share/1503756c-d22c-4edb-857c-6122b7f24a7d
                request.getRequestDispatcher("/views/member/register_ok.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("/views/member/register_fail.jsp").forward(request,response);

            }
        } else {
            request.getRequestDispatcher("/views/member/login.jsp").forward(request,response);

        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        doPost(request,response);
    }
}
