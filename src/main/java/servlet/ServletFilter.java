package servlet;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*" , "/admin/*"} , dispatcherTypes = DispatcherType.REQUEST)
public class ServletFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();
        UserDTO userSession = (UserDTO) ((HttpServletRequest) request).getSession().getAttribute("userSession");

        if(userSession == null){
            httpServletResponse.sendRedirect("/showSignIn");
            return;
        }

        if(uri.contains("/user/")){
            chain.doFilter(request, response);
            return;
        }

        if(uri.contains("/admin/")){
            boolean checkAdmin = userSession.isAdmin();
            if(checkAdmin){
                chain.doFilter(request, response);
                return;
            }
            httpServletResponse.sendRedirect("/showSignIn");
            return;
        }


    }
}
