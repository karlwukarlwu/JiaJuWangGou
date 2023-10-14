package file.web;

import file.entity.Cart;
import file.entity.Member;
import file.service.OrderService;
import file.service.imp.OrderServiceImp;
import file.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class OrderServlet extends BasicServlet {

    //    定义属性
    private OrderService orderService = new OrderServiceImp();

    protected void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //补充逻辑 买了商品但是清空了 即有购物车但是购物车为空 如果单纯判断购物车=null 会生成空订单
//        if (cart == null) {
        if (cart == null || cart.isEmpty()) {
            //没有购物车 回首页
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        Member member = (Member) req.getSession().getAttribute("member");
        if (null == member) {
            //没有登录 转发到登录页面
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            return;
        }

//        String orderId = null;
//        try {
//            //为什么这里可以进行commit 因为实际上所有的sql都在service调用了 这时候已经在一个事务里面了（thread local）
//            orderId = orderService.saveOrder(cart, member.getId());
////            统一提交
//            JDBCUtils.commitAndClose();
//        } catch (Exception e) {
//            //万一出问题了，回滚
//            JDBCUtils.rollbackAndClose();
//            e.printStackTrace();
//        }

        //这里用的filter统一监听
        String orderId = orderService.saveOrder(cart, member.getId());
        req.getSession().setAttribute("orderId", orderId);

        resp.sendRedirect(req.getContextPath() + "/views/order/checkout.jsp");
    }

    //对订单进行分页
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}
