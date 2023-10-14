package file.web;

import com.google.gson.Gson;
import file.entity.Cart;
import file.entity.CartItem;
import file.entity.Furn;
import file.service.FurnService;
import file.service.imp.FurnServiceImp;
import file.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Karl Rules!
 * 2023/10/7
 * now File Encoding is UTF-8
 */
public class CartServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImp();

    protected void addItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 商品编号
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //查到相应的家具信息
        Furn furn = furnService.queryFurnById(id);
        if (furn.getStock() <= 0) {
//            这里也得改 因为ajax 就不全局刷新了
            resp.sendRedirect("index.jsp"); //302状态码
//            req.getRequestDispatcher("index.jsp").forward(req,resp); 200状态码
            return;
        }
        //把家具信息转换成为cartItem商品项
        CartItem cartItem = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());
        //从session中获取
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            //如果没有购物车信息，就创建一个购物车
            //第一次登录 没有购物车 就创建一个购物车，以后就都用这个购物车了
            cart = new Cart();
            //把购物车放到session中
            req.getSession().setAttribute("cart", cart);
        }
        //把商品项放到购物车中
        cart.addItem(cartItem);
        System.out.println(cart);
//        添加完成以后 返回json数据给前端
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalCount", cart.getTotalCount());
        String json = new Gson().toJson(resultMap);
        //        返回json数据
        resp.getWriter().write(json);


    }

    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 商品编号
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //查到相应的家具信息
        Furn furn = furnService.queryFurnById(id);
        if (furn.getStock() <= 0) {
            resp.sendRedirect("index.jsp"); //302状态码
//            req.getRequestDispatcher("index.jsp").forward(req,resp); 200状态码
            return;
        }
        //把家具信息转换成为cartItem商品项
        CartItem cartItem = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());
        //从session中获取
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            //如果没有购物车信息，就创建一个购物车
            //第一次登录 没有购物车 就创建一个购物车，以后就都用这个购物车了
            cart = new Cart();
            //把购物车放到session中
            req.getSession().setAttribute("cart", cart);
        }
        //把商品项放到购物车中
        cart.addItem(cartItem);
        System.out.println(cart);

        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //id
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        // 数量
        int count = DataUtils.parseInt(request.getParameter("count"), 1);
        System.out.println("id=" + id + ", count=" + count);
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //正常情况下都是可以获取到购物车的，因为你有了货物自然就有了购物车，和cart那个update一样，防止从非图形化页面请求
        if (null != cart) {
            cart.updateCount(id, count);
        }
        System.out.println("cart=" + cart);
        //重定向回原来的购物车页面
        //为什么用referer 而不是直接用cart.html
        //因为cart.html是一个动态的页面，有可能是分页的 所以要返回指定的页面
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void delItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品编号
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        //获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (null != cart) {
            //删除了购物车商品项
            cart.delItem(id);
        }
        //重定向回原来的购物车页面
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (null != cart) {
            //清空购物车
            cart.clear();
        }
        //重定向回原来的购物车页面
        response.sendRedirect(request.getHeader("Referer"));
    }
}
