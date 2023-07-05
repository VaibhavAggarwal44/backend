package com.example.VaibhavProject.repository;

import com.example.VaibhavProject.model.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends ElasticsearchRepository<Article,String> {

}
