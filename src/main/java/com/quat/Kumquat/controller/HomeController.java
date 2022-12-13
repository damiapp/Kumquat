package com.quat.Kumquat.controller;

import com.quat.Kumquat.dto.ProductDto;
import com.quat.Kumquat.dto.UserDto;
import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.User;
import com.quat.Kumquat.service.ProductService;
import com.quat.Kumquat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {
    private UserService userService;
    private ProductService productService;

    @Autowired
    public HomeController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/index")
    public String home(){
        return "index.html";
    }

    @GetMapping("/login")
    public String login(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken){
            UserDto user = new UserDto();
            model.addAttribute("user", user);
            return "registration";
        }
        return "redirect:/";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/registration";
        }

        userService.saveUser(userDto);
        return "redirect:/registration?success";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        Set<ProductDto> items = productService.getAllProducts();
        model.addAttribute("items", items);
        return "shop";
    }

    @GetMapping("/items")
    public String items(Model model){
        ProductDto product = new ProductDto();
        model.addAttribute("product", product);
        Set<ProductDto> items = productService.getAllProducts();
        model.addAttribute("items", items);
        return "items";
    }

    @PostMapping(value ="/items/save", params="submit")
    public String itemSave(@Valid @ModelAttribute("item") ProductDto productDto,
                               BindingResult result,
                               Model model){
        if(result.hasErrors()){
            model.addAttribute("item", productDto);
            return "items";
        }

        productService.saveProduct(productDto);
        return "redirect:/items?success";
    }
    
    @PostMapping(value="/items/save", params="edit")
    public String itemChange(@Valid @ModelAttribute("item") ProductDto productDto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("item", productDto);
            return "items";
        }

        productService.changeProduct(productDto);
        return "redirect:/items?success";
    }
}