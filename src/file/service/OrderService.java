package file.service;

import file.entity.Cart;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
public interface OrderService {
    /**
     * 保存订单
     * @param userId
     * @return
     */
    //当订单拿到以后 返回一个订单号
    //业务分析 cart 对象在session中 因此是从web层传过来的
    // 订单需要和会员进行关联 因此需要传入会员id
    public String saveOrder(Cart cart, Integer memberId);
}
