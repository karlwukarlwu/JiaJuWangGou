package file.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Karl Rules!
 * 2023/10/6
 * now File Encoding is UTF-8
 */

//这个是购物车类
public class Cart {
    //定义属性 包含多个cartItem
//    使用hashmap
//    map 的key是家具的id value是cartItem的信息 其中cartItem的信息包括家具的id 名字 价格 数量 总价
//    items.put(cartItem.getId(), cartItem);
    private Map<Integer, CartItem> items = new HashMap<>();

    private Integer totalCount = 0;
    private BigDecimal totalPrice = new BigDecimal(0);


    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.size()==0;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem cartItem) {
        //先查看购物车中是否已经添加过此商品，如果已经添加
        //则数量累加，总金额更新，如果没有添加过，直接放到集合中即可
        CartItem item = items.get(cartItem.getId());
        if (null == item) {
            //之前没添加过
            items.put(cartItem.getId(), cartItem);
        } else {
            item.setCount(item.getCount() + 1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }

    }

    public Integer getTotalCount() {
        totalCount = 0;
        Set<Integer> keys = items.keySet();
        for (Integer key : keys) {
            CartItem cartItem = items.get(key);
            totalCount += cartItem.getCount();
        }
        System.out.println("this method is called");
        return totalCount;
    }
    public BigDecimal getTotalPrice() {
        totalPrice = new BigDecimal(0);
        Set<Integer> keys = items.keySet();
        for (Integer key : keys) {
            CartItem cartItem = items.get(key);
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        System.out.println("this method is called");
        return totalPrice;
    }

    //修改指定的cartItem的数量
    public void updateCount(Integer id, Integer count) {
        //先查看购物车中是否有此商品，如果有，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if (null != cartItem) {
            //先更新数量
            cartItem.setCount(count);
            //再更新总价，以后如果调用属性 最好用get方法 因为属性可能要被校验
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }

    //删除商品项
    public void delItem(Integer id) {
        items.remove(id);
    }
    //清空购物车
    public void clear() {
        items.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
