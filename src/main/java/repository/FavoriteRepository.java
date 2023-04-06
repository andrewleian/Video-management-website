package repository;

import db_connection.Connect;
import entity.Favorite;
import entity.FavoriteCustom;
import entity.User;
import entity.Video;
import entity.VideoCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FavoriteRepository {
    public static void add(Favorite object){
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

    public static void update(Favorite object){
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

    public static void delete(Favorite object){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            object = entityManager.merge(object);
            entityManager.remove(object);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    public List<Favorite> getAll(){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select f from Favorite f";
        TypedQuery<Favorite> typedQuery = entityManager.createQuery(jpql , Favorite.class);
        List<Favorite> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public List<Favorite> getAllByVideoId(String videoId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select f from Favorite f where f.video.id = :videoId";
        TypedQuery<Favorite> typedQuery = entityManager.createQuery(jpql , Favorite.class);
        typedQuery.setParameter("videoId" , videoId);
        List<Favorite> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public List<FavoriteCustom> getAllFavoriteCustom(){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String sql = "select Video.title as VideoTitle , count(Favorite.id) as FavoriteCount , max(Favorite.likeDate) as LatestDate , min(Favorite.likeDate) as OldestDate from Video left join Favorite on Video.id = Favorite.videoid group by Video.id";
        Query query = entityManager.createNativeQuery(sql , FavoriteCustom.class);
        List<FavoriteCustom> list = query.getResultList();
        entityManager.close();
        return list;
    }

    public List<Favorite> getByUser(String userId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select f from Favorite f where f.user.id = :id";
        TypedQuery<Favorite> typedQuery = entityManager.createQuery(jpql , Favorite.class);
        typedQuery.setParameter("id" , userId);
        List<Favorite> favoriteList = typedQuery.getResultList();
        entityManager.close();
        return favoriteList;
    }

    public Favorite getById(int id){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select f from Favorite f where f.id = :id";
        TypedQuery<Favorite> typedQuery = entityManager.createQuery(jpql , Favorite.class);
        typedQuery.setParameter("id" , id);
        Favorite object = typedQuery.getSingleResult();
        entityManager.close();
        return object;
    }

    public boolean checkExist(String userId , String videoId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            String jpql = "Select f from Favorite f where f.user.id = :userId and f.video.id= :videoId";
            TypedQuery<Favorite> typedQuery = entityManager.createQuery(jpql , Favorite.class);
            typedQuery.setParameter("userId" , userId);
            typedQuery.setParameter("videoId" , videoId);
            Favorite object = typedQuery.getSingleResult();
            entityManager.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Favorite getFavorite(String userId , String videoId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            String jpql = "Select f from Favorite f where f.user.id = :userId and f.video.id= :videoId";
            TypedQuery<Favorite> typedQuery = entityManager.createQuery(jpql , Favorite.class);
            typedQuery.setParameter("userId" , userId);
            typedQuery.setParameter("videoId" , videoId);
            Favorite object = typedQuery.getSingleResult();
            entityManager.close();
            return object;
        }catch (Exception e){
            return null;
        }
    }
}
