package com.apps.common.utils;

import java.io.File;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.teamsun.core.utils.FileUtils;

public final class CommonUploadTools {

    /**
     * 
     * @param request :
     * @param inputName :页面input的name,例:imgFile
     * @param fileName :文件名称,例:System.currentTimeMillis()
     * @param strAllowExtension :允许的后缀名,例:".jpg,.png,.gif"
     * @param folderName :文件夹名称,例:"user"
     * @return
     */
    public static String uploadMultipartFile(HttpServletRequest request, String inputName, String fileName,
            String strAllowExtension, String folderName) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile imgMultipartFile = multipartRequest.getFile(inputName);
            if (null != imgMultipartFile && !imgMultipartFile.isEmpty()) {
                String strExtension = imgMultipartFile.getOriginalFilename().substring(
                        imgMultipartFile.getOriginalFilename().lastIndexOf("."));
                if (!strAllowExtension.toLowerCase().contains(strExtension.toLowerCase())) {
                    throw new Exception("文件格式不正确(该模块仅支持" + strAllowExtension + "的文件)");
                }
                if (imgMultipartFile.getSize() <= 5242880) {
                    String filePath = FileUtils.getApplicationProperties("upload_path");
                    String fileFolder = FileUtils.getApplicationProperties("upload_home_name") + "/" + folderName
                            + "/";
                    createFolder(filePath + fileFolder);
                    File imgFile = new File(filePath + fileFolder + fileName + strExtension);
                    imgMultipartFile.transferTo(imgFile);
                    return fileFolder + fileName + strExtension;
                } else {
                    throw new Exception("上传文件大小不能超过5M");
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param request :
     * @param inputName :页面input的name,例:imgFile
     * @param fileName :文件名称,例:System.currentTimeMillis()
     * @param strAllowExtension :允许的后缀名,例:".jpg,.png,.gif"
     * @param folderName :文件夹名称,例:"user"
     * @return
     */
    public static FileUploadInfo uploadMultipartFileReturnInfo(HttpServletRequest request, String inputName,
            String fileName, String strAllowExtension, String folderName) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile imgMultipartFile = multipartRequest.getFile(inputName);
            if (null != imgMultipartFile && !imgMultipartFile.isEmpty()) {
                String strExtension = imgMultipartFile.getOriginalFilename().substring(
                        imgMultipartFile.getOriginalFilename().lastIndexOf("."));
                if (!strAllowExtension.toLowerCase().contains(strExtension.toLowerCase())) {
                    throw new Exception("文件格式不正确(该模块仅支持" + strAllowExtension + "的文件)");
                }
                if (imgMultipartFile.getSize() <= 5242880) {
                    String filePath = FileUtils.getApplicationProperties("upload_path");
                    String fileFolder = FileUtils.getApplicationProperties("upload_home_name") + "/" + folderName
                            + "/";
                    createFolder(filePath + fileFolder);
                    File imgFile = new File(filePath + fileFolder + fileName + strExtension);
                    imgMultipartFile.transferTo(imgFile);
                    FileUploadInfo fileUploadInfo = new FileUploadInfo();
                    fileUploadInfo.setFileName(imgMultipartFile.getOriginalFilename());
                    fileUploadInfo.setFilePath(fileFolder + fileName + strExtension);
                    fileUploadInfo.setFileSize(imgMultipartFile.getSize());
                    return fileUploadInfo;
                } else {
                    throw new Exception("上传文件大小不能超过5M");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    public static String getFormatSize(long size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + "Byte(s)";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    } 
    
    /**
     * 如果文件夹不存在，则创建
     * 
     * @param path
     */
    public static void createFolder(String path) {
        try {
            File uploadFilePath = new File(path);
            if (uploadFilePath.exists() == false) {
                uploadFilePath.mkdirs();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}