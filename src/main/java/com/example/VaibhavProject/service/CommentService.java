package com.example.VaibhavProject.service;

import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.model.Comment;
import com.example.VaibhavProject.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    public Iterable<Comment> getAllComments(){
        return commentRepo.findAll();
    }

    public Comment findbyid(String id) throws NoSuchElementException {
        return commentRepo.findById(id).get();
    }

    public Comment insertComment(Comment comment){
        comment.setParentComment(true);
        return commentRepo.save(comment);}

    public List<Comment> findArticles(String articleId){
        return commentRepo.findByArticleIdAndParentComment(articleId,true);
    }

    public Iterable<Comment> findArticlesAll(){
        return commentRepo.findAll();
    }

    public void deleteComment(String commentId){
        commentRepo.deleteById(commentId);
    }

    public Comment insertReply(Comment reply,String commentId){
        Comment comment=commentRepo.findById(commentId).get();
        comment.getReplies().add(reply);
        reply.setParentComment(false);
        commentRepo.save(comment);
        commentRepo.save(reply);
        return reply;
    }

}
