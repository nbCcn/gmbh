package com.guming.common.utils;

import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/21
 */
@Slf4j
@PropertySource("classpath:book.properties")
public class FileUtil {

    /**
     * 上传文件的最大尺寸
     */
    @Value("${file.upload.max-size}")
    public static Long FILE_UPLOAD_MAX_SIZE;

    public static final Integer BUFFER_SIZE =1024;

    /**
     * 文件上传
     * @param multipartFile 接受到的文件对象
     * @param filePath       上传的文件路径
     */
    public static void uploadFile(MultipartFile multipartFile, String filePath) {
        try {
            uploadFile(multipartFile.getInputStream(),multipartFile.getOriginalFilename(),multipartFile.getSize(),filePath);
        } catch (IOException e) {
            log.error("",e);
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 文件上传，该处锁只针对多例
     * @param is                     源文件输入流
     * @param sourceFileName        源文件文件名
     * @param fileSize              源文件大小
     * @param filePath              上传后的文件路径
     * @throws IOException
     */
    public static synchronized void uploadFile(InputStream is,String sourceFileName,Long fileSize,String filePath) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        //当文件存在时清除旧文件
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (fileSize > FILE_UPLOAD_MAX_SIZE) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_FILE_UPLOAD_SIZE_OVER, FILE_UPLOAD_MAX_SIZE.toString());
            }
            int n;
            while ((n = is.read(buffer, 0, buffer.length)) != -1) {
                fos.write(buffer, 0, n);
            }
            log.info("upload file {} success...", sourceFileName);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (is != null){
                is.close();
            }
        }
    }

    /**
     * 下载
     * @param fileName           下载的文件名
     * @param bs                  下载的文件流
     * @param sourceEncoding      原文件名的编码格式
     * @param targetEncoding       下载后的文件名编码格式
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> download(String fileName, byte[] bs, String sourceEncoding, String targetEncoding){
        try {
            String targetFileName = new String(fileName.getBytes(sourceEncoding), targetEncoding);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", targetFileName);
            return new ResponseEntity<byte[]>(bs, headers, HttpStatus.CREATED);
        } catch (UnsupportedEncodingException e) {
            log.error("",e);
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_FILE_DOWNLOAD_FAILED);
        }
    }

    /**
     * 创建文件夹
     * @param fileName  文件夹名
     */
    public static void mkdirs(String fileName) {
        File file = new File(fileName);
        mkdirs(file);
    }

    /**
     * 创建文件夹
     * @param file  文件夹
     */
    public static void mkdirs(File file) {
        if (!file.exists()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
            }
        }
    }
}
