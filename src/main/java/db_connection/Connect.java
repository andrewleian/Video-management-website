package db_connection;

import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transaction;

public class Connect {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("assignment_java4");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User();
        user.setId("User1");
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static EntityManager entityManager(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("assignment_java4");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

}
