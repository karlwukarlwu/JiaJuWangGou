package file.service.imp;

import com.sun.org.apache.xpath.internal.operations.Or;
import file.dao.FurnDAO;
import file.dao.OrderDAO;
import file.dao.OrderItemDAO;
import file.dao.imp.FurnDAOImp;
import file.dao.imp.OrderDAOImp;
import file.dao.imp.OrderItemDAOImp;
import file.entity.*;
import file.service.OrderService;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class OrderServiceImp implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImp();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImp();
    private FurnDAO furnDAO = new FurnDAOImp();

    @Override
    public String saveOrder(Cart cart, Integer memberId) {
        //cart 对象 到order 对象
        //唯一id uuid 这里用的毫秒数 有对应的uuid类 也可以用
        String orderId = System.currentTimeMillis() + "" + memberId;
        //通过cart 生成order 对象
        Order order =
                new Order(orderId, new Date(), cart.getTotalPrice(), 0, memberId);
        //保存订单
        orderDAO.saveOrder(order);

        //通过cartItem 生成orderItem 对象
        Map<Integer, CartItem> items = cart.getItems();
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem cartItem = items.get(id);
            //生成orderItem 对象
            OrderItem orderItem = new OrderItem(null, cartItem.getName(),
                    cartItem.getPrice(), cartItem.getCount(), cartItem.getTotalPrice(), orderId);
            //保存订单项
            orderItemDAO.saveOrderItem(orderItem);

            //更新库存和销量 开始改furn
            //这里就是分层的好处 直接调用另一层即可
            //1. 先查询出furn 因为key就是furn的id 因此直接用id来查furn
            Furn furn = furnDAO.queryFurnById(id);
            //2. 修改furn的销量和库存
            furn.setSales(furn.getSales() + cartItem.getCount());
            furn.setStock(furn.getStock() - cartItem.getCount());
            //3. 更新furn
            furnDAO.updateFurnById(furn);
            //这个方法就是他那个updateFurn 名字不一样罢了
        }
        //清空购物车
        cart.clear();
        return orderId;
    }
}
