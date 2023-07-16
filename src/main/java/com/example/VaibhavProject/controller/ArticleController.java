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
        List<Article> list=articleService.findPublicArticles("");
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

        Article article=new Article();
        article.D_SORT(list);

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


    @PostMapping("/final/delete")
    public void deleteAll() {
//        Article article;
        articleService.deleteArticle();
    }

    @GetMapping("/search/{word}/{username}")
    public List<Article> searchArticlesWithWord(@PathVariable String word,@PathVariable String username) throws IOException {
        if(!word.contains("--")) {
            SearchResponse<Article> searchResponse = articleSearchService.matchArticleWithWordService(word);
            System.out.println(searchResponse.hits().hits().toString());
            List<Hit<Article>> listOfHits = searchResponse.hits().hits(); //fuzzy
            List<Article> hlist=articleService.headingInfix(word);  //heading and ispublic
            List<Article> hlist2=articleService.headingInfix2(word,username); //heading and username
            List<Article> list = infixFinder(word);  //articlebody and ispublic
            List<Article> list2=articleService.infixFinder2(word,username);  //articlebody and username

            hlist.addAll(hlist2);
            hlist.addAll(list2);
            hlist.addAll(list);

            Set<Article> set=new LinkedHashSet<>(hlist);
            hlist.clear();
            hlist.addAll(set);

            if(hlist.isEmpty()){
                for (Hit<Article> item : listOfHits) {
                    hlist.add(item.source());
                }
                System.out.println("damn");
                return hlist;
            }else{
                return hlist;
            }
        }else{
            String query=word.replace("--"," ");
            SearchResponse<Article> searchResponse = articleSearchService.matchAllArticleService(query);
            SearchResponse<Article> searchResponse2=articleSearchService.matchAllHeadingService(query);
            System.out.println(searchResponse.hits().hits().toString());
            List<Hit<Article>> listOfHits = searchResponse.hits().hits();
            List<Hit<Article>> listOfHits2 = searchResponse2.hits().hits();
            System.out.println("contension");

            List<Article> list = new ArrayList<>();

            for (Hit<Article> item : listOfHits2) {
                list.add(item.source());
            }

            for (Hit<Article> item : listOfHits) {
                list.add(item.source());
            }

            Set<Article> set=new LinkedHashSet<>(list);
            list.clear();
            list.addAll(set);

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
