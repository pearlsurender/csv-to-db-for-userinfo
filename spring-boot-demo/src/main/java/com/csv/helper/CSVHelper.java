package com.csv.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.csv.model.User;

public class CSVHelper {
	
  public static String TYPE = "csv";
  public static List<User> failedUsersList = new ArrayList<User>();
  public static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
  public static Validator validator = validatorFactory.usingContext().getValidator();
  static Set<ConstraintViolation<User>> constrains = null;

  public static boolean hasCSVFormat(MultipartFile file) {
    if (!TYPE.equals(FilenameUtils.getExtension(file.getOriginalFilename()))) {
      return false;
    }

    return true;
  }

public static List<User> csvToTutorials(MultipartFile file) {
	  
	  List<User> users = new ArrayList<User>();
	  BufferedReader fileReader = null;
	  try 
	  {
		fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
		users = processInputFile(fileReader);
	  }
	  catch (IOException e) 
	  {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	  }
	  finally
	  {
		  fileReader = null;
	  }
      return users;
  }

  public static ByteArrayInputStream tutorialsToCSV(List<User> users) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (User user : users) {
        List<String> data = Arrays.asList(
        		user.getUserName(),
        		user.getFirstName(),
        		user.getLastName(),
        		user.getPassword(),
        		user.getEmail(),
        		user.getAddress());

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

  private static List<User> processInputFile(BufferedReader br) {

	    List<User> userList = new ArrayList<User>();
	    try
	    {
	    	userList = br.lines().skip(1).map(mapToItem).filter(x -> x != null).collect(Collectors.toList());
	    	br.close();
	    } catch (Exception e) 
	    {
	    	throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	    return userList ;
	}
  
  private static Function<String, User> mapToItem = (line) -> {
	
	try
	{
		String[] p = line.split(",");// a CSV has comma separated lines
		  User user = new User(
	      		p[0],//Username
	      		p[1],//FirstName
	      		p[2],//Lastname
	      		p[3],//Email
	      		p[4],//Password
	      		p[5] //Address
	          );
		  constrains = validator.validate(user);
	      if(constrains!=null && constrains.size()>0)
	      {
	    	  failedUsersList.add(user);
	    	  return null;
	      }
	      return user;
	}
	catch (Exception e) 
    {
    	throw new RuntimeException("fail to populate the pojo from CSV file: " + e.getMessage());
    }
	finally
	{
		constrains = null;
	}
	};
  
}
