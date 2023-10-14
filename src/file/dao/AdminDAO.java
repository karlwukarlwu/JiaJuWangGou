package file.dao;

import file.entity.Admin;

/**
 * Karl Rules!
 * 2023/9/22
 * now File Encoding is UTF-8
 */
public interface AdminDAO {
    public Admin isAdmin(String username, String password);
}
