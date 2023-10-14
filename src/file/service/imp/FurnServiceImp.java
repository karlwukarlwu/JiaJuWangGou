package file.service.imp;

import file.dao.FurnDAO;
import file.dao.imp.FurnDAOImp;
import file.entity.Furn;
import file.entity.Page;
import file.service.FurnService;

import java.util.List;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
public class FurnServiceImp implements FurnService {

    //因为service调用dao 所以这里造一个dao的对象
    private FurnDAO furnDAO = new FurnDAOImp();

    @Override
    public List<Furn> queryFurns() {
        return furnDAO.queryAllFurn();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurn(Integer id) {
        return furnDAO.deleteFurn(id);
    }

    //修改的时候先查询 回显使用
    @Override
    public Furn queryFurnById(Integer id) {
        return furnDAO.queryFurnById(id);
    }

    //修改
    @Override
    public int updateFurnById(Furn furn) {
        return furnDAO.updateFurnById(furn);
    }

    @Override
    public Page<Furn> page(int pageNo, int pageSize) {
        //先造一个page对象 根据实际情况填充属性
        //
        Page<Furn> furnPage = new Page<>();
        //当前页码 和pageSize从前端获取
        furnPage.setPageNo(pageNo);
        furnPage.setPageSize(pageSize);
        //总条数 从数据库获取
        int totalRow = furnDAO.getTotalRow();
        furnPage.setTotalRow(totalRow);
        //总页数 通过总条数和每页显示的条数计算得到
        int pageTotalCount = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
        furnPage.setPageTotalCount(pageTotalCount);


        //当前页要显示的数据 从数据库获取
        //跟id 没有关系 跟数据在数据库中排多少位有关系
        int begin = (pageNo - 1) * pageSize;//从第几条记录开始获取
        List<Furn> pageItems = furnDAO.getPageItems(begin, pageSize);
        furnPage.setItems(pageItems);

        //url 在分页导航 先放一放
        return furnPage;

    }

    @Override
    public Page<Furn> pageByName(int pageNo, int pageSize, String name) {
        Page<Furn> objectPage = new Page<>();
        objectPage.setPageNo(pageNo);
        objectPage.setPageSize(pageSize);
        //查询的名字的总条数 在数据库中 通过名字获取
        int totalRow = furnDAO.getTotalRowByName(name);
        objectPage.setTotalRow(totalRow);
        int pageTotalCount = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
        objectPage.setPageTotalCount(pageTotalCount);
        //因为前端页数是从1 开始 所以这里要减1
        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItemsByName(begin, pageSize, name);
        objectPage.setItems(pageItems);
        return objectPage;
    }


}
