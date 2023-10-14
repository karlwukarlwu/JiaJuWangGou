package file.test;

import file.dao.OrderItemDAO;
import file.dao.imp.OrderItemDAOImp;
import file.entity.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class OrderItemDAOTest {
    private OrderItemDAO orderItemDAO = new OrderItemDAOImp();
    @Test
    public void saveOrderItem() {
         OrderItem orderItem = new OrderItem(null, "test", new BigDecimal(100), 1, new java.math.BigDecimal(100), "sn0001");
         System.out.println(orderItemDAO.saveOrderItem(orderItem));
    }
}
