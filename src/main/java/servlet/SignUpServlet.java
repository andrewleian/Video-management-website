package servlet;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet({"/showSignUp","/signUp" })
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        String uri = request.getRequestURI();
        if(uri.contains("signUp")){
            userService.signUp(request , response);
            doGet(request,response);
        }
    }
}
