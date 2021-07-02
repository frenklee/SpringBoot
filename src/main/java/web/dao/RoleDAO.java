package web.dao;

import web.model.Role;

public interface RoleDAO {
    public Role getRoleById(int id);
    public void addRole(Role role);
}
