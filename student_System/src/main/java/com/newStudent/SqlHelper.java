package com.newStudent;
//这是一个对数据库操作的类

import java.sql.*;

public class SqlHelper {
    Connection conn=null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    static String driver ="com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/spdb1?useSSL=false";
    static String username = "root";
    static String password= "123456";

//    进行数据库的增删改操作
    public boolean editDateBase(String sql,String[] paras){

        Boolean b = true;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spdb1?useSSL=false","root","123456");

            preparedStatement = conn.prepareStatement(sql);

            for (int i = 0; i < paras.length; i++) {
                preparedStatement.setString(i+1,paras[i]);


            }


            if(preparedStatement.executeUpdate()!=1){
                b=false;
            }




        } catch (Exception e) {
            b=false;
            e.printStackTrace();
        }finally {
           this.close();
        }
        return b;

    }
    public ResultSet queryExecture(){
        try {
            String sql= "select * from stu";
            Class.forName(driver);
            conn=DriverManager.getConnection(url,username,password);
            preparedStatement = conn.prepareStatement(sql);

            rs=preparedStatement.executeQuery();




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            关闭资源
        }
        return rs;

    }

//    查询操作
    public ResultSet queryExecture(String sql,String [] prarse){

        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(url,username,password);
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < prarse.length; i++) {
                preparedStatement.setString(i+1,prarse[i]);
            }
            rs=preparedStatement.executeQuery();




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            关闭资源
        }
        return rs;
    }

//    数据库关闭资源
    public void close(){
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
