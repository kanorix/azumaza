package jp.ac.uaizu.azumaza.servlet.strage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.uaizu.azumaza.dto.User;
import jp.ac.uaizu.azumaza.helper.ServletHelper;
import jp.ac.uaizu.azumaza.logic.StrageLogic;
import jp.ac.uaizu.azumaza.model.form.StrageForm;

/**
 * Servlet implementation class StrageServlet
 */
@WebServlet("/strage")
public class StrageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final StrageLogic strageLogic = new StrageLogic();

    public enum Status {
        FAILED,
        NOT_SELECTED,
        DELETED,
        COMPLETED;
    }

    private Optional<String> resolveStatus(Optional<String> status) {
        List<String> statusNames = Arrays.asList(Status.values()).stream()
                .map(s -> s.name()).collect(Collectors.toList());
        return status
                .map(String::toUpperCase)
                .filter(s -> statusNames.contains(s));
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletHelper helper = new ServletHelper(request, response);

        if (!helper.existsLoginUser()) {
            helper.logout();
            return;
        }

        final User loginUser = helper.getLoginUser();

        final StrageForm form = new StrageForm();
        form.setFiles(strageLogic.createFileForms(loginUser.getId()));

        request.setAttribute("status", resolveStatus(helper.requestParamByName("status")).orElse(""));
        request.setAttribute("strageForm", form);
        helper.forward("/strage/index.jsp");
    }
}
