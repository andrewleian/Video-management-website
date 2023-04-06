package servlet;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.UserService;

import java.io.IOException;

@WebServlet({"/showSignIn" , "/signIn" , "/user/signOut" })
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.contains("signOut")){
            request.getSession().removeAttribute("userSession");

        }
        Cookie cookie[] = request.getCookies();
        String username ="";
        String password ="";
        if(cookie != null){
            for (Cookie c : cookie){
                if(c.getName().equals("JSESSIONID")){
                    continue;
                }
                username = c.getName();
                password = c.getValue();
            }
        }

        request.setAttribute("username", username);
        request.setAttribute("password", password);

        request.getRequestDispatcher("/signin.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        String uri = request.getRequestURI();
        if(uri.contains("signIn")){
           boolean check = userService.signIn(request,response);
           if(!check){
               doGet(request,response);
           }
           UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userSession");
           if(userDTO.isAdmin()){
               response.sendRedirect("/admin/showReports");
           }else{
               response.sendRedirect("/showTrangChu");
           }
        }
    }
}
