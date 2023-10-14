package file.web;

import file.entity.Furn;
import file.service.FurnService;
import file.service.imp.FurnServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
//这个是最开始的 版本 接下来他要用BeanUtils来简化代码
public class FurnServlet01 extends BasicServlet {
    private FurnService furnService = new FurnServiceImp();

    //同样是反射+动态绑定拿到对应的方法
    protected void list(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("list method is called");
        //调用service层的方法
        List<Furn> furns = furnService.queryFurns();
//将数据存到request域中
        request.setAttribute("furns", furns);
        //转发到jsp页面
        try {
            request.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (Furn furn :
//                furns) {
//            {
//                System.out.println(furn);
//            }
//
//        }
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String maker = req.getParameter("maker");
        String price = req.getParameter("price");
        String sales = req.getParameter("sales");
        String stock = req.getParameter("stock");
        try {
            int i = Integer.parseInt(price);
        }catch (NumberFormatException e){
            req.setAttribute("msg","价格输入有误");
            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req,resp);
            return;
        }
        Furn furn = new Furn(null, name, maker, new BigDecimal(price), new Integer(sales), new Integer(stock), "assets/images/product-image/default.jpg");
        furnService.addFurn(furn);
        try {
            //这里一定要手动改成list 不然会无线循环 因为action 一直没有被改成list 而是add
            // 为什么这里不用请求转发
//            因为使用请求转发 等于每次都重复执行一边add 然后再list
            //但是重定向 重新定位到list网页 这样每次刷新只是重新显示list网页
            //在第八章
//            req.getRequestDispatcher("/manage/furnServlet?action=list").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
