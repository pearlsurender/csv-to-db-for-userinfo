<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
   <head>
      <title>CSV to DB</title>
   </head>
   <body>
      <h2>File Upload For Users:</h2>
      
      <h3>${message}</h3>
      
      Select a file to upload: <br />
      <form name="csvForm" action = "/upload" method = "post" enctype = "multipart/form-data">
      
         <input type = "file" name = "file" id= "file" size = "50" />
         <br />
         <input type = "BUTTON" value = "Upload File" ONCLICK="onSubmit()"/>
         <br />
         <c:if test = "${download!=null}">
      		Click on this <strong>  <INPUT TYPE="BUTTON" id="Button" VALUE="Download" ONCLICK="buttonAction()"> <!-- <a href="/download" onclick="window.location.reload()">link</a> --></strong> to download the failure records.
     	 </c:if>
      </form>
   </body>
   <script type="text/javascript">
   function buttonAction()
   {
	   document.getElementById("Button").disabled = true;
	   csvForm.action = "/download";
	   csvForm.submit();
   } 
   
   function onSubmit()
   {
	   if(document.getElementById("file").value === "") {
		   alert("please select the file")
		   return false;
		}
	   csvForm.submit();
   }
   
   </script>
</html>