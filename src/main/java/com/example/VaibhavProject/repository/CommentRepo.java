package com.example.VaibhavProject.repository;

import com.example.VaibhavProject.model.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CommentRepo extends ElasticsearchRepository<Comment,String> {
    List<Comment> findByArticleIdAndParentComment(String articleId,boolean parentComment);
}

