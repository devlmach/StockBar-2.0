package com.example.projeto.controller;

import com.example.projeto.model.Product;
import com.example.projeto.repository.ProductRepository;
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

@RequestMapping()
@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/createForm")
    public String home() {
        return "createForm";
    }

    @GetMapping("/cadastroProduto")
    public String create() {
        return "cadastroProduto";
    }

    @PostMapping("/cadastroProduto")
    public String cadastrar(@ModelAttribute @Valid Product newProduct, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Erro ao cadastrar o produto");
            return "cadastroProduto";
        }

        Product save = productRepository.save(newProduct);
        model.addAttribute("successMessage", "Produto Cadastrado");
        return "cadastroProduto";
    }

    @GetMapping( "/listaProduto" )
    public String list( Model model, @RequestParam(defaultValue = "0") int page ) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Product> product = productRepository.findAll( pageable );
        model.addAttribute( "product", product );
        return "listaProduto";
    }

    @GetMapping("/deleteProduto/{id}")
    public String deleteProduto(@PathVariable(value = "id") Product id) {
        productRepository.delete(id);

        return "redirect:/listaProduto";
    }

    @GetMapping("/editProduct/{id}")
    public ModelAndView editar(@PathVariable(value = "id") Integer id) throws IllegalAccessException {
        ModelAndView mv = new ModelAndView( "editProduct");
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new IllegalAccessException("produto nao encontrado"));

        mv.addObject("product", findProduct);
        return mv;
    }
}

