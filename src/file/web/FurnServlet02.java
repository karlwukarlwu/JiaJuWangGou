package file.web;

import file.entity.Furn;
import file.service.FurnService;
import file.service.imp.FurnServiceImp;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
//这个是第二步 第三步再进行封装
public class FurnServlet02 extends BasicServlet {
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
//        String name = req.getParameter("name");
//        String maker = req.getParameter("maker");
//        String price = req.getParameter("price");
//        String sales = req.getParameter("sales");
//        String stock = req.getParameter("stock");
//        try {
//            int i = Integer.parseInt(price);
//        }catch (NumberFormatException e){
//            req.setAttribute("msg","价格输入有误");
//            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req,resp);
//            return;
//        }
//        Furn furn = new Furn(null, name, maker, new BigDecimal(price), new Integer(sales), new Integer(stock), "assets/images/product-image/default.jpg");
        Furn furn = new Furn();
        try {
            //这里使用BeanUtils来简化代码 引入commons-beanutils-1.9.4.jar 和commons-logging-1.2.jar
            //这里的populate方法是将map中的数据封装到bean中
            // 先造一个空参的furn 然后调用populate方法 将map中的数据封装到空参的bean中
            // 然后用这个填好的furn 进行对应的操作
            //req.getParameterMap() 这里用到了反射
            //这里的map中的key和bean中的属性名一致
//            <input name="name" type="text" value="Name"/>
            BeanUtils.populate(furn, req.getParameterMap());
            //当前端没有给出imgPath的时候，给一个默认的图片路径
            //如何解决 在entity里面设置默认的路径 这样反射的时候就不会出错
//             public Furn(Integer id, String name, String maker, BigDecimal price, Integer sales, Integer stock, String imgPath) {
//                           this.id = id;
//                           this.name = name;
//                           this.maker = maker;
//                           this.price = price;
//                           this.sales = sales;
//                           this.stock = stock;
//                           if (imgPath != null && !"".equals(imgPath.trim())) {
//                             this.imgPath = imgPath;
//                            }
//                           this.imgPath = imgPath;
//                       }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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
