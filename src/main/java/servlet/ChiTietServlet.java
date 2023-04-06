package servlet;

import dto.UserDTO;
import dto.VideoCustomDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.FavoriteService;
import service.VideoService;

import java.io.IOException;

@WebServlet({"/showChiTiet/" , "/user/chiTietUnlike/" , "/user/chiTietLike/"})
public class ChiTietServlet extends HttpServlet {
    private VideoService service = new VideoService();
    private FavoriteService favoriteService = new FavoriteService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if(uri.contains("chiTietUnlike")){
            favoriteService.unlike(request,response);
        }
        else if(uri.contains("chiTietLike")){
            favoriteService.like(request,response);
        }



        service.showChiTiet(request, response);

        request.getRequestDispatcher("/chitiet.jsp").forward(request , response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
