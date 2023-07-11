package com.example.VaibhavProject.controller;

import com.example.VaibhavProject.model.Comment;
import com.example.VaibhavProject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/insert")
    public Comment createComment(@RequestBody Comment comment){
        return commentService.insertComment(comment);
    }

    @PostMapping("/{commentId}/insert")
    public Comment createReply(@RequestBody Comment reply,@PathVariable String commentId){
        return commentService.insertReply(reply,commentId);
    }

    @GetMapping("/{articleId}")
    public List<Comment> getComments(@PathVariable String articleId){
        return commentService.findArticles(articleId);
    }
}
