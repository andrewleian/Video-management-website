package servlet;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.FavoriteService;
import service.VideoService;

import java.io.IOException;

@WebServlet({"/user/showMyFavorite" , "/user/myFavoriteUnlike/"})
public class MyFavoriteServlet extends HttpServlet {
    private VideoService videoService = new VideoService();
    private FavoriteService favoriteService = new FavoriteService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userSession = (UserDTO) request.getSession().getAttribute("userSession");

        String uri = request.getRequestURI();
        if(uri.contains("myFavoriteUnlike")){
            favoriteService.unlike(request,response);
        }

        request.setAttribute("videoList" , videoService.getAll(userSession.getId()));
        request.getRequestDispatcher("/myfavorite.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
