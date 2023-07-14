package com.example.VaibhavProject.repository;

//import co.elastic.clients.elasticsearch.ml.Page;
import com.example.VaibhavProject.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends ElasticsearchRepository<Article,String> {
    @Query("{\"match\": {\"createdBy\": {\"query\": \"?0\"}}}")
    Page<Article> findByName(String name, Pageable pageable);


    List<Article> findByCreatedBy(String name);
    List<Article> findByArticleBodyAndIsPublic(String query,boolean isPublic);
    List<Article> findByIsPublicOrCreatedBy(boolean isPublic,String createdBy);

    List<Article> findByCreatedByAndIsPublic(String createdBy,boolean isPublic);

    List<Article> findByArticleBodyContainingAndIsPublic(String s,boolean isPublic);
}
