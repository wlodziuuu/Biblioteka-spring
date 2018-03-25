package pl.brawanski.bibliotekaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.brawanski.bibliotekaspring.dao.OrderDao;
import pl.brawanski.bibliotekaspring.dao.PublicationDao;
import pl.brawanski.bibliotekaspring.dao.UserDao;
import pl.brawanski.bibliotekaspring.model.Order;
import pl.brawanski.bibliotekaspring.model.Publication;
import pl.brawanski.bibliotekaspring.model.User;
import pl.brawanski.bibliotekaspring.utils.DataReader;

import javax.persistence.EntityManager;

@SpringBootApplication
public class BibliotekaSpringApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BibliotekaSpringApplication.class, args);
		LibraryControl control = ctx.getBean(LibraryControl.class);
		control.controlLoop();
		ctx.close();
	}

}
