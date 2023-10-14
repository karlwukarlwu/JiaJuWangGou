package file.entity;

import java.util.List;

/**
 * Karl Rules!
 * 2023/9/23
 * now File Encoding is UTF-8
 */
//将一个网页当成一个目标数据结构
    //而页面展示的家具信息条数是这个数据结构的一个属性
    //所以这个数据结构是一个泛型类
    //这个page不仅仅可以用来展示家具信息 也可以用来展示订单信息

public class Page<T> {
    public static final Integer PAGE_SIZE = 3;
    //来自前端
    private Integer pageNo;//当前页码
    private Integer pageSize = PAGE_SIZE;//当前页显示数量
    //总页数 来自计算得到
    //通过总记录数和每页有多少数据 计算得出 pageTotalCount
    private Integer pageTotalCount;//总页数
    //总记录数 来自数据库->DAO层
    private Integer totalRow;//每页有多少数据


    private List<T> items;//当前页 要显示的数据
    private String url;//分页条中的请求地址

    public Page() {
    }

    public Page(Integer pageNo, Integer pageSize, Integer pageTotalCount, Integer totalRow, List<T> items, String url) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.totalRow = totalRow;
        this.items = items;
        this.url = url;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
