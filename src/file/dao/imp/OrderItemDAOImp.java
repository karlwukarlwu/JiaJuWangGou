package file.dao.imp;

import file.dao.BasicDao;
import file.dao.OrderItemDAO;
import file.entity.OrderItem;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class OrderItemDAOImp extends BasicDao<OrderItem> implements OrderItemDAO {

    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into `order_item`(`id`,`name`,`price`,`count`,`total_price`,`order_id`) values(?,?,?,?,?,?)";
        return update(sql, orderItem.getId(), orderItem.getName(), orderItem.getPrice(), orderItem.getCount(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
