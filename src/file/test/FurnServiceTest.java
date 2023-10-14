package file.test;

import file.dao.FurnDAO;
import file.dao.imp.FurnDAOImp;
import file.entity.Furn;
import file.entity.Page;
import file.service.FurnService;
import file.service.imp.FurnServiceImp;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
public class FurnServiceTest {
    private FurnService furnService = new FurnServiceImp();

    @Test
    public void testQueryFurns() {
        for (Furn queryFurn : furnService.queryFurns()) {
            System.out.println(queryFurn);
        }

    }

    @Test
    public void testAddFurn() {
        FurnService furnService = new FurnServiceImp();
        Furn furn = new Furn();
        furn.setId(null);
        furn.setName("test");
        furn.setMaker("test");
        furn.setPrice(BigDecimal.valueOf(100));
        furn.setSales(100);
        furn.setStock(100);
        furn.setImgPath("assets/images/product-image/default.jpg");
        int i = furnService.addFurn(furn);
        System.out.println(i);

    }

    @Test
    public void testDeleteFurn() {
        FurnService furnService = new FurnServiceImp();
        int i = furnService.deleteFurn(448);
        System.out.println(i);
    }

    @Test
    public void testQueryFurnById() {
        FurnService furnService = new FurnServiceImp();
        Furn furn = furnService.queryFurnById(446);
        System.out.println(furn);
    }

    @Test
    public void testUpdateFurnById() {
        FurnService furnService = new FurnServiceImp();
        Furn furn = new Furn();
        furn.setId(446);
        furn.setName("123123");
        furn.setMaker("test");
        furn.setPrice(BigDecimal.valueOf(100));
        furn.setSales(123);
        furn.setStock(123);
        furn.setImgPath("assets/images/product-image/default.jpg");
        int i = furnService.updateFurnById(furn);
        System.out.println(i);
    }

    @Test
    public void testPage() {
        FurnService furnService = new FurnServiceImp();
        Page<Furn> page = furnService.page(2, 3);
        for (Furn furn : page.getItems()) {
            System.out.println(furn);
        }
    }
    @Test
    public void testPageByName() {
        FurnService furnService = new FurnServiceImp();
        Page<Furn> page = furnService.pageByName(2, 3, "me");
        for (Furn furn : page.getItems()) {
            System.out.println(furn);
        }
    }
}
