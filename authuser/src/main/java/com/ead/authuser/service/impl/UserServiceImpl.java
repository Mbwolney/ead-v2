package com.ead.authuser.service.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepositroy;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepositroy userRepositroy;

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Override
    public List<UserModel> findAll() {
        return userRepositroy.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepositroy.findById(userId);
    }

    @Transactional // Ele volta a deleção se não for correta
    @Override
    public void delete(UserModel userModel) {
        userRepositroy.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepositroy.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepositroy.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositroy.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepositroy.findAll(spec, pageable);
    }

    @Transactional // Ele volta o salvamento se não for correta
    @Override
    public UserModel saveUser(UserModel userModel){
        userModel = save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
        return userModel;
    }
}
