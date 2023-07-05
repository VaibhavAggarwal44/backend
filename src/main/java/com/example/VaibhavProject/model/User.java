package com.example.VaibhavProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.*;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "users1")
public class User {
//    public int id;
    @Id
    public String username;

    public String password;
}
