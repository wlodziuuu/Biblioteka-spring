package pl.brawanski.bibliotekaspring.dao;

import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderDao {
    private EntityManager entityManager;

    @Autowired
    public OrderDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Order order){
        entityManager.persist(order);
    }

    public Order get(Long id) {
        Order order = entityManager.find(Order.class, id);
        return order;
    }

    public void update(Order order){
        entityManager.merge(order);
    }

    public void addPublicationToOrder(Long orderId, Publication... publications) {
        Order order = get(orderId);
        if(order != null) {
            for(Publication p: publications) {
                order.getPublications().add(p);
            }
        }
    }

    public List<Order> getAll(){
        final String getAll = "SELECT o FROM Order o";
        TypedQuery<Order> query = entityManager.createQuery(getAll, Order.class);
        List<Order> result = query.getResultList();
        return result;
    }

}
