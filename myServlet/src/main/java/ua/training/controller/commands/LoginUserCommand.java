package ua.training.controller.commands;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUserCommand implements Command {
    private DaoFactory factory = DaoFactory.getInstance();
    private UserDao userDao = factory.createUserDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (userName == null || password == null){
            return "WEB-INF/view/login.jsp";
        }


        //------------------------------------------------------




        try {
            User user = userDao.checkLogin(userName, password);
            if (user != null) {

                if (user.getRole().equals("ROLE_USER")) {

                    //session.setAttribute("login",true);
                    //response.sendRedirect("/user");
                    session.setAttribute("role",user.getRole());
                    session.setAttribute("userName",user.getUserName());
                    session.setAttribute("login",true);
                    session.setAttribute("redirect","/user");
                    System.out.println("found");
                    //return "WEB-INF/view/user.jsp";
                }else {

                    //session.setAttribute("login",true);
                    //response.sendRedirect("/admin");
                    session.setAttribute("role",user.getRole());
                    session.setAttribute("userName",user.getUserName());
                    session.setAttribute("login",true);
                    session.setAttribute("redirect","/admin");
                    //return "WEB-INF/view/admin.jsp";
                }
                return null;

            } else {
                System.out.println("not Found");
                return "WEB-INF/view/login.jsp";
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        //------------------------------------

    }
}
