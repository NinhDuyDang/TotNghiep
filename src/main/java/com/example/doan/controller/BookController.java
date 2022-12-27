package com.example.doan.controller;

import com.example.doan.config.MyUserDetails;
import com.example.doan.dto.UserDto;
import com.example.doan.entity.Book;
import com.example.doan.repository.BookRepository;
import com.example.doan.service.BookService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("viewBookDetail")
    String getBookDetail(ModelMap modelMap, @RequestParam("bookId") Integer bookId){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = myUserDetails.getUser().getUserId();
        modelMap.addAttribute("userId", userId);

        List<String> summaryList = new ArrayList<>();
        String summary = "";
        String publisher = "NXB KIm Dong";
        try {
            String url = "https://www.goodreads.com/book/show/"+bookId;
            Document doc = Jsoup.connect(url).get();
            Elements element = doc.select("span");
            for (int i = 0; i < element.size(); i++) {
                if(i>27 && i<35)
                    summaryList.add(element.get(i).text());
                if(i>40)
                    break;
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        for (int i=0; i < summaryList.size(); i++){
            if(summaryList.get(i).length()>summary.length())
                summary = summaryList.get(i);
        }
        System.out.println(summary);
        Book book = bookRepository.getById(bookId);

        modelMap.addAttribute("titleBook", book.getTitle());
        modelMap.addAttribute("imgBook", book.getImageUrl());
        modelMap.addAttribute("summary", summary);
        modelMap.addAttribute("publicationYear", book.getOriginalPublicationYear());
        modelMap.addAttribute("ratingAvg", book.getAverageRating());
        modelMap.addAttribute("publisher", publisher);
        modelMap.addAttribute("author", book.getAuthors());
        System.out.println("nguyen huu mung");
        return "viewBookDetail";
    }
}
