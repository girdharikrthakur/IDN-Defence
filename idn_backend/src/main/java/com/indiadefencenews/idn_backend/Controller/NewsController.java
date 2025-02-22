package com.indiadefencenews.idn_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.indiadefencenews.idn_backend.Model.News;
import com.indiadefencenews.idn_backend.Services.NewsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public Optional<News> getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    @GetMapping("/category/{category}")
    public List<News> getNewsByCategory(@PathVariable String category) {
        return newsService.getNewsByCategory(category);
    }

    @PostMapping
    public News addNews(@RequestBody News news) {
        return newsService.addNews(news);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
