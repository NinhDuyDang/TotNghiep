package com.example.doan.controller;
import com.example.doan.config.MyUserDetails;
import com.example.doan.dto.UserDto;
import com.example.doan.entity.Book;
import com.example.doan.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecommenderController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BookService bookService;
    @GetMapping("")
    String showHome(ModelMap modelMap){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = myUserDetails.getUser().getUserId();
        modelMap.addAttribute("userId", userId);
        System.out.println(userId);
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        String grades = restTemplate.postForObject("/predict", userDto, String.class);
        List<Book> listBook = bookService.getListBook(grades);
        List<Book> listTop20Book = listBook.stream().limit(20).collect(Collectors.toList());
        modelMap.addAttribute("listTop20Book", listTop20Book);
        return "home";
    }
    @GetMapping("viewAllBook")
    String showAllBook(ModelMap modelMap){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = myUserDetails.getUser().getUserId();
        modelMap.addAttribute("userId", userId);
        System.out.println(userId);
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        String grades = restTemplate.postForObject("/predict", userDto, String.class);
        List<Book> listBook = bookService.getListBook(grades);
        modelMap.addAttribute("listBook", listBook);
        return "viewAllBook";
    }
}
