package com.example.doan.controller;

import com.example.doan.config.MyUserDetails;
import com.example.doan.dto.UserDto;
import com.example.doan.entity.Book;
import com.example.doan.entity.InterativeBook;
import com.example.doan.repository.BookRepository;
import com.example.doan.repository.InterativeBookRepository;
import com.example.doan.repository.PublisherBookRepository;
import com.example.doan.repository.PublisherRepository;
import com.example.doan.service.BookService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private PublisherBookRepository publisherBookRepository;
    @Autowired
    private InterativeBookRepository interativeBookRepository;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("viewBookDetail")
    String getBookDetail(ModelMap modelMap, @RequestParam("bookId") Integer bookId){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = myUserDetails.getUser().getUserId();
        modelMap.addAttribute("userId", userId);

        InterativeBook interativeBook = new InterativeBook();
        interativeBook.setBookId(bookId);
        interativeBook.setUserId(userId);
        interativeBook.setRating(1);
        System.out.println(interativeBookRepository.save(interativeBook));

        List<String> summaryList = new ArrayList<>();
        String summary = "";
        String publisher = publisherRepository.getPublisher(bookId);
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
        modelMap.addAttribute("publicationYear", book.getOriginalPublicationYear().substring(0, book.getOriginalPublicationYear().length()-2));
        modelMap.addAttribute("ratingAvg", book.getAverageRating());
        modelMap.addAttribute("publisher", publisher);
        modelMap.addAttribute("author", book.getAuthors());
        System.out.println("ninh duy dang ");
        System.out.println(book.getOriginalPublicationYear().substring(0, book.getOriginalPublicationYear().length()-2));

        List<InterativeBook> interativeBooks = interativeBookRepository.findDistinctByBookId(bookId);
        List<Book> bookAnathorRecommendList = new ArrayList<>();
        for (int i=0; i<6; i++){
            System.out.println(interativeBooks.get(i).getUserId());
            System.out.println(i);
            UserDto userDto = new UserDto();
            userDto.setUserId(interativeBooks.get(i).getUserId());
            String grades = restTemplate.postForObject("/predict", userDto, String.class);
            List<Book> listBook = bookService.getListBook(grades);
            List<Book> bookAnathorRecommend = listBook.stream().limit(1).collect(Collectors.toList());
            bookAnathorRecommendList.add(bookAnathorRecommend.get(0));
        }
        modelMap.addAttribute("bookAnathorRecommendList", bookAnathorRecommendList);
        return "viewBookDetail";
    }

    @PostMapping("createBook")
    public String createBook(ModelMap modelMap,
                             @RequestParam("bookId") Integer bookId,
                             @RequestParam("author") String author,
                             @RequestParam("bestBookId") Integer bestBookId,
                             @RequestParam("workId") Integer workId ,
                             @RequestParam("imageUrl") String imageUrl,
                             @RequestParam("title") String title ,
                             @RequestParam("originalPublicationYear") String originalPublicationYear,
                             @RequestParam("average_rating") Float average_rating){

        // Lấy thông tin chi tiết người dùng hiện tại từ SecurityContextHolder
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDetails.getUser().getUserId();
        modelMap.addAttribute("userId", userId);
        try {
            // Tạo đối tượng Book mới
            Book book = new Book();
            book.setBookId(bookId);
            book.setAuthors(author);
            book.setBestBookId(bestBookId);
            book.setWorkId(workId);
            book.setImageUrl(imageUrl);
            book.setTitle(title);
            book.setOriginalPublicationYear(originalPublicationYear);
            book.setAverageRating(average_rating);
         bookService.createBook(book);

            modelMap.addAttribute("message", "Sách đã được tạo thành công!");
        } catch (Exception e) {

            modelMap.addAttribute("message", "Có lỗi xảy ra khi tạo sách: " + e.getMessage());
        }

        return "createBookView";
    }


}

