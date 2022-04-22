package com.gdd.ylz.modules.sys.service;

import com.gdd.ylz.modules.sys.entity.Filestore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzg
 * @since 2021-10-12
 */
public interface IFilestoreService extends IService<Filestore> {

    DataResult doSingleUpload(MultipartFile multipartFile);

    DataResult doSingleUploadMany(MultipartFile[] multipartFiles);

    DataResult doAvatarUpload(MultipartFile multipartFile);

    DataResult doLayeditUpload(MultipartFile multipartFile);
}
