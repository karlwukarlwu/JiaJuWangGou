package file.entity;

/**
 * Karl Rules!
 * 2023/9/5
 * now File Encoding is UTF-8
 */
public class Member {
    //跟数据库相关的这里设置成Integer
    private Integer id;
    private String username;
    private String password;
    private String email;

    public Member() {
    }
    //这里涉及到int的最好用Integer 因为这样可以接受null的值
    public Member(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
