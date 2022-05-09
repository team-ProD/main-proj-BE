package com.example.demo.mapper;

import com.example.demo.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by Hyunsik Lee on 2022-05-08. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Mapper
@Repository
public interface UserMapper {
   User findByEmail(String username);
   User join(User user);
}
