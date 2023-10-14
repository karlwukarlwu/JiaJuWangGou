package file.web;

import com.google.gson.Gson;
import file.entity.Member;
import file.service.MemberService;
import file.service.imp.MemberServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * Karl Rules!
 * 2023/9/17
 * now File Encoding is UTF-8
 */
public class MemberServlet extends BasicServlet {
    private MemberService memberService = new MemberServiceImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
//            使用session登录
            req.getSession().setAttribute("member", member);
            System.out.println("login success22");
            req.getRequestDispatcher("/views/member/login_ok.jsp").forward(req, resp);
        }
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //销毁session
        req.getSession().invalidate();
        //重定向到首页
        resp.sendRedirect(req.getContextPath());
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("RegisterServlet is called");
        //getParameter()方法是获取前端传过来的参数 对应的是前端的标签里面的name属性
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        System.out.println(email);

        String code = request.getParameter("code");
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //当获取验证码以后 立刻删除session中的储存的
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //判断验证码是否正确
        if (token != null && token.equalsIgnoreCase(code)) {
            System.out.println("验证码正确");
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
//                如果用户名已经存在
                System.out.println("register fail2");
                request.setAttribute("msg", "用户名已存在");
                request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);

            }
        } else {
            request.setAttribute("msg", "验证码错误");
            request.setAttribute("active", "register");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        }
    }

//    ajax检查用户名是不是在DB
    protected void isExistUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        boolean existsUsername = memberService.isExistsUsername(username);

//        如果前端需要我返回一个json对象 如何操作？
//        1.最简单的拼接
//        String json = "{\"existsUsername\":" + existsUsername + "}";

//        2.使用json工具包
        HashMap<String , Object> resultHashMap = new HashMap<>();
        resultHashMap.put("existsUsername", existsUsername);
        String json = new Gson().toJson(resultHashMap);


//        返回json数据
        resp.getWriter().write(json);

    }

    // ajax检查验证码
    protected void verificationCode(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String code = request.getParameter("code");
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //当获取验证码以后 立刻删除session中的储存的
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
//        将验证转化为json
        //判断验证码是否正确
        boolean equalT = token.equalsIgnoreCase(code);
        HashMap<String , Object> resultHashMap = new HashMap<>();
        if (token != null && equalT) {
            resultHashMap.put("result", true);
            String json = new Gson().toJson(resultHashMap);
            System.out.println("验证码正确");
            resp.getWriter().write(json);
        } else {
            resultHashMap.put("result", false);
            String json = new Gson().toJson(resultHashMap);
            resp.getWriter().write(json);
        }
    }
}
