package repository;

import db_connection.Connect;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserRepository {

    public static void add(User object){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    public static void update(User object){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(object);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    public static void delete(User object){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            User user = entityManager.merge(object);
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    public List<User> getAll(){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select u from User u";
        TypedQuery<User> typedQuery = entityManager.createQuery(jpql , User.class);
        List<User> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public List<User> getAllByPage(int page){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();

        String jpql = "Select u from User u";
        TypedQuery<User> typedQuery = entityManager.createQuery(jpql , User.class);
        typedQuery.setFirstResult((page-1) *10);
        typedQuery.setMaxResults(10);
        List<User> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public User getById(String id){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();

        try{
            String jpql = "Select u from User u where u.id = :id";
            TypedQuery<User> typedQuery = entityManager.createQuery(jpql , User.class);
            typedQuery.setParameter("id" , id);
            User object = typedQuery.getSingleResult();
            entityManager.close();
            return object;
        }catch (NoResultException e){
            return null;
        }


    }

    public int getTotalPage(int pageSize){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();

        String jpql1 = "Select count(u) from User u";
        TypedQuery<Long> query = entityManager.createQuery(jpql1 , Long.class);
        double resultOfQuery = query.getSingleResult();
        double pageDouble = resultOfQuery / pageSize;
        return  (int) Math.ceil(pageDouble);

    }


}
