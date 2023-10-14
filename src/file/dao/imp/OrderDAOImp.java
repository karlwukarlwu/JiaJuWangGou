package file.dao.imp;

import file.dao.BasicDao;
import file.dao.OrderDAO;
import file.entity.Order;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public class OrderDAOImp extends BasicDao<Order> implements OrderDAO {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into `order`(`id`,`create_time`,`price`,`status`,`member_id`) values(?,?,?,?,?)";
        return update(sql, order.getId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getMemberId());

    }
}
