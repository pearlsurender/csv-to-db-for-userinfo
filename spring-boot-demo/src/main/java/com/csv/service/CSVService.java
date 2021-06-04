package com.csv.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csv.helper.CSVHelper;
import com.csv.model.User;
import com.csv.repository.UserRepository;

@Service
public class CSVService {
  @Autowired
  UserRepository repository;

  public void save(MultipartFile file) {
    List<User> users = CSVHelper.csvToTutorials(file);
      repository.save(users);
  }

  public ByteArrayInputStream load() 
  {
    List<User> users = CSVHelper.failedUsersList;
    ByteArrayInputStream in = CSVHelper.tutorialsToCSV(users);
    CSVHelper.failedUsersList.clear();
    return in;
  }

  public List<User> getAllUsers() {
    return repository.findAll();
  }
}
