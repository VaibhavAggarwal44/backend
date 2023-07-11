package com.example.VaibhavProject.service;

import com.example.VaibhavProject.model.Comment;
import com.example.VaibhavProject.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    public Comment insertComment(Comment comment){return commentRepo.save(comment);}

    public List<Comment> findArticles(String articleId){
        return commentRepo.findByArticleIdAndParentComment(articleId,true);
    }

    public Comment insertReply(Comment reply,String commentId){
        Comment comment=commentRepo.findById(commentId).get();
        reply.getReplies().add(reply);
        reply.setParentComment(false);
        return reply;
    }

}
