package file.service;

import file.entity.Member;

/**
 * Karl Rules!
 * 2023/9/6
 * now File Encoding is UTF-8
 */
public interface MemberService {
    //用户注册
    public boolean register(Member member);
    //用户名是否存在
    public boolean isExistsUsername(String username);

    public Member login(Member member);
}
