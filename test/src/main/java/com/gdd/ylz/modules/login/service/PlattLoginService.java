package com.gdd.ylz.modules.login.service;

import com.gdd.ylz.modules.login.dto.LoginPassWordDTO;
import com.gdd.ylz.modules.login.dto.LoginRespDTO;
import com.gdd.ylz.modules.login.dto.PasswordBackDTO;
import com.gdd.ylz.modules.login.dto.RegisterDTO;
import com.gdd.ylz.result.DataResult;

public interface PlattLoginService {
    DataResult checkRegisterData(RegisterDTO registerDTO);

    DataResult doRegister(RegisterDTO registerDTO);

    LoginRespDTO login(LoginPassWordDTO loginPassWordDTO);

    DataResult checkNewpasswordData(PasswordBackDTO passwordBackDTO);

    DataResult doNewPassword(PasswordBackDTO passwordBackDTO);
}
