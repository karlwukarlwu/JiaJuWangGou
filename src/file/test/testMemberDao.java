package file.test;

import file.dao.imp.MemberDAOImpl;
import file.entity.Member;
import org.junit.Test;

/**
 * Karl Rules!
 * 2023/9/6
 * now File Encoding is UTF-8
 */
public class testMemberDao {
    private MemberDAOImpl memberDAO = new MemberDAOImpl();

    @Test
    public void t1() {
        if (memberDAO.queryMemberByUsername("admin") == null) {
            System.out.println("用户名不存在");
        } else {
            System.out.println("用户名存在");
        }
        if (memberDAO.queryMemberByUsername("karl") == null) {
            System.out.println("用户名不存在");
        } else {
            System.out.println("用户名存在");
        }
    }
    @Test
    public void t2(){
        Member member = new Member(0,"karl","123456","sout@gmail.com");
        int i = memberDAO.saveMember(member);
//        `username` VARCHAR(32) NOT NULL UNIQUE,
//        在创建数据库的时候已经设置了用户名不能重复的要求
        System.out.println(i);

    }
}
