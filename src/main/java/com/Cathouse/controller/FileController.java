package com.Cathouse.controller;

import com.Cathouse.utils.PathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final String UPLOAD_DIR = "/file";
    private static final String FILE_PARAM = "file";
    private static final String FILE_NAME_PARAM = "fileName";

    @Value("${my-server.api-context-path}")
    private String apiContextPath;

    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam(FILE_PARAM) MultipartFile multipartFile) {
        return handleFileUpload(multipartFile);
    }

    @GetMapping("/getFile")
    public void getFile(@RequestParam(FILE_NAME_PARAM) String fileName,
                        HttpServletResponse response) throws IOException {
        File file = getUploadedFile(fileName);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private Map<String, Object> handleFileUpload(MultipartFile multipartFile) {
        Map<String, Object> response = new HashMap<>();
        if (multipartFile.isEmpty()) {
            response.put("code", 400);
            response.put("msg", "文件不能为空");
            return response;
        }

        long currentTimeMillis = System.currentTimeMillis();
        String fileName = currentTimeMillis + multipartFile.getOriginalFilename();
        try {
            if (saveUploadedFile(multipartFile, fileName)) {
                response.put("code", 200);
                response.put("data", apiContextPath + "/file/getFile?" + FILE_NAME_PARAM + "=" + fileName);
                return response;
            }
        } catch (IOException e) {
            response.put("code", 500);
            response.put("msg", "文件上传失败");
            return response;
        }

        response.put("code", 500);
        response.put("msg", "文件上传失败");
        return response;
    }

    private boolean saveUploadedFile(MultipartFile multipartFile, String fileName) throws IOException {
        File uploadDir = new File(PathUtils.getClassLoadRootPath() + UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return false;
        }
        File destFile = new File(uploadDir, fileName);
        if (destFile.exists() && !destFile.delete()) {
            return false;
        }
        multipartFile.transferTo(destFile);
        return true;
    }

    private File getUploadedFile(String fileName) {
        File uploadDir = new File(PathUtils.getClassLoadRootPath() + UPLOAD_DIR);
        return new File(uploadDir, fileName);
    }
}