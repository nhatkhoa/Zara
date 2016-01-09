// Copyright (c) 2015 KMS Technology, Inc.
package vn.zara.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.zara.domain.common.exception.UserNotExisted;
import vn.zara.domain.learn.LessonResult;
import vn.zara.domain.learn.LessonResultRepository;
import vn.zara.domain.lesson.LessonRepository;
import vn.zara.domain.util.SecurityUtil;
import vn.zara.web.dto.UserDetail;

@Service
@Transactional(readOnly = true)
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final LessonResultRepository lessonResultRepository;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LessonResultRepository lessonResultRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.lessonResultRepository = lessonResultRepository;

    }

    @Transactional
    public void registerUser(String username, String password, boolean isBoy) {
        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.USER);
        user.setBoy(isBoy);
        userRepository.save(user);
    }

    public UserDetail getUserInfo() {
        String username = SecurityUtil.getCurrentLogin();
        User user = userRepository.findOneByUsername(username).orElse(null);
        if(user == null)
            throw new UserNotExisted("UserNotExisted");
        long sumOfScore = lessonResultRepository.findByUsername(username)
                .mapToLong(lessonResultRepository -> lessonResultRepository.getScore())
                .sum();
        long numOfPokemon = lessonResultRepository.findByUsername(username).count();
        UserDetail userDetail = new UserDetail(username, user.isBoy(), numOfPokemon, sumOfScore);
        return userDetail;
    }
}
