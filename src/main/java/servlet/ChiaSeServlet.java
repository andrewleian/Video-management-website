package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.ShareService;

import java.io.IOException;

@WebServlet({"/user/showChiaSe/" , "/user/share"})
public class ChiaSeServlet extends HttpServlet {
    ShareService service = new ShareService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("videoId" , request.getParameter("videoId"));
        request.getRequestDispatcher("/chiase.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service.share(request,response);
        doGet(request,response);
    }
}
