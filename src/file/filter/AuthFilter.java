package file.filter;

import com.google.gson.Gson;
import file.entity.Admin;
import file.entity.Member;
import file.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 用于登录验证 登陆过放行，没有登录过滤
 * Karl Rules!
 * 2023/10/12
 * now File Encoding is UTF-8
 */
public class AuthFilter implements Filter {
//    将拿到的参数的url放入 excludedUrls里面
    private List<String> excludedUrls;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String strExcludedUrls = filterConfig.getInitParameter("excludedUrls");
//            <param-value>/views/manage/manage_login.jsp,/views/member/login.jsp</param-value>
        //因此用逗号分割
        String[] split = strExcludedUrls.split(",");
        excludedUrls = Arrays.asList(split);
        for (String s :
             split) {
            System.out.println("T");
            System.out.println("excludedUrls = "+ excludedUrls);
        }
    }
//    这个是没有考虑Ajax的
    public void doFilter2(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getServletPath();
        //得到请求的url
        System.out.println("url= "+url);
        //判断是否要验证
        //未登录想登录 -> 正常登录
        //未登录访问别的 ->请求转发到登录页面
//        已登录访问别的 不被请求转发限制 走自己想访问的页面
        if (!excludedUrls.contains(url)) {
            //得到session中的member对象
            Member member = (Member)request.getSession().getAttribute("member");
            Admin admin = (Admin)request.getSession().getAttribute("admin");
            if(member==null&&admin==null){
//            请求转发不走filter
//            如果是重定向 这里会无线循环 因为到了member就会重新打到filter
//            而请求转发是一次请求 因此不会重复调用filter
                request.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest,servletResponse);
                return;
            }
        }


        filterChain.doFilter(servletRequest,servletResponse);

    }
//    这个是考虑了Ajax的
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getServletPath();
        //得到请求的url
        System.out.println("url= "+url);
        //判断是否要验证
        //未登录想登录 -> 正常登录
        //未登录访问别的 ->请求转发到登录页面
//        已登录访问别的 不被请求转发限制 走自己想访问的页面
        if (!excludedUrls.contains(url)) {
            //得到session中的member对象
            Member member = (Member)request.getSession().getAttribute("member");
            Admin admin = (Admin)request.getSession().getAttribute("admin");
            if(member==null&&admin==null){
//                开始考虑Ajax了
                if(!WebUtils.isAjaxRequest(request)){
                    request.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest,servletResponse);
                    return;
                }else {
                    HashMap<String, Object> resultMap = new HashMap<>();
                    resultMap.put("url","views/member/login.jsp");
                    String json = new Gson().toJson(resultMap);
                    servletResponse.getWriter().write(json);
                }
//
                return;
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
