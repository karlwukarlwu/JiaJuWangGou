package file.service;

import file.entity.Furn;
import file.entity.Page;

import java.util.List;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
public interface FurnService {
    public List<Furn> queryFurns();

    public int addFurn(Furn furn);

    public int deleteFurn(Integer id);

    public Furn queryFurnById(Integer id);

    public int updateFurnById(Furn furn);

    //分页
    //根据传入的begin 和pageSize 返回一个Page对象
    public Page<Furn> page(int pageNo, int pageSize);

    //通过名称检索 既要有总记录数 也要有当前页要返回的数据
    public Page<Furn> pageByName(int pageNo, int pageSize, String name);


}
