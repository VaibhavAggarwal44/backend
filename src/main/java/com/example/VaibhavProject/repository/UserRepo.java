package com.example.VaibhavProject.repository;

import com.example.VaibhavProject.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends ElasticsearchRepository<User,String> {
}
