package repository;

import db_connection.Connect;
import entity.Share;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ShareRepository {
    public static void add(Share object){
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

    public static void update(Share object){
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

    public static void delete(Share object){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(object);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    public List<Share> getAll(){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select s from Share s";
        TypedQuery<Share> typedQuery = entityManager.createQuery(jpql , Share.class);
        List<Share> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public List<Share> getAllByVideoId(String videoId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select s from Share s where s.video.id = :videoId";
        TypedQuery<Share> typedQuery = entityManager.createQuery(jpql , Share.class);
        typedQuery.setParameter("videoId" , videoId);
        List<Share> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public Share getById(int id){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select s from Share s where s.id = :id";
        TypedQuery<Share> typedQuery = entityManager.createQuery(jpql , Share.class);
        typedQuery.setParameter("id" , id);
        Share object = typedQuery.getSingleResult();
        entityManager.close();
        return object;
    }
}
