package com.ead.authuser.service.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
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
    UserCourseRepository userCourseRepository;

    @Autowired
    CourseClient courseClient;

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
        boolean deleteUserCourseInCourse = false;
        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllUserCourseIntoUser(userModel.getUserId());
        if(!userCourseModelList.isEmpty()){
            userCourseRepository.deleteAll(userCourseModelList);
            deleteUserCourseInCourse = true;
        }
        userRepositroy.delete(userModel);
        if(deleteUserCourseInCourse){
            courseClient.deleteUserInCourse(userModel.getUserId());
        }
    }

    @Override
    public void save(UserModel userModel) {
        userRepositroy.save(userModel);
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
}
