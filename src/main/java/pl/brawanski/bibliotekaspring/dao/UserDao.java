package pl.brawanski.bibliotekaspring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brawanski.bibliotekaspring.model.Order;
import pl.brawanski.bibliotekaspring.model.Publication;
import pl.brawanski.bibliotekaspring.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao {
    private EntityManager entityManager;

    @Autowired
    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User user){
        entityManager.persist(user);
    }
    public User get(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }
    public void update(User user) {
        entityManager.merge(user);
    }
    public void delete(User user) {
        entityManager.remove(user);
    }

    public User getUserByPesel(String pesel){
        final String get = "SELECT u FROM User u WHERE u.pesel = :pesel";
        TypedQuery<User> query = entityManager.createQuery(get, User.class);
        query.setParameter("pesel", pesel);
        User user = query.getSingleResult();
        return user;
    }

    public List<User> getAll(){
        final String getAll = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(getAll, User.class);
        List<User> result = query.getResultList();
        return result;
    }

    public void setOrder(User user, Order order){
        entityManager.merge(user);
        user.setOrder(order);
        entityManager.persist(user);
    }


}
