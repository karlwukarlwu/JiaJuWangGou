package file.entity;

import java.math.BigDecimal;

/**
 * Karl Rules!
 * 2023/9/18
 * now File Encoding is UTF-8
 */
//-- CREATE TABLE `furn`(
//        -- `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT, #Id
//        -- `name` VARCHAR(64) NOT NULL, #家具名
//        -- `maker` VARCHAR(64) NOT NULL, #制造商
//        -- `price` DECIMAL(11,2) NOT NULL,#价格 定点数 这个比float和double都好
//        -- `sales` INT UNSIGNED NOT NULL,#销量
//        -- `stock` INT UNSIGNED NOT NULL, #库存
//        -- `img_path` VARCHAR(256) NOT NULL #存放图片的路径
//        -- )CHARSET utf8 ENGINE INNODB
public class Furn {
    //一定不要用int 因为int null不能接受
    private Integer id;
    private String name;
    private String maker;
    //DECIMAL 对应Java 的bigDecimal数据类型 不要用double float
    private BigDecimal price;
    private Integer sales;
    private Integer stock;
    //表名和属性名不一样的时候怎么办
    private String imgPath = "assets/images/product-image/default.jpg";

    public Furn() {
    }

    public Furn(Integer id, String name, String maker, BigDecimal price, Integer sales, Integer stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        if (imgPath != null && !"".equals(imgPath.trim())) {
            this.imgPath = imgPath;
        }
        this.imgPath = imgPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Furn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maker='" + maker + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", stock=" + stock +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
