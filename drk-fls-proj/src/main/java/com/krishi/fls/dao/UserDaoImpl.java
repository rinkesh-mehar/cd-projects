package com.krishi.fls.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.krishi.fls.entity.Users;
import com.krishi.fls.repository.UserRepository;

@Repository
@Component
public class UserDaoImpl implements UserDao{

  @Autowired
  private UserRepository userRepo;
  
  @Override
  public Users findById(int userId) {
    if(userRepo.findById(userId).isPresent()) {
    	return userRepo.findById(userId).get();	
    }else {
    	return null;
    }
    
  }
  
  

}
