package com.example.projeto.controller;

import com.example.projeto.model.User;
import com.example.projeto.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "createForm";
    }

    @GetMapping("/cadastroUsuario")
    public String create() {
        return "cadastroUsuario";
    }

    @PostMapping("/cadastroUsuario")
    public String cadastrar(@ModelAttribute @Valid User newUser, BindingResult result, Model model, Exception e) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Erro ao cadastrar o usuário");
            return "cadastroUsuario";
        }

        if (userRepository.existsByCpf(newUser.getCpf())) {
            model.addAttribute("errorMessage", "CPF JÁ EXISTE");
            return "cadastroUsuario";
        }

        User save = userRepository.save(newUser);
        model.addAttribute("successMessage", "Usuário Cadastrado");
        return "cadastroUsuario";
    }

    @GetMapping( "/lista" )
    public String list( Model model, @RequestParam(defaultValue = "0") int page ) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<User> user = userRepository.findAll( pageable );
        model.addAttribute( "user", user );
        return "lista";
    }
}
