package servlet;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.UserService;

import java.io.IOException;

@WebServlet({"/user/showEditProfile" , "/user/editProfile"})
public class EditProfileServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userSession");
        request.setAttribute("object" , userDTO);
        request.getRequestDispatcher("/editprofile.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.contains("editProfile")){
            service.editProfile(request,response);
        }
        doGet(request,response);
    }
}
