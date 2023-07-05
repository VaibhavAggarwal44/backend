package com.example.VaibhavProject.service;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.repository.ArticleRepo;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    public Iterable<Article> getArticles(){
        return articleRepo.findAll();
    }

    public Article insertArticle(Article article){
        return articleRepo.save(article);
    }

    public Article findById(String id){return articleRepo.findById(id).get();}

    public Article updateArticleLikes(Article article, String id) {
        Article article1  = articleRepo.findById(id).get();
        article1.setLikes(article.getLikes()+1);
        article=article1;
        articleRepo.save(article);
        return article;
    }

    public Article updateArticleDislikes(Article article, String id) {
        Article article1  = articleRepo.findById(id).get();
        article1.setLikes(article.getLikes()-1);
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
