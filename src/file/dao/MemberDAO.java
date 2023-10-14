package file.dao;

import file.entity.Member;

/**
 * Karl Rules!
 * 2023/9/6
 * now File Encoding is UTF-8
 */
public interface MemberDAO {
    public Member queryMemberByUsername(String username);

    //1是成功 -1是失败
    public int saveMember(Member member);

    public Member queryMemberByUsernameAndPassword
            (String username, String password);

}
