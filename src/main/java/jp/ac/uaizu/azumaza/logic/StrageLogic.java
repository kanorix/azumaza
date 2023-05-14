package jp.ac.uaizu.azumaza.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import jp.ac.uaizu.azumaza.dao.StrageDAO;
import jp.ac.uaizu.azumaza.dto.Strage;
import jp.ac.uaizu.azumaza.dto.User;
import jp.ac.uaizu.azumaza.model.form.FileForm;

public class StrageLogic {

    public static final String DIR_PATH = "upload";

    private final StrageDAO strageDao = new StrageDAO();

    private final Path uploadDirectory = Paths.get(DIR_PATH);

    public List<FileForm> createFileForms(int userId) {
        try {
            return strageDao.findByUserId(userId).stream()
                    .map(s -> convertStrageToForm(s))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Optional<Strage> getStrage(int userId, int strageId) {
        try {
            return strageDao.findById(strageId)
                    .filter(s -> s.getUserId() == userId);
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteStrage(int userId, int strageId) {
        try {
            final Optional<Strage> strage = strageDao.deleteStrage(userId, strageId);
            if (strage.isPresent()) {
                final Path path = Paths.get(strage.get().getFilePath());
                Files.delete(path);
            }
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    private FileForm convertStrageToForm(Strage strage) {
        final FileForm form = new FileForm();
        form.setId(strage.getId());
        form.setName(strage.getFileName());
        form.setSize(getSizeWithUnit(strage.getFileSize()));
        return form;
    }

    private String getSizeWithUnit(int size) {
        final int kb = size / 1024;
        final int mb = kb / 1024;
        final int gb = mb / 1024;
        if (gb == 0 && mb == 0 && kb == 0) {
            return size + "Byte";
        }
        if (gb == 0 && mb == 0) {
            return kb + "KB";
        }
        if (gb == 0) {
            return mb + "MB";
        }
        return gb + "GB";
    }

    private String generageFileName(String originFileName) {
        final String fileName = RandomStringUtils.randomAlphanumeric(12);
        final String extension = FilenameUtils.getExtension(originFileName);
        if (extension.equals("")) {
            return fileName;
        }
        return fileName + "." + extension;
    }

    public String saveFile(User loginUser, Part part) {
        final String filename = generageFileName(part.getSubmittedFileName());
        final String filePath = writeFile(loginUser.getId(), part, filename);
        final Strage strage = new Strage();

        strage.setUserId(loginUser.getId());
        strage.setFileName(part.getSubmittedFileName());
        strage.setFilePath(filePath);
        strage.setFileSize((int) part.getSize());
        try {
            strageDao.insert(strage);
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return filePath;
    }

    private String writeFile(Integer userId, Part part, String fileName) {
        try {
            final Path directory = uploadDirectory.resolve(userId.toString());
            Files.createDirectories(directory);

            final File file = new File(directory.toFile(), fileName);
            final BufferedInputStream br = new BufferedInputStream(part.getInputStream());
            final BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));

            int len = 0;
            byte[] buff = new byte[1024];
            while ((len = br.read(buff)) != -1) {
                bw.write(buff, 0, len);
            }
            bw.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return "";
    }
}
