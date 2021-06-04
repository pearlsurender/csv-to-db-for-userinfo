<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
   <head>
      <title>File Uploading Form</title>
   </head>
   <body>
      <h3>File Upload:</h3>
      Select a file to upload: <br />
      <form action = "next" method = "post"
         enctype = "multipart/form-data">
         <input type = "file" name = "file" size = "50" />
         <br />
         <input type = "submit" value = "Upload File" />
      </form>
      
      Click on this <strong><a href="/">link</a></strong> to download the failure records.
      
   </body>
</html>