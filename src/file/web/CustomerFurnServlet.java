package file.web;

import file.entity.Furn;
import file.entity.Page;
import file.service.FurnService;
import file.service.imp.FurnServiceImp;
import file.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Karl Rules!
 * 2023/9/26
 * now File Encoding is UTF-8
 */

//这个servlet是给客户用的 对应的是 customer/index.jsp
//那个furnServlet是给管理员用的
public class CustomerFurnServlet extends BasicServlet{
    private FurnService furnService = new FurnServiceImp();
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        //调用service层的方法
        Page<Furn> page = furnService.page(DataUtils.parseInt(pageNo, 1), DataUtils.parseInt(pageSize, Page.PAGE_SIZE));
        //将数据存到request域中
        request.setAttribute("page", page);
        //转发到jsp页面
        request.getRequestDispatcher("/views/customer/index.jsp").forward(request, response);
    }
    protected void pageByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        int pageNo =DataUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"),Page.PAGE_SIZE);
        String name = request.getParameter("name");
        // 如果有name但是没有值 接受的是 “”
        // 如果干脆就没有name参数 是null
        if (null == name) {
            name= "";
        }

        //调用service层的方法
        Page<Furn> page = furnService.pageByName(pageNo, pageSize, name);
        //将数据存到request域中
        request.setAttribute("page", page);
//        System.out.println("..");


//        request.setAttribute("name", name);
//        如果这样做 很繁琐
//        "customerFurnServlet?action=pageByName&pageNo=${requestScope.page.pageNo-1}&name=${requestScope.name}"
//        将 前面这一大串共同的url 用stringBuilder拼接起来 再用el提取
        //
        StringBuilder url = new StringBuilder("customerFurnServlet?action=pageByName");
        if(!"".equals(name)){
            url.append("&name=").append(name);
        }
        page.setUrl(url.toString());
//        封装完url 传到前端
//            <li><a href="${requestScope.page.url}&pageNo=1">首页</a></li>

        //转发到jsp页面
        request.getRequestDispatcher("/views/customer/index.jsp").forward(request, response);
    }
}
