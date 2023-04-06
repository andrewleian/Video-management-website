package service;

import dto.FavoriteDTO;
import dto.ShareDTO;
import dto.UserDTO;
import dto.VideoDTO;
import entity.Favorite;
import entity.Share;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import repository.FavoriteRepository;
import repository.ShareRepository;
import util.Utils;

import java.sql.Date;
import java.util.List;

public class ShareService {
    private ShareRepository repository = new ShareRepository();
    private ModelMapper _modelMapper = new ModelMapper();

    public void save(ShareDTO object) {
        Share obj = _modelMapper.map(object, Share.class);
        repository.add(obj);
    }

    public List<ShareDTO> getAll(){
        List<Share> shareList = repository.getAll();
        List<ShareDTO> shareDTOList = _modelMapper.map(shareList , new TypeToken<List<ShareDTO>>(){}.getType());
        return shareDTOList;
    }

    public List<ShareDTO> getAllByVideoId(String videoId){
        List<Share> shareList = repository.getAllByVideoId(videoId);
        List<ShareDTO> shareDTOList = _modelMapper.map(shareList , new TypeToken<List<ShareDTO>>(){}.getType());
        return shareDTOList;
    }

    public ShareDTO getById(int id){
        Share share = repository.getById(id);
        ShareDTO shareDTO = _modelMapper.map(share , ShareDTO.class);
        return shareDTO;
    }
    public void update(ShareDTO object) {
        Share obj = _modelMapper.map(object, Share.class);
        repository.update(obj);
    }

    public void delete(ShareDTO object) {
        Share obj = _modelMapper.map(object, Share.class);
        repository.delete(obj);
    }

    public void share(HttpServletRequest request, HttpServletResponse response) {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userSession");
        String videoId = request.getParameter("videoId");
        String email = request.getParameter("email");
        String emails[] = email.split(";");
        for (int i = 0; i < emails.length; i++) {
            ShareDTO shareDTO = new ShareDTO();
            shareDTO.setUser(userDTO);
            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(videoId);
            shareDTO.setVideo(videoDTO);
            shareDTO.setEmails(emails[i]);
            shareDTO.setShareDate(new Date(System.currentTimeMillis()));
            save(shareDTO);
            Utils.sendMail(emails[i] , userDTO.getEmail() +" dã chia sẻ cho bạn 1 video" , "http://localhost:8080/showChiTiet/?videoId="+videoId);
        }
    }

}
