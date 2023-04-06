package repository;

import db_connection.Connect;
import dto.VideoCustomDTO;
import dto.VideoCustomIDAndTitle;
import entity.User;
import entity.Video;
import entity.VideoCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class VideoRepository {
    private int totalPage;


    public static void add(Video object){
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

    public static void update(Video object){
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

    public static void delete(Video object){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            Video video = entityManager.merge(object);
            entityManager.remove(video);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }

    public List<Video> getAll(){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select v from Video v";
        TypedQuery<Video> typedQuery = entityManager.createQuery(jpql , Video.class);
        List<Video> list = typedQuery.getResultList();
        entityManager.clear();
        entityManager.close();
        return list;
    }

    public List<Video> getAll(String userId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            String jpql = "Select v from Video v join Favorite f on v.id = f.video.id where f.user.id = :userId";
            TypedQuery<Video> typedQuery = entityManager.createQuery(jpql , Video.class);
            typedQuery.setParameter("userId" , userId);
            List<Video> list = typedQuery.getResultList();
            entityManager.clear();
            entityManager.close();
            return list;
        }catch (Exception e){
            return null;
        }
    }

    public List<Video> getAllByPage(int page){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();

        String jpql = "Select v from Video v";
        TypedQuery<Video> typedQuery = entityManager.createQuery(jpql , Video.class);
        typedQuery.setFirstResult((page-1) *10);
        typedQuery.setMaxResults(10);
        List<Video> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public List<Object[]> getAllVideoTitle(){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        try {
            String jpql = "Select v.id as id , v.title as title from Video v";
            TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql , Object[].class);
            List<Object[]> list = typedQuery.getResultList();
            entityManager.clear();
            entityManager.close();
            return list;
        }catch (Exception e){
            return null;
        }
    }

    public List<VideoCustom> getAllVideoCustom(String userId , int page){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();

        String sql = "SELECT Video.* , CASE WHEN Favorite.Id IS NULL THEN FALSE ELSE TRUE END AS 'Like' " +
                "FROM Video " +
                "LEFT JOIN Favorite ON Video.Id = Favorite.VideoId AND Favorite.UserId = '"+userId+"' " +
                "where Video.Active = 1 " +
                "order by Video.views desc";
        Query query = entityManager.createNativeQuery(sql,VideoCustom.class);
        query.setFirstResult((page-1) * 6);
        query.setMaxResults(6);
        List<VideoCustom> list = query.getResultList();
        entityManager.close();
        return list;
    }

//    public List<VideoCustom> getAllVideoCustomIJ(String userId){
//        EntityManager entityManager = Connect.entityManager();
//        entityManager.getTransaction().begin();
//        String sql = "SELECT Video.* , CASE WHEN Favorite.Id IS NULL THEN FALSE ELSE TRUE END AS 'Like' " +
//                "FROM Video " +
//                "inner JOIN Favorite ON Video.Id = Favorite.VideoId AND Favorite.UserId = 'user4' where Video.Active = 1";
//        Query query = entityManager.createNativeQuery(sql,VideoCustom.class);
//        List<VideoCustom> list = query.getResultList();
//        entityManager.close();
//        return list;
//    }

    public VideoCustom getVideoCustom(String videoId , String userId){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String sql = "SELECT Video.* , CASE WHEN Favorite.Id IS NULL THEN FALSE ELSE TRUE END AS 'Like' " +
                "FROM Video " +
                "LEFT JOIN Favorite ON Video.Id = Favorite.VideoId AND Favorite.UserId = '"+userId+"' where Video.Active = 1 and Video.Id = '"+videoId+"' ";
        Query query = entityManager.createNativeQuery(sql,VideoCustom.class);
        VideoCustom object = (VideoCustom) query.getSingleResult();
        entityManager.close();
        return object;
    }

    public Video getById(String id){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();
        String jpql = "Select v from Video v where v.id = :id";
        TypedQuery<Video> typedQuery = entityManager.createQuery(jpql , Video.class);
        typedQuery.setParameter("id" , id);
        Video object = typedQuery.getSingleResult();
        entityManager.close();
        return object;
    }



    public int getTotalPage(int pageSize){
        EntityManager entityManager = Connect.entityManager();
        entityManager.getTransaction().begin();

        String jpql1 = "Select count(v) from Video v";
        TypedQuery<Long> query = entityManager.createQuery(jpql1 , Long.class);
        double resultOfQuery = query.getSingleResult();
        double pageDouble = resultOfQuery / pageSize;
        return  (int) Math.ceil(pageDouble);
    }

    public static void main(String[] args) {
//        List<VideoCustom> list = new VideoRepository().getAllVideoCustom();
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).toString());
//        }

    }
}
