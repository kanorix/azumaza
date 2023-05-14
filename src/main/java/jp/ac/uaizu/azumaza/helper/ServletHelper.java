package jp.ac.uaizu.azumaza.helper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class ServletHelper {

    private static final String LOGIN_URL = "/azumaza/auth/login";

    private static final String JSP_PATH = "/WEB-INF/jsp";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String KEY_LOGIN_USER = "LOGIN-USER";

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpSession session;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        this.session = request.getSession();

        // setting
        try {
            this.request.setCharacterEncoding(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void error(String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        forward("/error.jsp");
    }

    public void forward(String path) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_PATH + path).forward(request, response);
    }

    public void redirect(String path) throws IOException {
        response.sendRedirect(path);
    }

    public void redirect() throws IOException {
        try {
            redirect(new URI(request.getHeader("referer")).getPath());
        } catch (URISyntaxException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void download(String fileName, File file) throws IOException {
        response.setContentType("application/actet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.getOutputStream().write(Files.readAllBytes(file.toPath()));
    }

    public <T> T requestParam(Class<T> clazz) {
        try {
            T form = clazz.getConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                final Optional<String> param = requestParamByName(field.getName());

                if (param.isPresent()) {
                    field.setAccessible(true);
                    field.set(form, param.get());
                }
                if (field.getType() == Part.class) {
                        final Optional<Part> part = requestPartByName(field.getName());
                        if (part.isPresent() && part.get().getSize() != 0) {
                            field.setAccessible(true);
                            field.set(form, part.get());
                        }
                }
            }

            return form;
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return null;
    }

    public Optional<String> requestParamByName(String name) {
        return Optional.ofNullable(request.getParameter(name));
    }

    public Optional<Part> requestPartByName(String name) throws IOException, ServletException {
        return Optional.ofNullable(request.getPart(name));
    }

    public <T> void setLoginUser(Object user) {
        session.setAttribute(KEY_LOGIN_USER, user);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getLoginUserOpt() {
        return Optional.ofNullable((T) session.getAttribute(KEY_LOGIN_USER));
    }

    public boolean existsLoginUser() {
        return getLoginUserOpt().isPresent();
    }

    public <T> T getLoginUser() throws IOException {
        Optional<T> loginUser = getLoginUserOpt();
        return loginUser.get();
    }

    public void logout() throws IOException {
        session.invalidate();
        redirect(LOGIN_URL);
    }
}
