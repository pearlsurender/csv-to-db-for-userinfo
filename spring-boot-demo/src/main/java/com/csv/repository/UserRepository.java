package com.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csv.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
