package file.test;

import file.dao.FurnDAO;
import file.dao.imp.FurnDAOImp;
import file.entity.Furn;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
public class FurnDAOTest {
    @Test
    public void testQueryAllFurn() {
        FurnDAO furnDAO = new FurnDAOImp();
        List<Furn> furns = furnDAO.queryAllFurn();
        for (   Furn furn:
                furns) {
            System.out.println(furn);
        }
    }
    @Test
    public void testAddFurn() {
        FurnDAO furnDAO = new FurnDAOImp();
        Furn furn = new Furn();
        furn.setId(null);
        furn.setName("test");
        furn.setMaker("test");
        furn.setPrice(BigDecimal.valueOf(100));
        furn.setSales(100);
        furn.setStock(100);
        furn.setImgPath("assets/images/product-image/default.jpg");
        int i = furnDAO.addFurn(furn);

        System.out.println(i);
    }


    @Test
    public void getTotalRowByName() {
        System.out.println(new FurnDAOImp().getTotalRowByName("me"));
    }
    @Test
    public void getPageItemsByName() {
        List<Furn> furns = new FurnDAOImp().getPageItemsByName(0, 3, "me");
        for (  Furn furn:
                furns) {
            System.out.println(furn);

        }
    }
}
