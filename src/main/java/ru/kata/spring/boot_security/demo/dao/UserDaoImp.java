package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        user.addRole(new Role(1L, "ROLE_USER"));
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.getReference(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.getReference(User.class, id);
    }

    public User getUserByName(String name) {
        List<User> queryList = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.firstName = :custName", User.class)
                .setParameter("custName", name)
                .getResultList();
        if (queryList.size() != 0) {
            return queryList.get(0);
        }
        return null;
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

}
