package com.bsuir.petition.service.util.impl;

import com.bsuir.petition.bean.dto.user.UpdateUserDTO;
import com.bsuir.petition.bean.dto.user.UserInformationDTO;
import com.bsuir.petition.bean.dto.user.UserRegistrationDTO;
import com.bsuir.petition.bean.entity.City;
import com.bsuir.petition.bean.entity.Role;
import com.bsuir.petition.bean.entity.User;
import com.bsuir.petition.bean.entity.UserInformation;
import com.bsuir.petition.dao.CityDao;
import com.bsuir.petition.dao.RoleDao;
import com.bsuir.petition.service.util.Exchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class ExchangerImpl implements Exchanger {

    private RoleDao roleDao;

    private CityDao cityDao;

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public User getUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setNick(userRegistrationDTO.getNick());
        return user;
    }

    @Override
    public void getUser(User user, UpdateUserDTO updateUserDTO) {
        user.setBlocked(updateUserDTO.isBlocked());

        ArrayList<String> rolesNames = updateUserDTO.getRoles();
        Set<Role> roles =  new HashSet<Role>(0);
        for (String roleName : rolesNames) {
            Role role;
            role = roleDao.getRoleByName(roleName);
            roles.add(role);
        }
        user.setRoles(roles);
    }

    @Override
    public void setUserInformation(UserInformation userInformation, UserInformationDTO userInformationDTO) {
        userInformation.setName(userInformationDTO.getUsername());
        userInformation.setSurname(userInformationDTO.getSurname());
        City city = cityDao.getCityByName(userInformationDTO.getCity());
        userInformation.setCity(city);
    }
}
