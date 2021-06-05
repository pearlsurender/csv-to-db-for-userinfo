package com.csv.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csv.helper.CSVHelper;
import com.csv.service.CSVService;

@Controller
public class CSVController {

	@Autowired
	CSVService fileService;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) 
	{
		return "index";
	}
	
	@RequestMapping("/upload")
	public String upload(Map<String, Object> model, @RequestParam("file") MultipartFile file) 
	{
		String message = "";
		try
		{
			if(!CSVHelper.failedUsersList.isEmpty())
				CSVHelper.failedUsersList.clear();
			
			if(!CSVHelper.users.isEmpty())
				CSVHelper.users.clear();
			
			if (CSVHelper.hasCSVFormat(file)) 
		    {
		        fileService.save(file);
		        message = "Uploaded the file successfully: " + file.getOriginalFilename();
		    }
		    else
		    {
		    	message = "Please upload a csv file!";
		    }
		}
		catch (Exception e) 
		{
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	    }
	    
		if(CSVHelper.users.isEmpty())
		{
			model.put("download", "download");
			if(message.contains("successfully"))
				message = "Uploaded failed and "+CSVHelper.failedUsersList.size()+" users were failed to upload, please download the failed users through the link : " + file.getOriginalFilename();
		
		}
		else if(!CSVHelper.failedUsersList.isEmpty())
		{
			model.put("download", "download");
			if(message.contains("successfully"))
				message = "Uploaded the file partially and "+CSVHelper.failedUsersList.size()+" users were failed to upload, please download the failed users through the link : " + file.getOriginalFilename();
		}
		model.put("message", message);
		return "index";
	}
	
	@RequestMapping("/download")
	public ResponseEntity<InputStreamResource> download(Map<String, Object> model) {
		
		String filename = "failedUsers.csv";
	    InputStreamResource file = new InputStreamResource(fileService.load());
	    model.clear();
	    
	    if(!CSVHelper.failedUsersList.isEmpty())
			CSVHelper.failedUsersList.clear();
		
		if(!CSVHelper.users.isEmpty())
			CSVHelper.users.clear();
		
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/csv"))
	        .body(file);
	}

}