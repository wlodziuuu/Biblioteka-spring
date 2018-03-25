package pl.brawanski.bibliotekaspring.utils;

import pl.brawanski.bibliotekaspring.model.Order;
import pl.brawanski.bibliotekaspring.model.Publication;
import pl.brawanski.bibliotekaspring.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DataReader {
    private Scanner sc;

    public DataReader() {
        sc = new Scanner(System.in);
    }

    public void close() {
        sc.close();
    }

    public int getInt() throws NumberFormatException{
        int number = 0;
        try {
            number = sc.nextInt();
        }catch(InputMismatchException ex){
            throw new NumberFormatException("Liczba wprowadzona w niepoprawnej formie");
        }finally {
            sc.nextLine();
        }
        return number;
    }

    public Publication readAndCreatePublication() throws InputMismatchException{
        System.out.println("Tytuł: ");
        String title = sc.nextLine();
        System.out.println("Autor: ");
        String author = sc.nextLine();
        System.out.println("Wydawnictwo: ");
        String publisher = sc.nextLine();
        System.out.println("ISBN: ");
        String isbn = sc.nextLine();
        int pages = 0;
        try {
            System.out.println("Ilość stron: ");
            pages = sc.nextInt();
            sc.nextLine();
        }catch (InputMismatchException e){
            throw e;
        }
        return new Publication(title, publisher, author, pages, isbn);
    }


    public User readAndCreateUser() {
        System.out.println("Imię: ");
        String firstName = sc.nextLine();
        System.out.println("Nazwisko: ");
        String lastName = sc.nextLine();
        System.out.println("PESEL: ");
        String pesel = sc.nextLine();

        return new User(firstName, lastName, pesel);
    }

    public Order readAndCreateOrder() {
        System.out.println("Podaj szczegóły dotyczące zamówienia: ");
        String details = sc.nextLine();

        return new Order(details);
    }

    public String readPesel(){
        System.out.println("Podaj pesel");
        return sc.nextLine();
    }
    public String readTitle(){
        System.out.println("Podaj tytuł");
        return sc.nextLine();
    }
}
