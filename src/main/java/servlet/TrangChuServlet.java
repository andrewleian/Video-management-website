package servlet;

import dto.FavoriteDTO;
import dto.UserDTO;
import dto.VideoCustomDTO;
import dto.VideoDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.FavoriteService;
import service.VideoService;
import util.Utils;

import java.io.IOException;
import java.util.List;

@WebServlet({"/showTrangChu" , "/showTrangChu/" , "/user/trangChuLike/" , "/user/trangChuUnlike/" , ""})
public class TrangChuServlet extends HttpServlet {
    private VideoService service = new VideoService();
    private FavoriteService favoriteService = new FavoriteService();
    private int currentPage;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userSession = (UserDTO) request.getSession().getAttribute("userSession");

        String uri = request.getRequestURI();
        if(uri.contains("trangChuLike")){
            favoriteService.like(request,response);
        }
        else if(uri.contains("trangChuUnlike")){
            favoriteService.unlike(request,response);
        }

        currentPage = Utils.checkPage(request,response);;

        if(userSession != null){
            request.setAttribute("videoList" , service.getAllVideoCustomDTO(userSession.getId() , currentPage));
        }else {
            request.setAttribute("videoList" , service.getAllVideoCustomDTO("" , currentPage));
        }
        request.setAttribute("totalPage" , service.getTotalPage(6));
        request.getRequestDispatcher("/trangchu.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
