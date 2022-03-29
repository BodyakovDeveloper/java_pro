package com.koval.spring.controller;

import com.koval.spring.constant.ConstantClass;
import com.koval.spring.service.RoleDaoService;
import com.koval.spring.service.UserDaoService;
import com.koval.spring.captcha.ICaptchaService;
import com.koval.spring.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final RoleDaoService roleDaoService;
    private final UserDaoService userDaoService;
    private final PasswordEncoder passwordEncoder;
    private final ICaptchaService captchaService;

    public RegistrationController(RoleDaoService roleDaoService, UserDaoService userDaoService,
                                  PasswordEncoder passwordEncoder, ICaptchaService captchaService) {
        this.roleDaoService = roleDaoService;
        this.userDaoService = userDaoService;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
    }

    @GetMapping("/signup")
    public String getRegistrationPage() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String addNewUser(@ModelAttribute("userToAdd") UserEntity userEntity,
                             Model model,
                             @RequestParam("g-recaptcha-response") String recaptchaResponse) {

        if (userDaoService.isUserExists(userEntity.getLogin(), userEntity.getEmail())) {
            model.addAttribute("errorMessage", ConstantClass.USER_IS_ALREADY_EXIST_MESSAGE);
            return "signup";
        }

        if (captchaService.processResponse(recaptchaResponse)) {

            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoleEntity(roleDaoService.getRoleByName(ConstantClass.ROLE_USER));
            userDaoService.create(userEntity);

            return "redirect:/login";
        } else {
            model.addAttribute("errorMessage", ConstantClass.CAPTCHA_RESPONSE_ERROR);
            return "signup";
        }
    }
}