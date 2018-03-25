package pl.brawanski.bibliotekaspring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.brawanski.bibliotekaspring.model.Publication;
import pl.brawanski.bibliotekaspring.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PublicationDao {

    private EntityManager entityManager;

    @Autowired
    public PublicationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Publication pub){
        entityManager.persist(pub);
    }

    public Publication get(Long id){
        Publication find = entityManager.find(Publication.class, id);
        return find;
    }

    public Long getIdByTitle(String title){
        final String get = "SELECT p FROM Publication p WHERE p.title = :title";
        TypedQuery<Publication> query = entityManager.createQuery(get, Publication.class);
        query.setParameter("title", title);
        Publication pub = query.getSingleResult();
        return pub.getId();
    }

    public List<Publication> getAll(){
        final String getAll = "SELECT p FROM Publication p";
        TypedQuery<Publication> query = entityManager.createQuery(getAll, Publication.class);
        List<Publication> result = query.getResultList();
        return result;
    }
}
