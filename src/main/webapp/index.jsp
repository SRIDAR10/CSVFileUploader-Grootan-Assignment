<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload CSV</title>
</head>
<body>
<div align="center">
<h1><%= "CSV File Uploader" %>
</h1>
<br/>

<form action="post-servlet" method="post" enctype="multipart/form-data">
    File:<input type="file" accept=".csv" name="file" >
    <input type="submit" value="upload">
</form>
</div>
</body>
</html>