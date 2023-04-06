package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.FavoriteService;
import service.ShareService;
import service.VideoService;

import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/showReports" , "/admin/favoriteUsers/" , "/admin/shareFriends/"})
public class ReportServlet extends HttpServlet {
    private FavoriteService favoriteService = new FavoriteService();
    private VideoService videoService = new VideoService();
    private ShareService shareService = new ShareService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        List<Object[]> objectList = videoService.getAllVideoTitle();
        Object obj[] = objectList.get(0);
        String videoId = obj[0].toString();

        if(uri.contains("/admin/showReports")){
            request.setAttribute("SFSelected" , videoId);
            request.setAttribute("FUSelected" , videoId);
        }

        if(uri.contains("shareFriends") || uri.contains("favoriteUsers")){
            String videoIdSF = request.getParameter("videoIdSF");
            request.setAttribute("favoriteByVideoId" , shareService.getAllByVideoId(videoIdSF));
            request.setAttribute("SFSelected" , videoIdSF);

            String videoIdFU = request.getParameter("videoIdFU");
            request.setAttribute("favoriteUsers" , favoriteService.getAllByVideoId(videoIdFU));
            request.setAttribute("FUSelected" , videoIdFU);

        }else {
            request.setAttribute("favoriteUsers" , favoriteService.getAllByVideoId(videoId));
            request.setAttribute("favoriteByVideoId" , shareService.getAllByVideoId(videoId));
        }


        request.setAttribute("favoriteCustom" , favoriteService.getAllFavoriteCustomDTO());
        request.setAttribute("videoTitleList" , objectList);
        request.getRequestDispatcher("/reports.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
