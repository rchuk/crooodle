package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.service.AuthService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Відображення форми логіну
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginRequestDto", new UserLoginRequestDto());
        return "login"; // Повертає шаблон login.html
    }

    // Обробка логіну
    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginRequestDto requestDto, Model model) {
        try {
            authService.login(requestDto);
            return "redirect:/"; // Редирект на головну сторінку після успішного логіну
        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login"; // Повертає шаблон login.html з повідомленням про помилку
        }
    }

    // Відображення форми реєстрації
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterRequestDto", new UserRegisterRequestDto());
        return "register"; // Повертає шаблон register.html
    }

    // Обробка реєстрації
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserRegisterRequestDto requestDto, Model model) {
        try {
            authService.register(requestDto);
            return "redirect:/auth/login"; // Редирект на сторінку логіну після успішної реєстрації
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Email might be already in use.");
            return "register"; // Повертає шаблон register.html з повідомленням про помилку
        }
    }
}
