package file.dao;

import file.entity.Order;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public interface OrderDAO {
    /**
     * 保存订单
     * @param order
     * @return
     */
    public int saveOrder(Order order);
}
