package com.krishi.fls.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.krishi.fls.entity.Users;

@Component
@Repository
public interface UserDao {

  public Users findById(int userId);
}
