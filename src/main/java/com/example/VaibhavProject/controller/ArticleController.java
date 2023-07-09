package com.example.VaibhavProject.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.service.ArticleSearchService;
import com.example.VaibhavProject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/apis")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleSearchService articleSearchService;

    @GetMapping("/articles")
    public List<Article> getAllArticles(){
        List<Article> list=articleService.findPublicArticles();
        return list;
    }

    @GetMapping("/checker/{id}")
    public List<Article> findByUsername(@PathVariable String id){
        return articleService.findbyusername(id);
    }

    @GetMapping("/public/{query}")
    public List<Article> findPublicArticles(@PathVariable String query){
        String word=query.replace("--"," ");
        return articleService.searchPublicArticles(word);
    }

    @GetMapping("/articles/sortLike")
    public List<Article> sortByLikes(){
        List<Article> list=articleService.findPublicArticles();

        Article article=new Article();
        article.L_SORT(list);

        return list;
    }

    @GetMapping("/articles/userArticles")
    public Iterable<Article> userArticles(){
        Iterable<Article> list1=articleService.getArticles();
        List<Article> list=new ArrayList<>();

        for(Article item:list1){
            if(item.isPublic) {
                list.add(item);
            }
        }

        list1=list;
        return list1;
    }

    @GetMapping("/articles/sortDislike")
    public Iterable<Article> sortByDislikes(){
        Iterable<Article> list1=articleService.getArticles();
        List<Article> list=new ArrayList<>();

        for(Article item:list1){
            list.add(item);
        }

//        Collections.sort(list);list
        Article article=new Article();
        article.D_SORT(list);

//        for(Article item:list){
//            System.out.println(item);
//        }

        list1=list;
        return list1;
    }

    @GetMapping("/articles/sortView")
    public List<Article> sortByViews(){
        List<Article> list=articleService.findPublicArticles();

        Article article=new Article();
        article.V_SORT(list);

        return list;
    }

    @PostMapping("/insert")
    public Article insertArticle(@RequestBody Article article){return articleService.insertArticle(article);}

    @PostMapping("/{id}/like")
    public Article updateArticleLike(@PathVariable String id){
        Article article=articleService.findById(id);
        article=articleService.updateArticleLikes(article,id);
        return article;
    }

    @PostMapping("/{id}/dislike")
    public Article updateArticleDislike(@PathVariable String id){
        Article article=articleService.findById(id);
        article=articleService.updateArticleDislikes(article,id);
        return article;
    }

    @GetMapping("/{id}")
    @CrossOrigin("http://localhost:3000/view/article")
    public Article updateArticleView(@PathVariable String id){
        Article article=articleService.findById(id);
        article=articleService.updateArticleViews(article,id);
        return article;
    }

//    @GetMapping("/matchAll")
//    public String matchAll() throws IOException {
//        SearchResponse<Map> searchResponse=articleSearchService.matchAllService();
//        System.out.println(searchResponse.hits().hits().toString());
//        return searchResponse.hits().hits().toString();
//    }

    @PostMapping("/final/delete")
    public void deleteAll() {
//        Article article;
        articleService.deleteArticle();
    }

//    @GetMapping("/matchAllArticles")
//    public List<Article> matchAllArticles() throws IOException {
//        SearchResponse<Article> searchResponse=articleSearchService.matchAllArticleService();
//        System.out.println(searchResponse.hits().hits().toString());
//        List<Hit<Article>> listOfHits=searchResponse.hits().hits();
//        List<Article> list=new ArrayList<>();
//        for(Hit<Article> item:listOfHits){
//            list.add(item.source());
//        }
//        return list;
//    }

    @GetMapping("/search/{word}")
    public List<Article> searchArticlesWithWord(@PathVariable String word) throws IOException {
        if(!word.contains("--")) {
            SearchResponse<Article> searchResponse = articleSearchService.matchArticleWithWordService(word);
            System.out.println(searchResponse.hits().hits().toString());
            List<Hit<Article>> listOfHits = searchResponse.hits().hits();
            List<Article> list = new ArrayList<>();
            int i = 0;
            for (Hit<Article> item : listOfHits) {
                if (i == 10) break;
                i++;
                list.add(item.source());
            }
            return list;
        }else{
            String query=word.replace("--"," ");
            SearchResponse<Article> searchResponse = articleSearchService.matchAllArticleService(query);
            System.out.println(searchResponse.hits().hits().toString());
            List<Hit<Article>> listOfHits = searchResponse.hits().hits();
            List<Article> list = new ArrayList<>();
            int i = 0;
            for (Hit<Article> item : listOfHits) {
                if (i == 10) break;
                i++;
                list.add(item.source());
            }
            return list;

        }
    }

    @GetMapping("/articles/privateArticles")
    public Iterable<Article> privateArticles(){
        Iterable<Article> list1=articleService.getArticles();
        List<Article> list=new ArrayList<>();

        for(Article item:list1){
            if(!item.isPublic) {
                list.add(item);
            }
        }

        list1=list;
        return list1;
    }
}
