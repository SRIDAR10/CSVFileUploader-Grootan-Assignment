package com.example.G2;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Db.CreateTable;
import org.apache.commons.io.IOUtils;

@WebServlet("/post-servlet")
@MultipartConfig
public class PostServlet extends HttpServlet {
    private Boolean isEncription;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("file");//form file PArt
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();//filenam test.csv
        InputStream fileContent = filePart.getInputStream();
        String theString = IOUtils.toString(fileContent, StandardCharsets.UTF_8);
        String tableName=fileName.substring(0,fileName.length()-4);
        String header[]=theString.split("\\n")[0].split(",");
        for (String s:header){
            if (s.equals("password")) {
                isEncription = true;
                break;
            }
        }
        CreateTable createDB = new CreateTable();
        createDB.createTable(tableName,header);
        createDB.populateTable(tableName,theString.split("\\n"));
        if(isEncription) {
            createDB.encrypt(tableName);
        }
        response.sendRedirect("uploaded.jsp");
    }
    public void destroy() {
    }
}