package servlet;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import util.Utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet({"/admin/showUsers" , "/admin/showUsers/" , "/admin/userEdit/" ,"/admin/userUpdate/", "/admin/userDelete/"})
public class UsersServlet extends HttpServlet {
    private UserService service = new UserService();
    private int currentPage;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if(uri.contains("userEdit")){
            String userId = request.getParameter("userId");
            request.setAttribute("userEdit", service.getById(userId));
            request.setAttribute("userList" , service.getAll());

            request.setAttribute("isIdEnable" , "readonly");
            request.setAttribute("isDeleteable" , "");
            request.setAttribute("isUpdateable" , "");
        }

        if(uri.contains("showUsers")){
            service.enableCreate(request,response);
        }

        currentPage = Utils.checkPage(request,response);

        request.setAttribute("totalPage" , service.getTotalPage(10));
        request.setAttribute("userList" , service.getAllByPage(currentPage));
        request.getRequestDispatcher("/users.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.contains("userUpdate")){
           service.userUpdate(request,response);
        }

        if(uri.contains("userDelete")){
            UserDTO userDTO = new UserDTO();
            try {
                BeanUtils.populate(userDTO,request.getParameterMap());
                service.delete(userDTO);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            service.enableCreate(request,response);
        }

        doGet(request,response);

    }
}
