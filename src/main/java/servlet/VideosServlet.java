package servlet;

import dto.VideoDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.beanutils.BeanUtils;
import service.VideoService;
import util.Utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet({"/admin/showVideos", "/admin/showVideos/" , "/admin/createVideo/" , "/admin/updateVideo/" , "/admin/deleteVideo/" , "/admin/resetVideo/", "/admin/editVideo/"})
@MultipartConfig
public class VideosServlet extends HttpServlet {
    private VideoService service = new VideoService();
    private int currentPage;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if(uri.contains("editVideo")){
            String id = request.getParameter("idEdit");
            VideoDTO videoDTO = service.getById(id);
            request.setAttribute("idEdit" , videoDTO );
            service.disableCreate(request,response);
        }

        if(uri.contains("resetVideo") || uri.contains("showVideos")){
            service.enableCreate(request,response);
        }

        currentPage = Utils.checkPage(request,response);

        request.setAttribute("totalPage" , service.getTotalPage(10));
        request.setAttribute("videoList" , service.getAllByPage(currentPage));



        request.getRequestDispatcher("/videos.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.contains("createVideo")){
            service.saveVideo(request,response);
        }
        else if(uri.contains("updateVideo")){
            service.updateVideo(request,response);
        }
        else if(uri.contains("deleteVideo")){
            service.deleteVideo(request,response);
        }

        doGet(request,response);
    }


}
