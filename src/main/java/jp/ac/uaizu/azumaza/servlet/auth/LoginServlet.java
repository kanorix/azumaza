package jp.ac.uaizu.azumaza.servlet.auth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.uaizu.azumaza.dto.User;
import jp.ac.uaizu.azumaza.helper.ServletHelper;
import jp.ac.uaizu.azumaza.logic.AuthLogic;
import jp.ac.uaizu.azumaza.model.form.LoginForm;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthLogic authLogic = new AuthLogic();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletHelper helper = new ServletHelper(request, response);
        request.setAttribute("loginForm", new LoginForm());
        helper.forward("/auth/login.jsp");
    }

    /**
     * @throws IOException
     * @throws ServletException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletHelper helper = new ServletHelper(request, response);
        final LoginForm form = helper.requestParam(LoginForm.class);

        Optional<User> authUser = authLogic.tryLogin(form.getName(), form.getPassword());

        if (authUser.isEmpty()) {
            request.setAttribute("loginForm", form);
            helper.forward("/auth/login.jsp");
        } else {
            helper.setLoginUser(authUser.get());
            helper.redirect("/azumaza/strage");
        }
    }

}