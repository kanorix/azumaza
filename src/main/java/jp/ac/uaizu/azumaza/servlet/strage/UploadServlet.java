package jp.ac.uaizu.azumaza.servlet.strage;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import jp.ac.uaizu.azumaza.dto.User;
import jp.ac.uaizu.azumaza.helper.ServletHelper;
import jp.ac.uaizu.azumaza.logic.StrageLogic;
import jp.ac.uaizu.azumaza.model.form.StrageForm;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/strage/upload")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final StrageLogic strageLogic = new StrageLogic();

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletHelper helper = new ServletHelper(request, response);
        if (!helper.existsLoginUser()) {
            helper.logout();
            return;
        }

        final StrageForm form = helper.requestParam(StrageForm.class);
        final User loginUser = helper.getLoginUser();

        final Optional<Part> part = Optional.ofNullable(form.getUploadFile());
        if (part.isPresent()) {
            // `ファイルが存在する場合、アップロードする
            strageLogic.saveFile(loginUser, part.get());
            helper.redirect("/azumaza/strage?status=completed");
            return;
        }

        helper.redirect("/azumaza/strage");
    }

}
