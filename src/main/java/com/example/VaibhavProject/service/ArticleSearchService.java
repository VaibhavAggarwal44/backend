package com.example.VaibhavProject.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.utils.ArticleSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;

@Service
public class ArticleSearchService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public SearchResponse<Article> matchAllArticleService(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Article> matchArticleWithWordService(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplierQueryWithWord(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Article> matchAllHeadingService(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier2(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }
}
