package jp.ac.uaizu.azumaza.servlet.strage;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.uaizu.azumaza.dto.User;
import jp.ac.uaizu.azumaza.helper.ServletHelper;
import jp.ac.uaizu.azumaza.logic.StrageLogic;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/strage/delete")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final StrageLogic strageLogic = new StrageLogic();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletHelper helper = new ServletHelper(request, response);
        if (!helper.existsLoginUser()) {
            helper.logout();
            return;
        }

        final User loginUser = helper.getLoginUser();
        final Optional<String> id = helper.requestParamByName("fileId");
        if (id.isEmpty()) {
            helper.error("");
            return;
        }
        strageLogic.deleteStrage(loginUser.getId(), Integer.parseInt(id.get()));
        helper.redirect("/azumaza/strage?status=deleted");
    }

}
