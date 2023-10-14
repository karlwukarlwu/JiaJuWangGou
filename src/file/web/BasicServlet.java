package file.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Karl Rules!
 * 2023/9/17
 * now File Encoding is UTF-8
 */
public abstract class BasicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //解决中文乱码问题
        req.setCharacterEncoding("UTF-8");


        System.out.println("BasicServlet is called");

        String action = req.getParameter("action");
        System.out.println(action);
//        面对get请求 需要将get请求的url写成这样 不然action拿不到参数 会报错
//        http://localhost:8080/shopping/manage/furnServlet?action=list


        // 使用模板模式 子类调用父类方法 ，
        // 但是父类方法里面的this还是指向子类 这样通过反射 在父类中拿到子类的方法
        // 简化多个if-else

        //通过反射 拿到子类的方法
        try {
            //子类调用父类方法 父类中的this指向子类，通过反射，拿到子类的方法
            //对应的子类servlet中分别有 register和login 两个方法 对应的 action （action的值和方法名是一致的，不一致无法反射）
            Method declaredMethod = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //用反射调用子类的方法
            System.out.println(this);
            declaredMethod.invoke(this, req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doPost(req, resp);
    }
}
