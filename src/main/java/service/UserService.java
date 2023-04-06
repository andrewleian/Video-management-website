package service;

import dto.UserDTO;
import entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import repository.UserRepository;
import util.Utils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class UserService {
    private ModelMapper _modelMapper = new ModelMapper();
    private UserRepository repository = new UserRepository();
    public void save(UserDTO object) {
        User obj = _modelMapper.map(object, User.class);
        repository.add(obj);
    }

    public List<UserDTO> getAll(){
        List<User> userList = repository.getAll();
        List<UserDTO> userDTOList = _modelMapper.map(userList , new TypeToken<List<UserDTO>>(){}.getType());
        return userDTOList;
    }

    public List<UserDTO> getAllByPage(int page){
        List<User> userList = repository.getAllByPage(page);
        List<UserDTO> userDTOList = _modelMapper.map(userList , new TypeToken<List<UserDTO>>(){}.getType());
        return userDTOList;
    }

    public UserDTO getById(String id){
        User user = repository.getById(id);
        UserDTO userDTO = _modelMapper.map(user , UserDTO.class);
        return userDTO;
    }
    public void update(UserDTO object) {
        User obj = _modelMapper.map(object, User.class);
        repository.update(obj);
    }

    public void delete(UserDTO object) {
        User obj = _modelMapper.map(object, User.class);
        repository.delete(obj);
    }

    public void signUp(HttpServletRequest request, HttpServletResponse response){
        UserDTO object = new UserDTO();
        try {
            BeanUtils.populate(object , request.getParameterMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        object.setAdmin(false);

        //check form validate
        if(!checkValidate(request,response,object)){
            return;
        }

        UserDTO checkExist = getById(object.getId());

        if(checkExist != null){
            request.setAttribute("objectError" , object);
            request.setAttribute("idError" , "Username already exist!");
            return;
        }
        //


        save(object);
        Utils.sendMail(object.getEmail() , "Welcome to FPT ASSIGNMENT APPLICATION" , "Tk: "+object.getFullname() + " , mk: "+object.getPassword());
        request.setAttribute("successMessage" ,"Congratulations, you have successfully created an account");
    }

    public boolean checkValidate(HttpServletRequest request, HttpServletResponse response , UserDTO object){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(object);

        if(!violations.isEmpty()){
            for (ConstraintViolation<UserDTO> violation : violations) {
                String errorField = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                request.setAttribute(errorField+"Error" , message);
            }
            request.setAttribute("objectError" , object);
            return false;
        }
        return true;
    }

    public boolean signIn(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
       String username = request.getParameter("id");
       String password = request.getParameter("password");
       User user = repository.getById(username);

       if(user == null || !user.getPassword().equals(password)){
           request.setAttribute("signInFail" , "Incorrect account or password");
           return false;
       }

        String remember = request.getParameter("remember");

        if(remember != null) {
            Cookie cookie = new Cookie(username, password);
            cookie.setMaxAge(24*60*60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }else {
            Cookie cookie = new Cookie(username, password);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }


           UserDTO userDTO = _modelMapper.map(user , UserDTO.class);
           session.setAttribute("userSession" , userDTO);
           return true;

    }

    public void forgotPassword (HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("id");
        String email = request.getParameter("email");
        User user = repository.getById(username);

        if(user == null || !user.getEmail().equals(email)){
            request.setAttribute("error" , "Incorrect account or email address");
        }else {
            request.setAttribute("success" , "We have sent a new password to your email address");
            Utils.sendMail(email , "Cấp lại mật khẩu FPT Assignment Application" , "Mật khẩu mới của bạn là 12345");
            user.setPassword("12345");
            repository.update(user);
        }
    }


    public void changePassword(HttpServletRequest request, HttpServletResponse response) {
        UserDTO userSession = (UserDTO) request.getSession().getAttribute("userSession");
        String username = request.getParameter("id");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        if(userSession.getId().equals(username) && userSession.getPassword().equals(currentPassword) && newPassword.equals(confirmNewPassword)){
            userSession.setPassword(newPassword);
            update(userSession);
            request.getSession().removeAttribute("userSession");
            request.getSession().setAttribute("userSession" , userSession);
            request.setAttribute("success" ,"Change password successfully");
            return;
        }
        request.setAttribute("error" ,"Change password failed, please check the information again");
    }

    public void editProfile(HttpServletRequest request, HttpServletResponse response) {
        UserDTO userDTO = new UserDTO();
        try {
            BeanUtils.populate(userDTO , request.getParameterMap());
            update(userDTO);
            request.getSession().removeAttribute("userSession");
            request.getSession().setAttribute("userSession" , userDTO);
            request.setAttribute("success" , "Changed information successfully");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalPage (int pageSize){
        return repository.getTotalPage(pageSize);
    }

    public void userUpdate(HttpServletRequest request, HttpServletResponse response) {
        UserDTO object = new UserDTO();
        try {
            BeanUtils.populate(object,request.getParameterMap());

            //check validate
            if(!checkValidate(request,response,object)){
                request.setAttribute("userEdit" , object);
                return;
            }

            //
            update(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        enableCreate(request,response);
    }

    public void enableCreate(HttpServletRequest request , HttpServletResponse response){
        request.setAttribute("isIdEnable" , "");
        request.setAttribute("isDeleteable" , "disabled");
        request.setAttribute("isUpdateable" , "disabled");
    }

}
