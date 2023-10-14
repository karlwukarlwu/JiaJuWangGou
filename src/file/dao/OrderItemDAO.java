package file.dao;

import file.entity.OrderItem;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public interface OrderItemDAO {
    /**
     * 保存订单项
     * @param orderItem
     * @return
     */
    public int saveOrderItem(OrderItem orderItem);
}
