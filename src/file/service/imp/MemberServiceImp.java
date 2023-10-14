package file.service.imp;

import file.dao.MemberDAO;
import file.dao.imp.MemberDAOImpl;
import file.entity.Member;
import file.service.MemberService;

/**
 * Karl Rules!
 * 2023/9/17
 * now File Encoding is UTF-8
 */
public class MemberServiceImp implements MemberService {
    //这里要建一个实现类
    private MemberDAO memberDAO =  new MemberDAOImpl();

    //用户注册
    @Override
    public boolean register(Member member) {
        //是否成功注册？
        return memberDAO.saveMember(member) == 1 ? true : false;
    }

    //用户名是否存在
    @Override
    public boolean isExistsUsername(String username) {
        //三元运算符 找到返回true 找不到这个方法返回null null对应的是false
        return memberDAO.queryMemberByUsername(username) == null ? false : true;

    }

    //用户登录
    //这里的参数最好用自己定义的数据对象 方便维护
    //对象在前端创建
    @Override
    public Member login(Member member) {
        return memberDAO.queryMemberByUsernameAndPassword
                (member.getUsername(), member.getPassword());
    }
}
