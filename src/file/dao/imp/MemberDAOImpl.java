package file.dao.imp;

import file.dao.BasicDao;
import file.dao.MemberDAO;
import file.entity.Member;

/**
 * Karl Rules!
 * 2023/9/6
 * now File Encoding is UTF-8
 */
public class MemberDAOImpl extends BasicDao<Member> implements MemberDAO {
    //实现基础的DAO方法 同时根据接口 实现接口需要的方法

    @Override
    public Member queryMemberByUsername(String username) {
        //这行先在 mysql workbench 中测试一下
        String sql = "select * from member where username = ?";
        Member member = querySingle(sql, Member.class, username);
        return member;
    }

    //保存一个会员
    @Override
    public int saveMember(Member member) {
        // 这里update是一个 传入很多参数的方法
        //通过对member的get方法获取到对应的值
        String sql = "insert into member(username,password,email) values(?,md5(?),?)";
        // `username` VARCHAR(32) NOT NULL UNIQUE,
        // 在创建数据库的时候已经设置了用户名不能重复的要求
        return update(sql, member.getUsername(), member.getPassword(), member.getEmail());
    }

    @Override
    public Member queryMemberByUsernameAndPassword(String username, String password) {
        String sql = "SELECT `id`,`username`,`password`,`email` FROM `member` " +
                " WHERE `username`=? and `password`=md5(?)";
        return querySingle(sql, Member.class, username, password);
    }
}
