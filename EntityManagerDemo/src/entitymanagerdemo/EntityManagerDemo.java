/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Address;
import model.Customer;
import java.util.List;

/**
 *
 * @author Bukhoree
 */

public class EntityManagerDemo {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        initializeEntityManager();

        createData();

        printAllCustomers();

        printCustomersByCity("Bangkok");

        closeEntityManager();
    }

    private static void initializeEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private static void closeEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void createData() {
        entityManager.getTransaction().begin();

        Address address1 = new Address(1L, "123/4 Viphavadee Rd.", "Bangkok", "10900", "TH");
        Customer customer1 = new Customer(1L, "John", "Henry", "jh@mail.com", address1);
        address1.setCustomerFk(customer1);

        Address address2 = new Address(2L, "123/5 Viphavadee Rd.", "Bangkok", "10900", "TH");
        Customer customer2 = new Customer(2L, "Marry", "Jane", "mj@mail.com", address2);
        address2.setCustomerFk(customer2);

        Address address3 = new Address(3L, "543/21 Fake Rd.", "Nonthaburi", "20900", "TH");
        Customer customer3 = new Customer(3L, "Peter", "Parker", "pp@mail.com", address3);
        address3.setCustomerFk(customer3);

        Address address4 = new Address(4L, "678/90 Unreal Rd.", "Pathumthani", "30500", "TH");
        Customer customer4 = new Customer(4L, "Bruce", "Wayne", "bw@mail.com", address4);
        address4.setCustomerFk(customer4);

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.persist(customer3);
        entityManager.persist(customer4);

        entityManager.getTransaction().commit();
    }

    public static void printAllCustomers() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();

        for (Customer customer : customers) {
            System.out.println("First Name: " + customer.getFirstname());
            System.out.println("Last Name: " + customer.getLastname());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Street: " + customer.getAddress().getStreet());
            System.out.println("City: " + customer.getAddress().getCity());
            System.out.println("Zip Code: " + customer.getAddress().getZipcode());
            System.out.println("Country: " + customer.getAddress().getCountry());
            System.out.println("------------------------------------------");
        }
    }

    public static void printCustomersByCity(String city) {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.address.city = :city", Customer.class);
        query.setParameter("city", city);
        List<Customer> customers = query.getResultList();

        for (Customer customer : customers) {
            System.out.println("First Name: " + customer.getFirstname());
            System.out.println("Last Name: " + customer.getLastname());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Street: " + customer.getAddress().getStreet());
            System.out.println("City: " + customer.getAddress().getCity());
            System.out.println("Zip Code: " + customer.getAddress().getZipcode());
            System.out.println("Country: " + customer.getAddress().getCountry());
            System.out.println("------------------------------------------");
        }
    }
}

