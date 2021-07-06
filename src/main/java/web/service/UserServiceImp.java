package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDAO;
import web.dao.UserDAO;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder){
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImp(){}

    @Override
    @Transactional
    public List<User> listUsers() {
        return userDAO.listUsers();
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
        Set<Role> roles = new HashSet<>();
        if(user.getId() == 1){
            roles.add(roleDAO.getRoleById(1));
        } else {
            roles.add(roleDAO.getRoleById(2));
        }
        user.setRoles(roles);
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getRoleById(2));
        user.setRoles(roles);
        userDAO.addUser(user);
    }

    @Override
    public User getUserByLogin(String name) {
        return findByUsername(name);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void addInitData() {
        if(userDAO.getUser(1)==null) {
            User admin = new User("ADMIN", 1,
                    1, passwordEncoder.encode("100"));
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role1);
            admin.setRoles(roles);
            roleDAO.addRole(role1);
            roleDAO.addRole(role2);
            userDAO.addUser(admin);
        }
    }
}
