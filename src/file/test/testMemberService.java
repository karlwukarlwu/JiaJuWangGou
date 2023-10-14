package file.test;

import file.entity.Member;
import file.service.MemberService;
import file.service.imp.MemberServiceImp;
import org.junit.Test;

/**
 * Karl Rules!
 * 2023/9/17
 * now File Encoding is UTF-8
 */
public class testMemberService {
    private MemberService memberService = new MemberServiceImp();
    @Test
    public void t1(){
        if (memberService.isExistsUsername("admin")) {
            System.out.println("用户名存在");
        } else {
            System.out.println("用户名不存在");
        }
    }
    @Test
    public void t2(){
        Member member = new Member(null,"karl2","123456","123123@qq.com");
        if (memberService.register(member)) {
            System.out.println("注册成功");
        } else {
            System.out.println("注册失败");
        }
    }
    @Test
    public void t3(){
        Member member = new Member(null,"karl2","123456",null);
        if (memberService.login(member) != null) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
    }
}
