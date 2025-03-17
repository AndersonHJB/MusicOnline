package com.example.musiconline.controller;

import com.example.musiconline.domain.R;
import com.example.musiconline.log.annotation.Log;
import com.example.musiconline.log.enums.BusinessType;
import com.example.musiconline.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 通用请求处理
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    @Value("${file.upload.dic}")
    private String fileUploadDic;

    /**
     * 文件上传
     */
    @Log(title = "文件上传", businessType = BusinessType.OTHER)
    @PostMapping({"/upload/file"})
    public R<?> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        String newFileName = sdf.format(new Date()) + r.nextInt(100) + suffixName;
        File fileDirectory = new File(fileUploadDic);
        //创建文件
        File destFile = new File(fileUploadDic + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            String filePath = ServletUtils.getHost(new URI(String.valueOf(request.getRequestURL()))) + "/upload/" + newFileName;
            return R.ok("上传成功",filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("文件上传失败");
        }
    }
}
