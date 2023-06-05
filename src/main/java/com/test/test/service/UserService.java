package com.test.test.service;

import com.test.test.dto.LoginDTO;
import com.test.test.dto.UserDTO;
import com.test.test.model.User;
import com.test.test.model.UserSession;
import com.test.test.repository.UserRepository;
import com.test.test.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;


    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(generateUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(hashPassword(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(new Date());

        userRepository.save(user);

        return user;
    }

    public String loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user == null || !verifyPassword(loginDTO.getPassword(), user.getPassword())) {
            return null;
        }

        String sessionToken = generateSessionToken();

        UserSession userSession = new UserSession();

        userSession.setToken(sessionToken);
        userSession.setExpiredAt(calculateExpirationDate());
        userSession.setCreatedAt(new Date());
        userSession.setUpdatedAt(new Date());

        userSessionRepository.save(userSession);
        return sessionToken;
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    private String generateUserId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    private Boolean verifyPassword(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    private String generateSessionToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        return calendar.getTime();
    }








}
