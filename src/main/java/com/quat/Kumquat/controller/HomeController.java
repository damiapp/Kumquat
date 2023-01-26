package com.quat.Kumquat.controller;

import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.ProductOrder;
import com.quat.Kumquat.model.User;
import com.quat.Kumquat.service.ProductOrderService;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private UserService userService;
    private ProductService productService;
    private ProductOrderService productOrderService;

    @Autowired
    public HomeController(UserService userService,
                          ProductService productService,
                          ProductOrderService productOrderService) {
        this.userService = userService;
        this.productService = productService;
        this.productOrderService = productOrderService;
    }

    @GetMapping("/index")
    public String home(){
        return "index.html";
    }

    @GetMapping("/login")
    public String login(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken){
            User user = new User();
            model.addAttribute("user", user);
            return "registration";
        }
        return "redirect:/";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(user.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/registration";
        }

        userService.saveUser(user);
        return "redirect:/registration?success";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/items")
    public String items(
            @RequestParam(value = "prodName", required = false) String prodName,
            Model model){
        Product product;
        if(prodName!=null) {
            product = productService.getProduct(Integer.parseInt(prodName));
        }
        else{
            product = new Product();
        }
        model.addAttribute("product", product);
        return "items";
    }

    @PostMapping(value ="/items/save", params="submit")
    public String itemSave(@Valid @ModelAttribute("item") Product product,
                               BindingResult result,
                               Model model){
        if(result.hasErrors()){
            model.addAttribute("item", product);
            return "items";
        }

        productService.saveProduct(product);
        return "redirect:/items?success";
    }
    
    @PostMapping(value="/items/save", params="edit")
    public String itemChange(@Valid @ModelAttribute("item") Product product,
                           @RequestParam(value = "prodName", required = true) String prodName,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("item", product);
            return "items";
        }
        productService.changeProduct(Integer.parseInt(prodName),product);
        return "redirect:/items?success";
    }

    @GetMapping(value="/order/save")
    public String itemOrder(@Valid @ModelAttribute("item") Product product,
                            @RequestParam("prodId") long prodId,
                             BindingResult result,
                             Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Product prod = productService.findProduct(prodId);
        productOrderService.addProductForUser(user,prod);
        return "redirect:/shop?success";
    }

    @GetMapping(value = "/orders")
    public String allProductOrders(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(user.getRoles().get(0).getName());
        if(user.getRoles().get(0).getName().equals("ROLE_USER")){
            List<ProductOrder> productOrders = productOrderService.findAllProductOrdersForUser(user);
            model.addAttribute("productOrders",productOrders);
        }
        else{
            List<User> users = userService.findAllUsers();
            List<User> users1 = new ArrayList<User>();
            for(User u:users){
                if(!u.getOrders().isEmpty()){
                    users1.add(u);
                }
            }
            model.addAttribute("users",users1);
        }
        return "order";
    }

    @GetMapping(value = "/ship")
    public String allProductOrders(@RequestParam("prodOrderId") long prodOrderId,
            Model model){
        productOrderService.setStatusSent(prodOrderId);
        return "redirect:/orders";
    }
}