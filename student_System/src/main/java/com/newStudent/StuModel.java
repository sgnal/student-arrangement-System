package com.newStudent;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;

public class StuModel extends AbstractTableModel  {
//    把jdbc中的封装进入到此类中

    Vector rowData, columnNames;
//    定义数据据中连接jdbc中的相关类
    Connection conn=null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
//  增删改操作
    public Boolean editStu(String sql,String[] paras){

//        创建一个SQLHelper
        SqlHelper sqlHelper = new SqlHelper();
        return sqlHelper.editDateBase(sql,paras);
//        Boolean b = true;
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spdb1?useSSL=false","root","123456");
//
//            preparedStatement = conn.prepareStatement(sql);
//
//            for (int i = 0; i < paras.length; i++) {
//                preparedStatement.setString(i+1,paras[i]);
//
//
//            }
//
//
//            if(preparedStatement.executeUpdate()!=1){
//                b=false;
//            }
//
//
//
//
//        } catch (Exception e) {
//            b=false;
//            e.printStackTrace();
//        }finally {
//            if (preparedStatement!=null){
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            if (conn!=null){
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return b;
    }


    public void queryStu(String sql,String[] paras){
        SqlHelper sqlHelper = null;

        columnNames = new Vector();
        columnNames.add("学号");
        columnNames.add("名字");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("籍贯");
        columnNames.add("系别");

        rowData = new Vector();

        try{
           sqlHelper = new SqlHelper();
           ResultSet rs = sqlHelper.queryExecture(sql,paras);
            while(rs.next()){
                Vector hang = new Vector();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getInt(4));
                hang.add(rs.getString(5));
                hang.add(rs.getString(6));
                rowData.add(hang);


            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlHelper.close();
        }
    }



//    做一个构造函数


//    得到共有多少行
    @Override
    public int getRowCount() {

        return this.rowData.size();
    }
//得到共有多少列
    @Override
    public int getColumnCount() {

        return this.columnNames.size();
    }
//得到某行某列的数据
    @Override
    public Object getValueAt(int row, int column) {
        return ((Vector)this.rowData.get(row)).get(column);
    }

    @Override
    public String getColumnName(int arg0) {
        return (String)this.columnNames.get(arg0);
    }
}
