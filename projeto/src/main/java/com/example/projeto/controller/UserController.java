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
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@RequestMapping("/")
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/createFormUser")
    public String home() {
        return "createFormUser";
    }

    @GetMapping("/cadastroUsuario")
    public String create() {
        return "cadastroUsuario";
    }

    @PostMapping("/cadastroUsuario")
    public String cadastrar(@ModelAttribute @Valid User newUser, BindingResult result, Model model) {

        Random random = new Random();
        int comanda = random.nextInt(100);

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Erro ao cadastrar o usuário");
            return "cadastroUsuario";
        }

        if (userRepository.existsByCpf(newUser.getCpf())) {
            model.addAttribute("errorMessage", "CPF JÁ EXISTE");
            return "cadastroUsuario";
        }

        newUser.setComand(comanda);
        User save = userRepository.save(newUser);

        model.addAttribute("successMessage", "Usuário Cadastrado");
        model.addAttribute("comanda", newUser.getComand());
        return "cadastroUsuario";
    }

    @GetMapping( "/listaUsuario" )
    public String list( Model model, @RequestParam(defaultValue = "0") int page ) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<User> user = userRepository.findAll( pageable );
        model.addAttribute( "user", user );
        return "listaUsuario";
    }

    @GetMapping("/deleteUsuario/{id}")
    public String deleteUsuario(@PathVariable(value = "id") User id) {
        userRepository.delete(id);

        return "redirect:/listaUsuario";
    }

    @GetMapping("/editUsuario/{id}")
    public ModelAndView editar(@PathVariable(value = "id") Integer id) throws IllegalAccessException {
        ModelAndView mv = new ModelAndView( "editUsuario");
        User finduser = userRepository.findById(id).orElseThrow(() -> new IllegalAccessException("cliente nao encontrado"));

        if (userRepository.existsByCpf(finduser.getCpf())) {
            finduser.setName(finduser.getName());
            finduser.setAddress(finduser.getAddress());
            finduser.setRole(finduser.getRole());
        }

        mv.addObject("user", finduser);
        mv.addObject("roles", finduser.getRole());
        return mv;
    }
}
