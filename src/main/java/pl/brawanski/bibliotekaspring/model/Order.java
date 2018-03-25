package pl.brawanski.bibliotekaspring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "orders")
public class Order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;
    private String details;
    @OneToOne(mappedBy = "order")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_publications",
            joinColumns = {@JoinColumn(name="order_id",referencedColumnName="id_order")},
            inverseJoinColumns = {@JoinColumn(name="publication_id",referencedColumnName="id_publication")}
    )
    private List<Publication> publications;

    Order(){}

    public Order(String details) {
        this.details = details;
    }

    public Order(String details, User user){
        this.details = details;
        setUser(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", details='" + details + '\'' +
                '}';
    }
}
