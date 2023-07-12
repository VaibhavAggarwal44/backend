package com.example.VaibhavProject.service;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.repository.ArticleRepo;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    public Page<Article> getById(String id){
        System.out.println("checker");
        Pageable pageable= PageRequest.of(0, 5, Sort.by(
                Order.asc("createdBy")));

        return articleRepo.findByName(id,pageable);
    }

    public List<Article> findbyusername(String id){
        return articleRepo.findByCreatedBy(id);
    }

    public List<Article> searchPublicArticles(String query){
        return articleRepo.findByArticleBodyAndIsPublic(query,true);
    }

    public List<Article> findPublicArticles(){
        return articleRepo.findByIsPublic(true);
    }

    public Iterable<Article> getArticles(){
        return articleRepo.findAll();
    }

    public Article insertArticle(Article article){
        return articleRepo.save(article);
    }

    public Article findById(String id){return articleRepo.findById(id).get();}

    public Article updateArticleLikes(Article article, String id,String likedBy) {
        Article article1  = articleRepo.findById(id).get();
        if(article1.getLikedBy().contains(likedBy)){
            return article1;
        }
        if(article1.getDislikedBy().contains(likedBy)){
            article1.setDislikes(article1.getDislikes()-1);
        }
        article1.setLikes(article.getLikes()+1);
        String s=article1.getLikedBy();
        s+=likedBy+" ";
        article1.setLikedBy(s);
        String s2=article1.getDislikedBy();
        String replace = s2.replace(likedBy+" ", "");
        article1.setDislikedBy(replace);
        article=article1;
        articleRepo.save(article);
        return article;
    }

    public Article updateArticleDislikes(Article article, String id,String dislikedBy) {
        System.out.println("check");
        Article article1  = articleRepo.findById(id).get();
        if(article1.getDislikedBy().contains(dislikedBy)){
            return article1;
        }
        if(article1.getLikedBy().contains(dislikedBy)){
            article1.setLikes(article1.getLikes()-1);
        }
        article1.setDislikes(article.getDislikes()+1);
        String s=article1.getDislikedBy();
        s+=dislikedBy+" ";
        article1.setDislikedBy(s);
        String s2=article1.getLikedBy();
        String replace = s2.replace(dislikedBy+" ", "");
        article1.setLikedBy(replace);
        article=article1;
        articleRepo.save(article);
        return article1;
    }


    public Article updateArticleViews(Article article, String id) {
        Article article1  = articleRepo.findById(id).get();
        article1.setViews(article.getViews()+1);
        article=article1;
        articleRepo.save(article);
        return article1;
    }

    public void updateArticleViewsTest(Article article, String id) {
        Article article1  = articleRepo.findById(id).get();
        article1.setViews(article.getViews()-1);
        article=article1;
        articleRepo.save(article);
        return ;
    }

    public Article updateArticle(Article article) {
        articleRepo.save(article);
        return article;
    }

    private ElasticsearchOperations elasticsearchOperations;

//    public List<Map<String, Object>> searchDocumentsWithArrayElement(String indexName, String fieldName, String searchValue) throws IOException {
//        SearchRequest searchRequest = new SearchRequest(indexName);
//
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.timeout(TimeValue.timeValueSeconds(30));
//
//        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery(fieldName, searchValue);
//        sourceBuilder.query(queryBuilder);
//
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = elasticsearchOperations.getClient().search(searchRequest, RequestOptions.DEFAULT);
//        SearchHits hits = searchResponse.getHits();
//
//        List<Map<String, Object>> documents = new ArrayList<>();
//        for (SearchHit hit : hits.getHits()) {
//            Map<String, Object> document = hit.getSourceAsMap();
//            documents.add(document);
//        }
//
//        return documents;
//    }



//    public Iterable<Article> customSearch(String word){return articleRepo.}

    public void deleteArticle(){
        articleRepo.deleteAll();
    }

}
