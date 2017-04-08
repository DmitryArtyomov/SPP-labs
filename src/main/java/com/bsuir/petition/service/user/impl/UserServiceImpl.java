package com.bsuir.petition.service.user.impl;

import com.bsuir.petition.bean.dto.user.*;
import com.bsuir.petition.bean.entity.User;
import com.bsuir.petition.bean.entity.UserInformation;
import com.bsuir.petition.dao.UserDao;
import com.bsuir.petition.service.exception.ErrorInputException;
import com.bsuir.petition.service.user.UserService;
import com.bsuir.petition.service.exception.ServerException;
import com.bsuir.petition.service.user.exception.*;
import com.bsuir.petition.service.user.util.Creator;
import com.bsuir.petition.service.user.util.DtoExchanger;
import com.bsuir.petition.service.user.util.Exchanger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Creator creator;

    private UserDao userDao;

    private DtoExchanger dtoExchanger;

    private Exchanger exchanger;

    @Autowired
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    @Autowired
    public void setDtoExchanger(DtoExchanger dtoExchanger) { this.dtoExchanger = dtoExchanger; }

    @Autowired
    public void setExchanger(Exchanger exchanger) {
        this.exchanger = exchanger;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserListDTO getUsers() throws ServerException {
        UserListDTO userListDTO;
        try {
            List<User> users;
            users = userDao.getUsers();
            userListDTO = dtoExchanger.getUserListDTO(users);
        } catch (HibernateException exception) {
            throw new ServerException("Server exception!", exception);
        }
        return userListDTO;
    }

    @Override
    public void updateUser(long id, UpdateUserDTO updateUserDTO) throws UserNotFoundException, ServerException {
        User user;
        try {
            user = userDao.getUserById(id);
        } catch (Exception exception) {
            throw new ServerException("Server exception!", exception);
        }

        if (user == null) {
            throw new UserNotFoundException("No such user!");
        }

        exchanger.getUser(user, updateUserDTO);

        try {
            userDao.updateUser(user);
        } catch (Exception exception) {
            throw new ServerException("Server exception!", exception);
        }
    }

    @Override
    public User getUser(String userEmail) throws UserNotFoundException {
        User user;
        try {
            user = userDao.getUserByEmail(userEmail);
        } catch (Exception exception) {
            throw new UserNotFoundException(exception);
        }
        return user;
    }

    @Override
    public UserDTO getUser(long id) throws UserNotFoundException, ServerException {
        User user;
        try {
            user = userDao.getUserById(id);
            if (user == null) {
                throw new UserNotFoundException("Such user not found!");
            }
        } catch (HibernateException exception) {
            throw new ServerException("Server exception!", exception);
        }

        UserDTO userDTO;
        userDTO = dtoExchanger.getUserDTO(user);
        return userDTO;
    }

    @Override
    public UserInformationDTO getUserInformation(long id)
                        throws UserInformationNotFoundException, ServerException {
        UserInformation userInformation;
        try {
            userInformation = userDao.getUserInformationById(id);
            if (userInformation == null) {
                throw new UserInformationNotFoundException("No such user information!");
            }
        } catch (HibernateException | NullPointerException exception) {
            throw new ServerException("Server exception!", exception);
        }

        return dtoExchanger.getUserInformationDTO(userInformation);
    }

    @Override
    public User registration(UserRegistrationDTO userRegistrationDTO)
            throws DifferentPasswordsException, ErrorInputException,
            SuchUserExistsException, ServerException {

        User user;
        if (userRegistrationDTO.getRepeatPassword().equals(userRegistrationDTO.getPassword())) {

            try {
                user = exchanger.getUser(userRegistrationDTO);
                userDao.addUser(user);
            } catch (Exception exception) {
                throw new SuchUserExistsException("User with such name exists!", exception);
            }
        } else {
            throw new DifferentPasswordsException("Password and repeat password, must be equals!");
        }
        return user;
    }

    @Override
    public void updateUserInformation(long id, UserInformationDTO userInformationDTO)
            throws ErrorInputException, ServerException {

        UserInformation userInformation;
        userInformation = userDao.getUserInformationById(id);

        try {
            if (userInformation == null) {
                User user = userDao.getUserById(id);
                userInformation = creator.getUserInformation(user, userInformationDTO.getCity());
            }
            exchanger.setUserInformation(userInformation, userInformationDTO);
            userDao.updateUserInformation(userInformation);
        } catch (Exception exception) {
            throw new ServerException("Server exception!", exception);
        }
    }

}