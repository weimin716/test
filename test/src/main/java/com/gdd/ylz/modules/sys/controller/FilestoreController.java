package com.gdd.ylz.modules.sys.controller;


import com.gdd.ylz.modules.sys.service.IFilestoreService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-10-12
 */
@RestController
@RequestMapping("/sys/filestore")
@Api(tags={"sys"})
public class FilestoreController {


    @Autowired
    private IFilestoreService filestoreService;

    @PostMapping(value = "/singleupload")
    @ApiOperation(value = "单文件上传", notes = "单文件上传")
    public DataResult doSingleUpload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return filestoreService.doSingleUpload(multipartFile);
    }

    @PostMapping(value = "/layeditupload")
    @ApiOperation(value = "富文本编辑图片接口", notes = "富文本编辑图片接口")
    public DataResult doLayeditUpload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return filestoreService.doLayeditUpload(multipartFile);
    }

    @PostMapping(value = "/avatarupload")
    @ApiOperation(value = "头像上传", notes = "头像上传")
    public DataResult doAvatarUpload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return filestoreService.doAvatarUpload(multipartFile);
    }



    @PostMapping(value = "/singleuploadmany")
    @ApiOperation(value = "多文件上传", notes = "多文件上传")
    public DataResult doSingleUploadMany(@RequestParam("multipartFiles") MultipartFile[] multipartFiles) {
        return filestoreService.doSingleUploadMany(multipartFiles);
    }

    @GetMapping("/getfile")
    @ApiOperation(value = "根据id查询file", notes = "根据id查询file")
    public DataResult getFile(String  id) {
        return DataResult.success(filestoreService.getById(id));
    }

    @GetMapping("/downloadLocal")
    @ApiOperation(value = "下载", notes = "下载")
    public void downloadLocal(String path, HttpServletResponse response) throws IOException {
        // 读到流中
        InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        String filename = new File(path).getName();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }
}

