package com.quat.Kumquat.controller;

import com.quat.Kumquat.model.Inbox;
import com.quat.Kumquat.model.Product;
import com.quat.Kumquat.model.ProductOrder;
import com.quat.Kumquat.model.User;
import com.quat.Kumquat.service.InboxService;
import com.quat.Kumquat.service.ProductOrderService;
import com.quat.Kumquat.service.ProductService;
import com.quat.Kumquat.service.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private UserService userService;
    private ProductService productService;
    private ProductOrderService productOrderService;
    private InboxService inboxService;

    @Autowired
    public HomeController(UserService userService,
                          ProductService productService,
                          ProductOrderService productOrderService,
                          InboxService inboxService) {
        this.userService = userService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.inboxService=inboxService;
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
        prod.setStock(prod.getStock()-1);
        productService.saveProduct(prod);
        productOrderService.addProductForUser(user,prod);
        return "redirect:/shop?success";
    }

    @GetMapping(value = "/orders")
    public String allProductOrders(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
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
        //send msg for shiping
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User sender = userService.findUserByEmail(auth.getName());
        ProductOrder productOrder = productOrderService.findById(prodOrderId);
        User reciever = productOrder.getUser();
        Product product = productOrder.getProduct();
        String title = "Your " + product.getItemName() + " has been shiped";
        String msg = "Thank you " + reciever.getName() + " for ordering item " + product.getItemName() +
                " at the price of " + product.getPrice() + "RSD!";
        inboxService.sendMessage(sender,reciever,title,msg);
        return "redirect:/orders";
    }

    @GetMapping(value = "/inbox")
    public String allMessages(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Inbox> inbox = user.getInbox();
        model.addAttribute("inbox",inbox);
        return "inbox";
    }

    @GetMapping(value="generisiIzvestaj")
    public void generisiIzvestaj(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Inbox> inbox = user.getInbox();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(inbox);
        InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/inbox.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name",user.getName());
        params.put("mail",user.getEmail());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        inputStream.close();
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=InboxReport.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }

    @GetMapping(value="vratiIzvestaj")
    public void vratiIzvestaj(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<ProductOrder> productOrders = productOrderService.findAll();
        List<ProductOrder> productOrders1 = new ArrayList<ProductOrder>();
        for(ProductOrder po: productOrders){
            if(po.isStatus())
                productOrders1.add(po);
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productOrders1);
        InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/shop.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name",user.getName());
        params.put("mail",user.getEmail());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        inputStream.close();
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=OrderReport.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}