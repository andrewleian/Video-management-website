package service;

import dto.FavoriteDTO;
import dto.UserDTO;
import dto.VideoCustomDTO;
import dto.VideoCustomIDAndTitle;
import dto.VideoDTO;
import entity.Favorite;
import entity.User;
import entity.Video;
import entity.VideoCustom;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import repository.FavoriteRepository;
import repository.UserRepository;
import repository.VideoRepository;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VideoService {
    private VideoRepository repository = new VideoRepository();
    private ModelMapper _modelMapper = new ModelMapper();
    public void save(VideoDTO object) {
        Video obj = _modelMapper.map(object, Video.class);
        repository.add(obj);
    }

    public List<VideoDTO> getAll(){
        List<Video> videoList = repository.getAll();
        List<VideoDTO> videoDTOList = _modelMapper.map(videoList , new TypeToken<List<VideoDTO>>(){}.getType());
        return videoDTOList;
    }

    public List<VideoDTO> getAll(String userId){
        List<Video> videoList = repository.getAll(userId);
        List<VideoDTO> videoDTOList = _modelMapper.map(videoList , new TypeToken<List<VideoDTO>>(){}.getType());
        return videoDTOList;
    }

    public List<VideoDTO> getAllByPage(int page){
        List<Video> videoList = repository.getAllByPage(page);
        List<VideoDTO> videoDTOList = _modelMapper.map(videoList , new TypeToken<List<VideoDTO>>(){}.getType());
        return videoDTOList;
    }

    public VideoDTO getById(String id){
        Video video = repository.getById(id);
        VideoDTO videoDTO = _modelMapper.map(video , VideoDTO.class);
        return videoDTO;
    }
    public void update(VideoDTO object) {
        Video obj = _modelMapper.map(object, Video.class);
        repository.update(obj);
    }

    public void delete(VideoDTO object) {
        Video obj = _modelMapper.map(object, Video.class);
        repository.delete(obj);
    }

    public void saveVideo(HttpServletRequest request, HttpServletResponse response){
        VideoDTO videoDTO = new VideoDTO();
        try {
            BeanUtils.populate(videoDTO , request.getParameterMap());

            if(!checkValidate(request,response,videoDTO)){
                enableCreate(request,response);
                return;
            }

            Part image = request.getPart("poster");
            String fileName = Path.of(image.getSubmittedFileName()).getFileName().toString();
            String realPath ="/Users/lehungyen/Documents/Intellij java 4/assignment_java4/src/main/webapp/img";
            if(!Files.exists(Path.of(realPath))) {
                Files.createDirectory(Path.of(realPath));
            }
            image.write(realPath+"/"+fileName);
            videoDTO.setPoster(fileName);
            save(videoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableCreate(request,response);
    }

    public void updateVideo(HttpServletRequest request, HttpServletResponse response){
        VideoDTO videoDTO = new VideoDTO();
        try {
            BeanUtils.populate(videoDTO , request.getParameterMap());

            if(!checkValidate(request,response,videoDTO)){
                disableCreate(request,response);
                return;

            }

            Part image = request.getPart("poster");
            String fileName = Path.of(image.getSubmittedFileName()).getFileName().toString();
            String realPath ="/Users/lehungyen/Documents/Intellij java 4/assignment_java4/src/main/webapp/img";
            if(!Files.exists(Path.of(realPath))) {
                Files.createDirectory(Path.of(realPath));
            }
            image.write(realPath+"/"+fileName);
            videoDTO.setPoster(fileName);
            update(videoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableCreate(request,response);
    }

    public void deleteVideo(HttpServletRequest request, HttpServletResponse response){
        VideoDTO videoDTO = new VideoDTO();
        try {
            BeanUtils.populate(videoDTO , request.getParameterMap());
            delete(videoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableCreate(request,response);
    }

    public void enableCreate(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("isIdEnable" , "");
        request.setAttribute("isCreateable" , "");
        request.setAttribute("isDeleteable" , "disabled");
        request.setAttribute("isUpdateable" , "disabled");
    }
    public List<VideoCustomDTO> getAllVideoCustomDTO(String userId , int page){
        List<VideoCustom> videoCustomList = repository.getAllVideoCustom(userId , page);
        List<VideoCustomDTO> videoCustomDTOList = _modelMapper.map(videoCustomList , new TypeToken<List<VideoCustomDTO>>(){}.getType());
        return videoCustomDTOList;
    }

    public VideoCustomDTO getVideoCustomDTO (String videoId , String userId){
        VideoCustom object = repository.getVideoCustom(videoId , userId);
        VideoCustomDTO obj = _modelMapper.map(object , VideoCustomDTO.class);
        return obj;
    }

    public List<Object[]> getAllVideoTitle(){
        return repository.getAllVideoTitle();
    }

    public int getTotalPage(int pageSize){
        return repository.getTotalPage(pageSize);
    }

    public boolean checkValidate(HttpServletRequest request, HttpServletResponse response , VideoDTO object){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<VideoDTO>> violations = validator.validate(object);

        if(!violations.isEmpty()){
            for (ConstraintViolation<VideoDTO> violation : violations) {
                String errorField = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                request.setAttribute(errorField+"Error" , message);
            }
            request.setAttribute("idEdit" , object);
            return false;
        }
        return true;
    }

    public void showChiTiet(HttpServletRequest request, HttpServletResponse response) {
        String videoId = request.getParameter("videoId");
        UserDTO userSession = (UserDTO) request.getSession().getAttribute("userSession");

        // tang so luot views cua video
        Video video = repository.getById(videoId);
        int views = video.getViews();
        views++;
        video.setViews(views);
        repository.update(video);

        //luu video vao cookie
        Cookie cookie = new Cookie("watchedVideo"+videoId , videoId);
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/showChiTiet/");
        response.addCookie(cookie);

        //get list cookie
        Cookie listCookie[] = request.getCookies();
        List<VideoDTO> watchedVideoList = new ArrayList<>();

        if(listCookie != null){
            for (int i = listCookie.length-1; i >=0 ; i--) {
                if(listCookie[i].getName().contains("watchedVideo") && !listCookie[i].getName().contains(videoId)){
                    VideoDTO videoDTO = getById(listCookie[i].getValue());
                    watchedVideoList.add(videoDTO);
                }
                if(watchedVideoList.size() == 4){
                    break;
                }
            }
            request.setAttribute("watchedVideo" , watchedVideoList);
        }

        if(userSession != null){
            request.setAttribute("video" , getVideoCustomDTO(videoId , userSession.getId()));
        }else{
            request.setAttribute("video" , getVideoCustomDTO(videoId , ""));
        }
    }

    public void disableCreate(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("isIdEnable" , "readonly");
        request.setAttribute("isCreateable" , "disabled");
        request.setAttribute("isDeleteable" , "");
        request.setAttribute("isUpdateable" , "");
    }
}
