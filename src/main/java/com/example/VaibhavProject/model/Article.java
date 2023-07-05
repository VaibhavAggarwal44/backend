package com.example.VaibhavProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

//@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "article1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
//    public int id;
    public String id;
    public int likes;
//    public int dislikes;
    public boolean isPublic;
    public int views;
    public String articleBody;
    public String heading;

    public String createdBy;

    public static String generateUniqueID() {
        // Generate a random UUID (Universally Unique Identifier)
        UUID uuid = UUID.randomUUID();

        // Remove the hyphens from the UUID and take the first 10 characters
        String id = uuid.toString().replaceAll("-", "").substring(0, 10);

        return id;
    }

    public Article(){
        this.id=generateUniqueID();
    }



    static class LikeComparator implements Comparator<Article> {
        @Override
        public int compare(Article article1, Article article2) {
            return Integer.compare(article2.getLikes(), article1.getLikes());
        }
    }

    static class DisLikeComparator implements Comparator<Article> {
        @Override
        public int compare(Article article1, Article article2) {
            return Integer.compare(article1.getLikes(), article2.getLikes());
        }
    }

    static class ViewComparator implements Comparator<Article> {
        @Override
        public int compare(Article article1, Article article2) {
            return Integer.compare(article2.getViews(), article1.getViews());
        }
    }

    public List<Article> L_SORT(List<Article> list){
        Collections.sort(list,new Article.LikeComparator());
        return list;
    }

    public List<Article> D_SORT(List<Article> list){
        Collections.sort(list,new Article.DisLikeComparator());
        return list;
    }

    public List<Article> V_SORT(List<Article> list){
        Collections.sort(list,new Article.ViewComparator());
        return list;
    }

}
