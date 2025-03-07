package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Role;

public class RoleDao {
    public Role getRoleById(int id) {
        Role role = (Role) JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("select * from role where id= :id")
                    .bind("id", id)
                    .mapToBean(Role.class)
                    .findOne().orElse(null);
        });
        return role;
    }
}
