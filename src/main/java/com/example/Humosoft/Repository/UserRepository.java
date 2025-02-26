package com.example.Humosoft.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.User;

public interface UserRepository  extends  JpaRepository<User,Integer>{

}
