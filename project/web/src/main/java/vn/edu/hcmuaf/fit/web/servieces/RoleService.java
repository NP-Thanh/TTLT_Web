package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.RoleDao;
import vn.edu.hcmuaf.fit.web.model.Role;

public class RoleService {
    static RoleDao roleDao = new RoleDao();

    public Role getRoleById(int id) {
        return roleDao.getRoleById(id);
    }
}
