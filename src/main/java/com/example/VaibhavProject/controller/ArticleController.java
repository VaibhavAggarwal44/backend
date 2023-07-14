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

//    @GetMapping("/articles")
//    public List<Article> getAllArticles(){
//        List<Article> list=articleService.findPublicArticles();
//        return list;
//    }

    @GetMapping("/checker/{id}")
    public List<Article> findByUsername(@PathVariable String id){
        return articleService.findbyusername(id);
    }

    @GetMapping("/public/{query}")
    public List<Article> findPublicArticles(@PathVariable String query){
        String word=query.replace("--"," ");
        return articleService.searchPublicArticles(word);
    }

    @GetMapping("/articles/sortLike/{username}")
    public List<Article> sortByLikes(@PathVariable String username){
        List<Article> list=articleService.findPublicArticles(username);

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

    @GetMapping("/articles/sortView/{username}")
    public List<Article> sortByViews(@PathVariable String username){
        List<Article> list=articleService.findPublicArticles(username);

        Article article=new Article();
        article.V_SORT(list);

        return list;
    }

    @PostMapping("/insert")
    public Article insertArticle(@RequestBody Article article){return articleService.insertArticle(article);}

    @PostMapping("/update")
    public Article updateArticle(@RequestBody Article article){return articleService.updateArticle(article);}

    @GetMapping("/{id}/{user}/{ld}")
    public Article updateArticleLike(@PathVariable String id,@PathVariable String user,@PathVariable String ld){
        System.out.println("check3");
        Article article=articleService.findById(id);
        if(ld.equals("like"))
            article=articleService.updateArticleLikes(article,id,user);
        else if(ld.equals("dislike"))
            article=articleService.updateArticleDislikes(article,id,user);
        return article;
    }

//    @GetMapping("/dislike")
//    public Article updateArticleDislike(@PathVariable String id,@PathVariable String dislikedby){
//        System.out.println("check 2");
//        Article article=articleService.findById(id);
////        article=articleService.updateArticleDislikes(article,id,dislikedby);
//        return article;
//    }

    @GetMapping("/{id}")
    public Article updateArticleView(@PathVariable String id){
        Article article=articleService.findById(id);
        article=articleService.updateArticleViews(article,id);
        return article;
    }

    @GetMapping("/{id}/tester")
    public void updateViewTest(@PathVariable String id){
        Article article=articleService.findById(id);
        articleService.updateArticleViewsTest(article,id);
        return ;
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
            List<Article> list = infixFinder(word);
            if(list.isEmpty()){
                int i = 0;
                for (Hit<Article> item : listOfHits) {
                    if (i == 10) break;
                    i++;
                    list.add(item.source());
                }
                System.out.println("damn");
                return list;
            }else{
                return list;
            }
//            int i = 0;
//            for (Hit<Article> item : listOfHits) {
//                if (i == 10) break;
//                i++;
//                list.add(item.source());
//            }
//            return list;
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

    @GetMapping("/infix/{query}")
    public List<Article> infixFinder(@PathVariable String query){
        return articleService.infixFinder(query);
    }

    @GetMapping("/{username}/getPublic")
    public List<Article> getArticlesByUsername(@PathVariable String username){
        return articleService.findArticlesByUsername(username);
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
