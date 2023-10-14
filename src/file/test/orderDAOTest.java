package file.test;

import file.dao.OrderDAO;
import file.dao.imp.OrderDAOImp;
import file.entity.Order;
import org.junit.Test;

import java.util.Date;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class orderDAOTest {
    private OrderDAO orderDAO = new OrderDAOImp();
    @Test
    public void saveOrder() {
        Order order =
                new Order("sn00002", new Date(), new java.math.BigDecimal(200), 0, 2);
        System.out.println(orderDAO.saveOrder(order));
    }
}
