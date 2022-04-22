package com.gdd.ylz.modules.sys.service.impl;

import com.gdd.ylz.common.util.DateUtils;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.modules.mail.dto.LayeditPicDTO;
import com.gdd.ylz.modules.sys.entity.Filestore;
import com.gdd.ylz.modules.sys.dao.FilestoreMapper;
import com.gdd.ylz.modules.sys.service.IFilestoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.result.DataResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileStore;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-10-12
 */
@Service
public class FilestoreServiceImpl extends ServiceImpl<FilestoreMapper, Filestore> implements IFilestoreService {
    @Autowired
    private FilestoreMapper filestoreMapper;
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    @Transactional
    public DataResult doSingleUpload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = "." + FilenameUtils.getExtension(originalFilename);
        String newname = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + StringUtils.getGUID() + suffix;
        int size= (int) multipartFile.getSize();
        String relativePath=uploadPath+"/"+DateUtils.formatDate(new Date(), "yyyyMMdd");
        String dirpath=System.getProperty("user.dir")+"/"+relativePath;
        File file=new File(dirpath);
        if(!file.exists()){
            file.mkdirs();
        }
        String type= multipartFile.getContentType();
        // 允许上传的文件后缀列表
        Set<String> imgSuffix = new HashSet<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif",".webp"));
        int isImg=0;
        if (imgSuffix.contains(suffix.toLowerCase())) {
            isImg=1;
        }
        Filestore filestore=new Filestore();
        String id=StringUtils.getGUID();
        filestore.setId(id);
        filestore.setFileId(id);
        filestore.setFilePath(dirpath+"/"+newname);
        filestore.setRelativePath(relativePath+"/"+newname);
        filestore.setFileSize(size);
        filestore.setFileSuffix(suffix);
        filestore.setNewName(newname);
        filestore.setOriginalName(originalFilename);
        filestore.setIsImg(isImg);
        filestore.setDownCounts(BigDecimal.ZERO);
        filestore.setFileType(type);
        //开始上传
        try {
            multipartFile.transferTo(new File(dirpath+"/"+newname));
            filestoreMapper.insert(filestore);
            return DataResult.success(filestore);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    @Transactional
    public DataResult doSingleUploadMany(MultipartFile[] multipartFiles) {
        List<Filestore> filestoreList=new ArrayList<>();
        for(MultipartFile multipartFile:multipartFiles){
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = "." + FilenameUtils.getExtension(originalFilename);
            String newname = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + StringUtils.getGUID() + suffix;
            int size= (int) multipartFile.getSize();
            String relativePath=uploadPath+"/"+DateUtils.formatDate(new Date(), "yyyyMMdd");
            String dirpath=System.getProperty("user.dir")+"/"+relativePath;
            File file=new File(dirpath);
            if(!file.exists()){
                file.mkdirs();
            }
            String type= multipartFile.getContentType();
            // 允许上传的文件后缀列表
            Set<String> imgSuffix = new HashSet<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif",".webp"));
            int isImg=0;
            if (imgSuffix.contains(suffix.toLowerCase())) {
                isImg=1;
            }
            Filestore filestore=new Filestore();
            filestore.setId(StringUtils.getGUID());
            filestore.setFileId(StringUtils.getGUID());
            filestore.setFilePath(dirpath+"/"+newname);
            filestore.setRelativePath(relativePath+"/"+newname);
            filestore.setFileSize(size);
            filestore.setFileSuffix(suffix);
            filestore.setNewName(newname);
            filestore.setOriginalName(originalFilename);
            filestore.setIsImg(isImg);
            filestore.setDownCounts(BigDecimal.ZERO);
            filestore.setFileType(type);
            //开始上传
            try {
                multipartFile.transferTo(new File(dirpath+"/"+newname));
                filestoreMapper.insert(filestore);
                filestoreList.add(filestore);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return DataResult.success(filestoreList);
    }

    @Override
    public DataResult doAvatarUpload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = "." + FilenameUtils.getExtension(originalFilename);
        String newname = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + StringUtils.getGUID() + suffix;
        int size= (int) multipartFile.getSize();
        String relativePath=uploadPath+"/"+DateUtils.formatDate(new Date(), "yyyyMMdd");
        String dirpath=System.getProperty("user.dir")+"/"+relativePath;
        File file=new File(dirpath);
        if(!file.exists()){
            file.mkdirs();
        }
        String type= multipartFile.getContentType();
        // 允许上传的文件后缀列表
        Set<String> imgSuffix = new HashSet<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif",".webp"));
        int isImg=0;
        if (imgSuffix.contains(suffix.toLowerCase())) {
            isImg=1;
        }
        Filestore filestore=new Filestore();
        String id=StringUtils.getGUID();
        filestore.setId(id);
        filestore.setFileId(id);
        filestore.setFilePath(dirpath+"/"+newname);
        filestore.setRelativePath(relativePath+"/"+newname);
        filestore.setFileSize(size);
        filestore.setFileSuffix(suffix);
        filestore.setNewName(newname);
        filestore.setOriginalName(originalFilename);
        filestore.setIsImg(isImg);
        filestore.setIsAvatar(1);
        filestore.setDownCounts(BigDecimal.ZERO);
        filestore.setFileType(type);
        //开始上传
        try {
            multipartFile.transferTo(new File(dirpath+"/"+newname));
            filestoreMapper.insert(filestore);
            return DataResult.success(filestore);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public DataResult doLayeditUpload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = "." + FilenameUtils.getExtension(originalFilename);
        String newname = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + StringUtils.getGUID() + suffix;
        int size= (int) multipartFile.getSize();
        String relativePath=uploadPath+"/"+DateUtils.formatDate(new Date(), "yyyyMMdd");
        String dirpath=System.getProperty("user.dir")+"/"+relativePath;
        File file=new File(dirpath);
        if(!file.exists()){
            file.mkdirs();
        }
        String type= multipartFile.getContentType();
        // 允许上传的文件后缀列表
        Set<String> imgSuffix = new HashSet<>(Arrays.asList(".jpg", ".jpeg", ".png", ".gif",".webp"));
        int isImg=0;
        if (imgSuffix.contains(suffix.toLowerCase())) {
            isImg=1;
        }
        Filestore filestore=new Filestore();
        String id=StringUtils.getGUID();
        filestore.setId(id);
        filestore.setFileId(id);
        filestore.setFilePath(dirpath+"/"+newname);
        filestore.setRelativePath(relativePath+"/"+newname);
        filestore.setFileSize(size);
        filestore.setFileSuffix(suffix);
        filestore.setNewName(newname);
        filestore.setOriginalName(originalFilename);
        filestore.setIsImg(isImg);
        filestore.setDownCounts(BigDecimal.ZERO);
        filestore.setFileType(type);
        //开始上传
        try {
            multipartFile.transferTo(new File(dirpath+"/"+newname));
            filestoreMapper.insert(filestore);
            LayeditPicDTO layeditPicDTO=new LayeditPicDTO();
            layeditPicDTO.setSrc(filestore.getRelativePath());
            layeditPicDTO.setTitle(filestore.getOriginalName());
            return DataResult.success(layeditPicDTO);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
