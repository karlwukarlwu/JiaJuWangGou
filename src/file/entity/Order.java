package file.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Karl Rules!
 * 2023/10/8
 * now File Encoding is UTF-8
 */
//Order 表由无数个orderItem组成  一对多的关系 一个订单 三个桌子，四把椅子...
public class Order {
//    CREATE TABLE `order`(
//            `id` VARCHAR(64) PRIMARY KEY, -- 订单号 因为可能会有英文
//            `create_time` DATETIME NOT NULL, -- 订单生成时间 最好写not null
//            `price` DECIMAL(11,2) NOT NULL,-- 要参考之前的表的类型 不然可能会匹配不上
//            `status` TINYINT NOT NULL,-- 0未发货 1 已发货 2 已结账
//            `member_id` INT NOT NULL -- 订单对应的member id
//          -- 接下来关联 member表和这个表之间的外键关系
//          -- FOREIGN KEY(`member_id`) REFERENCES `member`(`id`)
//            -- 随着发展 这样影响效率 用其他办法保证数据一致性
//    )CHARSET utf8 ENGINE INNODB
    private String id;
    private Date createTime;// 这里用的是java.util.Date
    private BigDecimal price;
    private Integer status = 0;//0未发货 1 已发货 2 已结账
    private Integer memberId;//订单对应的member id

    public Order() {
    }

    public Order(String id, Date createTime, BigDecimal price, Integer status, Integer memberId) {
        this.id = id;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", memberId=" + memberId +
                '}';
    }
}
