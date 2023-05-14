package jp.ac.uaizu.azumaza.servlet.strage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.uaizu.azumaza.dto.Strage;
import jp.ac.uaizu.azumaza.dto.User;
import jp.ac.uaizu.azumaza.helper.ServletHelper;
import jp.ac.uaizu.azumaza.logic.StrageLogic;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/strage/download")
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final StrageLogic strageLogic = new StrageLogic();

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
         final Optional<String> strageId = helper.requestParamByName("id");

         if (strageId.isEmpty()) {
             helper.error("ダウンロード可能なファイルが存在しません。");
             return;
         }
         final Optional<Strage> strage = strageLogic.getStrage(loginUser.getId(), Integer.parseInt(strageId.get()));
         if (strage.isEmpty()) {
             helper.error("ダウンロード可能なファイルが存在しません。");
             return;
         }
         helper.download(strage.get().getFileName(), new File(strage.get().getFilePath()));

    }

}
