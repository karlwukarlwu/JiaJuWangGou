package file.test;

import file.entity.Cart;
import file.entity.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Karl Rules!
 * 2023/10/6
 * now File Encoding is UTF-8
 */
public class CartTest {
    private Cart cart = new Cart();
    @Test
    public void addItem() {
        cart.addItem(new CartItem(1,"小桌子",new BigDecimal(200.00), 2, new BigDecimal(400.00)));
        cart.addItem(new CartItem(2,"小椅子",new BigDecimal(200.00), 2, new BigDecimal(400.00)));
        System.out.println(cart);

    }

}

