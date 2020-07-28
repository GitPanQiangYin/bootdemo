package com.boot.bootdemo.util;

import com.boot.bootdemo.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/7/24 11:46
 */
public interface UserRepository extends ElasticsearchRepository<User, Integer> {

    List<User> findByUsername(String username);
}
