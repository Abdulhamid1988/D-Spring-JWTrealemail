package com.example.jwtrealemail.sevice;

import com.example.jwtrealemail.entity.User;
import com.example.jwtrealemail.entity.enums.RoleName;
import com.example.jwtrealemail.payload.ApiResponse;
import com.example.jwtrealemail.payload.RegisterDto;
import com.example.jwtrealemail.repository.RoleRepository;
import com.example.jwtrealemail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public ApiResponse registerUser(RegisterDto registerDto){
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday email allaqchon mavjud",false);
        }
        User user=new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Muvoffaqiyatli utingiz, emailingizni tekshiring",true);
    }

    public boolean sendEmail(String sendingEmail, String emailCode){
        try{
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("khamidjon.khakimov@mail.ru");
        simpleMailMessage.setTo(sendingEmail);
        simpleMailMessage.setSubject("Accountni Tasdiqlash");
        simpleMailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlash</a>");
        javaMailSender.send(simpleMailMessage);
        return true;
        }catch (Exception e){
            return false;
        }
    }
}
