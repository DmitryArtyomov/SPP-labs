package com.bsuir.petition.service.util;

import com.bsuir.petition.bean.dto.user.UpdateUserDTO;
import com.bsuir.petition.bean.dto.user.UserInformationDTO;
import com.bsuir.petition.bean.dto.user.UserRegistrationDTO;
import com.bsuir.petition.bean.entity.User;
import com.bsuir.petition.bean.entity.UserInformation;

public interface Exchanger {
    User getUser(UserRegistrationDTO userRegistrationDTO);
    void getUser(User user, UpdateUserDTO updateUserDTO);
    void setUserInformation(UserInformation userInformation, UserInformationDTO userInformationDTO);
}
