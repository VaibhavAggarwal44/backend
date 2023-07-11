package com.example.VaibhavProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "comments")
public class Comment {
    @Id
    public String commentId;

    public String username;

    public String articleId;

    public String content;

    public boolean parentComment;

    public List<Comment> replies;

    public static String generateUniqueID() {
        // Generate a random UUID (Universally Unique Identifier)
        UUID uuid = UUID.randomUUID();

        // Remove the hyphens from the UUID and take the first 10 characters
        String id = uuid.toString().replaceAll("-", "").substring(0, 10);

        return id;
    }

    public Comment(){
        this.commentId=generateUniqueID();
        this.replies=new ArrayList<Comment>();
    }
}
