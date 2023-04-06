package service;

import dto.FavoriteCustomDTO;
import dto.FavoriteDTO;
import dto.UserDTO;
import dto.VideoDTO;
import entity.Favorite;
import entity.FavoriteCustom;
import entity.User;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import repository.FavoriteRepository;
import repository.UserRepository;

import java.sql.Date;
import java.util.List;

public class FavoriteService {
    private FavoriteRepository repository = new FavoriteRepository();
    private ModelMapper _modelMapper = new ModelMapper();

    public void save(FavoriteDTO object) {
        Favorite obj = _modelMapper.map(object, Favorite.class);
        repository.add(obj);
    }

    public List<FavoriteDTO> getAll(){
        List<Favorite> favoriteList = repository.getAll();
        List<FavoriteDTO> favoriteDTOList = _modelMapper.map(favoriteList , new TypeToken<List<FavoriteDTO>>(){}.getType());
        return favoriteDTOList;
    }

    public List<FavoriteDTO> getAllByVideoId(String videoId){
        List<Favorite> favoriteList = repository.getAllByVideoId(videoId);
        List<FavoriteDTO> favoriteDTOList = _modelMapper.map(favoriteList , new TypeToken<List<FavoriteDTO>>(){}.getType());
        return favoriteDTOList;
    }



    public List<FavoriteCustomDTO> getAllFavoriteCustomDTO(){
        List<FavoriteCustom> favoriteCustomList = repository.getAllFavoriteCustom();
        List<FavoriteCustomDTO> favoriteCustomDTOList = _modelMapper.map(favoriteCustomList , new TypeToken<List<FavoriteCustomDTO>>(){}.getType());
        return favoriteCustomDTOList;
    }

    public List<FavoriteDTO> getByUserId(){
        List<Favorite> favoriteList =  repository.getByUser("user4");
        List<FavoriteDTO> favoriteDTOList = _modelMapper.map(favoriteList , new TypeToken<List<FavoriteDTO>>(){}.getType());
        return favoriteDTOList;
    }

    public FavoriteDTO getById(int id){
        Favorite favorite = repository.getById(id);
        FavoriteDTO favoriteDTO = _modelMapper.map(favorite , FavoriteDTO.class);
        return favoriteDTO;
    }
    public void update(FavoriteDTO object) {
        Favorite obj = _modelMapper.map(object, Favorite.class);
        repository.update(obj);
    }

    public void delete(FavoriteDTO object) {
        Favorite obj = _modelMapper.map(object, Favorite.class);
        repository.delete(obj);
    }

    public void like(HttpServletRequest request, HttpServletResponse response) {
        String videoId = request.getParameter("videoId");
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userSession");

        if(checkFavoriteExist(userDTO.getId() , videoId)){
            return;
        }

        User user = _modelMapper.map(userDTO , User.class);
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(videoId);

        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setId(0);
        favoriteDTO.setUser(userDTO);
        favoriteDTO.setVideo(videoDTO);
        favoriteDTO.setLikeDate(new Date(System.currentTimeMillis()));

        save(favoriteDTO);

    }

    public boolean checkFavoriteExist (String userId , String videoId){
        return  repository.checkExist(userId , videoId);
    }

    public void unlike(HttpServletRequest request, HttpServletResponse response) {
        String videoId = request.getParameter("videoId");
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userSession");
        Favorite favorite = repository.getFavorite(userDTO.getId() , videoId);

        if(favorite != null){
            repository.delete(favorite);
        }
    }

}
