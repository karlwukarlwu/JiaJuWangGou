package file.service;

import file.entity.Admin;

/**
 * Karl Rules!
 * 2023/9/22
 * now File Encoding is UTF-8
 */
public interface AdminService {
    public Admin isAdmin(String username, String password);

}
