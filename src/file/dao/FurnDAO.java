package file.dao;

import file.entity.Furn;

import java.util.List;

/**
 * Karl Rules!
 * 2023/9/18
 * now File Encoding is UTF-8
 */
public interface FurnDAO {
    /**
     * 返回所有家具的信息集合
     */
    public List<Furn> queryAllFurn();

    //添加家具 存入db int是影响的行数
    public int addFurn(Furn furn);

    //删除DB中的家具
    public int deleteFurn(Integer id);

//根据id查询
    public Furn queryFurnById(Integer id);

    //根据id修改
    public int updateFurnById(Furn furn);

    //下面两条是一起的getTotalRow getPageItems
    //分析 有哪些是能从数据库中获取的？
    //总记录数
    public int getTotalRow();

    //当前页要返回的数据
    // begin 表示从第几条记录开始获取 从零开始
//
    public List<Furn> getPageItems(int begin, int pageSize);

    //通过名称检索 既要有总记录数 也要有当前页要返回的数据
    public int getTotalRowByName(String name);
     //根据名字返回当前的数据具体信息
    public List<Furn> getPageItemsByName(int begin, int pageSize, String name);

}
