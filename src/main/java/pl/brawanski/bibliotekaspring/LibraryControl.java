package pl.brawanski.bibliotekaspring;

import org.springframework.beans.factory.annotation.Autowired;
import pl.brawanski.bibliotekaspring.dao.OrderDao;
import pl.brawanski.bibliotekaspring.dao.PublicationDao;
import pl.brawanski.bibliotekaspring.dao.UserDao;
import pl.brawanski.bibliotekaspring.model.Order;
import pl.brawanski.bibliotekaspring.model.Publication;
import pl.brawanski.bibliotekaspring.model.User;
import pl.brawanski.bibliotekaspring.utils.DataReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;


public class LibraryControl {

    private DataReader dataReader;
    private UserDao userDao;
    private PublicationDao publicationDao;
    private OrderDao orderDao;


    @Autowired
    public LibraryControl(UserDao userDao, OrderDao orderDao, PublicationDao publicationDao) {
        dataReader = new DataReader();
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.publicationDao = publicationDao;
    }

    public void controlLoop() {
        Option option = null;
        while (option != Option.EXIT) {
            try {
                printOptions();
                option = Option.createFromInt(dataReader.getInt());
                switch (option) {
                    case ADD_PUBLICATION:
                        addPublication();
                        break;
                    case PRINT_PUBLICATIONS:
                        printPublications();
                        break;
                    case ADD_USER:
                        addUser();
                        break;
                    case PRINT_USERS:
                        printUsers();
                        break;
                    case ADD_ORDER:
                        addOrder();
                        break;
                    case PRINT_ORDERS:
                        printOrders();
                        break;
                    case BORROW_PUBLICATION:
                        borrowPublication();
                        break;
                    case PRINT_BORROWED:
                        printBorrowed();
                        break;
                    case EXIT:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono niepoprawne dane, publikacji nie dodano");
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("Wybrana opcja nie istnieje, wybierz ponownie:");
            }
        }
        dataReader.close();
    }

    private void printOptions() {
        System.out.println("Wybierz opcję: ");
        for (Option o : Option.values()) {
            System.out.println(o);
        }
    }

    private void addPublication() {
        Publication pub = dataReader.readAndCreatePublication();
        publicationDao.save(pub);
    }

    private void printPublications() {
        List<Publication> pubs =  publicationDao.getAll();
        pubs.forEach(System.out::println);
    }

    private void addUser() {
        User user = dataReader.readAndCreateUser();
        userDao.save(user);
    }

    private void printUsers() {
        List<User> users = userDao.getAll();
        users.forEach(System.out::println);
    }

    private void addOrder(){
        Order order = dataReader.readAndCreateOrder();
        System.out.println("Podaj pesel użytkownika do którego chcesz przypisać zamówienie: ");
        String pesel = dataReader.readPesel();
        Long id = userDao.getIdByPesel(pesel);
        User user = userDao.get(id);
        orderDao.save(order);
        user.setOrder(order);
        userDao.update(user);
        order.setUser(user);
        orderDao.update(order);
    }

    private void printOrders(){
        List<Order> orders = orderDao.getAll();
        orders.forEach(System.out::println);
    }

    private void borrowPublication(){
        String pesel = dataReader.readPesel();
        String title = dataReader.readTitle();
        Long idUser = userDao.getIdByPesel(pesel);
        User user = userDao.get(idUser);
        Long idPub = publicationDao.getIdByTitle(title);
        Publication pub = publicationDao.get(idPub);
        orderDao.addPublicationToOrder(user.getOrder().getId(),pub);
    }

    private void printBorrowed(){
        String pesel = dataReader.readPesel();
        Long idUser = userDao.getIdByPesel(pesel);
        User user = userDao.get(idUser);
        user.getOrder().getPublications().forEach(System.out::println);
    }

    private enum Option {
        EXIT(0, "Wyjście z programu"),
        ADD_USER(1, "Dodanie nowego użytkownika"),
        ADD_ORDER(2, "Dodanie zamówienia"),
        ADD_PUBLICATION(3, "Dodanie publikacji"),
        PRINT_USERS(4, "Wyświetlenie listy użytkowników"),
        PRINT_ORDERS(5, "Wyświetlenie zamówień"),
        PRINT_PUBLICATIONS(6, "Wyświetlenie dostępnych publikacji"),
        BORROW_PUBLICATION(7, "Wypożyczanie publikacji"),
        PRINT_BORROWED(8, "Wyświetl wypożyczone publikacje");
        private int value;
        private String description;

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        public static Option createFromInt(int option) throws NoSuchElementException {
            Option result = null;
            try {
                result = Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException("Brak elementu o wskazanym ID");
            }

            return result;
        }
    }
}
