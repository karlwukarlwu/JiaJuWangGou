package file.dao.imp;

import file.dao.BasicDao;
import file.dao.FurnDAO;
import file.entity.Furn;


import java.util.List;

/**
 * Karl Rules!
 * 2023/9/18
 * now File Encoding is UTF-8
 */
public class FurnDAOImp extends BasicDao<Furn> implements FurnDAO {

    @Override
    public List<Furn> queryAllFurn() {
        String sql = "select `id`,`name`,`maker`,`price`,`sales`,`stock`,`img_path`imgPath from furn";
        List<Furn> furns = queryList(sql, Furn.class);
        return furns;
    }

    @Override
    public int addFurn(Furn furn) {
        String sql = "insert into furn(`id`,`name`,`maker`,`price`,`sales`,`stock`,`img_path`) values(null,?,?,?,?,?,?)";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath());
    }

    @Override
    public int deleteFurn(Integer id) {
        String sql = "delete from `furn` where id = ?";
        return update(sql, id);
    }

    @Override
    public Furn queryFurnById(Integer id) {
        String sql = "select `id`,`name`,`maker`,`price`,`sales`,`stock`,`img_path`imgPath from furn where id = ?";
        return querySingle(sql, Furn.class, id);
    }

    @Override
    public int updateFurnById(Furn furn) {
        String sql = "update `furn` set `name`=?,`maker`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? where id = ?";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath(), furn.getId());
    }

    @Override
    public int getTotalRow() {
        String sql = "select count(*) from `furn`";
        Number number = (Number) queryScalar(sql);
        return number.intValue();

    }

    @Override
    public List<Furn> getPageItems(int begin, int pageSize) {
        String sql = "select `id`,`name`,`maker`,`price`,`sales`,`stock`,`img_path`imgPath from furn limit ?,?";
        return queryList(sql, Furn.class, begin, pageSize);
    }

    @Override
    public int getTotalRowByName(String name) {
        String sql = "SELECT COUNT(*) FROM `furn` WHERE `name` LIKE ?";
        Number number = (Number) queryScalar(sql, "%" + name + "%");
        return number.intValue();
    }

    @Override
    public List<Furn> getPageItemsByName(int begin, int pageSize, String name) {
        String sql = "select `id`,`name`,`maker`,`price`,`sales`,`stock`,`img_path`imgPath from furn where `name` like ? limit ?,?";
        //展示具体数据 也要注意分页
        return queryList(sql, Furn.class, "%" + name + "%", begin, pageSize);
    }


}

