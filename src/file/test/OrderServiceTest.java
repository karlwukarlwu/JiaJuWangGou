package file.test;

import file.entity.Cart;
import file.entity.CartItem;
import file.service.OrderService;
import file.service.imp.OrderServiceImp;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class OrderServiceTest {
    private OrderService orderService = new OrderServiceImp();

    @Test
    public void saveOrder() {
        Cart cart = new Cart();
        //两条item 因此数据库的order_item表中也有两条数据
        //一个购物车 因此数据库的orders表中也有一条数据
        //furn对应的数据也会发生变化
        cart.addItem(new CartItem(1, "desk", new BigDecimal(200.00), 2, new BigDecimal(400.00)));
        cart.addItem(new CartItem(447, "123", new BigDecimal(123.00), 2, new BigDecimal(246.00)));
        String orderId = orderService.saveOrder(cart, 1);
        System.out.println(orderId);
    }
}
