package Db;

import Encrypt.EncryptPassword;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateTable {
    private Connection conn = null;
    private Statement stmt = null;

    public void createTable(String tableName,String Columns[]){
        initializeDB();
        String sql;
        StringBuilder sqlB = new StringBuilder("CREATE TABLE IF NOT EXISTS "+tableName);
        sqlB.append("(");
        for(int i=0;i<Columns.length-1;i++){
            sqlB.append(Columns[i]+" varchar,");
        }
        sqlB.append(Columns[Columns.length-1]+" varchar);");
        sql=sqlB.toString();
        try {
            stmt.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeDB() {
        final String JDBC_DRIVER = "org.postgresql.Driver";
        final String DB_URL = "jdbc:postgresql://localhost:5432/CSVData";
        final String USER = "";
        final String PASS = "";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println("Successfully connected to database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void populateTable(String tableName,String[] rowValues){
        String sql;
        StringBuilder sqlB = new StringBuilder("INSERT INTO "+tableName);
        sqlB.append(" VALUES ( ");
        for(int i=1;i<rowValues.length;i++) {
            StringBuilder sqlB2 = new StringBuilder();
            String columnValue[] = rowValues[i].split(",");
            for(int j=0;j<columnValue.length-1;j++){
                sqlB2.append("'"+columnValue[j]+"'"+", ");
            }
            sqlB2.append("'"+columnValue[columnValue.length-1]+"'"+" );");
            sql=sqlB.toString()+sqlB2.toString();
            try {
                stmt.execute(sql);
                System.out.println("populate success");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    public void encrypt(String tableName){
        String sql;
        StringBuilder sqlB = new StringBuilder("Select password from "+tableName+" ;");
        sql=sqlB.toString();
        ArrayList<String> passwords=new ArrayList<>();
        try {
            ResultSet pass =stmt.executeQuery(sql);
            while (pass.next()){
                passwords.add(pass.getString("password"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for(String pass:passwords){
            EncryptPassword encryptPassword = new EncryptPassword();
            String encrypted = encryptPassword.sha256(pass);
            String sql2 = "UPDATE "+tableName+" SET "+"password ="+"'"+encrypted+"'"+" WHERE "+"password="+"'"+pass+"'"+";";
            try {
                stmt.execute(sql2);
                System.out.println("populate success");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

