package com.newStudent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StuManage extends JFrame implements ActionListener{


    JPanel jp1,jp2;
    JLabel jl1;
    JButton jb1,jb2,jb3,jb4;
    JTable jt;
    JScrollPane jsp;
    JTextField jtf;

    StuModel sm;




    public static void main(String[] args) {

        StuManage sm = new StuManage();

    }
    public StuManage(){
        jp1=new JPanel();
        jtf=new JTextField(10);
        jb1=new JButton("查询");

        jb1.addActionListener(this);

        jl1 = new JLabel("请输入名字");
        jp1.add(jl1);
        jp1.add(jtf);
        jp1.add(jb1);


        jp2=new JPanel();

        jb2=new JButton("添加");
        jb2.addActionListener(this);
        jb3=new JButton("修改");
        jb3.addActionListener(this);
        jb4 = new JButton("删除");
        jb4.addActionListener(this);

//        把各个按钮加入到就jp2
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);

//       中间
        sm=new StuModel();
        String[] paras ={"1"};
        sm.queryStu("select * from stu where 1=?",paras);
        jt = new JTable(sm);

        jsp =new JScrollPane(jt);

        this.add(jsp);

        this.add(jp1,"North");
        this.add(jp2,"South");

        this.setSize(400,300);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
//        表示查询
        if (arg0.getSource()==jb1){
            System.out.println("yonghuxiwangchaxun");
//            因为把对表的数据封装到stumodel中，我们就可以比较简单的完成查询任务
            String name =this.jtf.getText().trim();
            System.out.println(name.length());
            System.out.println("-----------------------");




            if (name.length()==0){
                String sql1 = "select * from stu where 1=?";
                String[] paras2 = {"1"};
                sm=new StuModel();
                sm.queryStu(sql1,paras2);
                jt.setModel(sm);

            }else {
                String sql = "select * from stu where stuName=?";
                String[] paras = {name};

                sm = new StuModel();
                sm.queryStu(sql, paras);
                jt.setModel(sm);


            }
        }
//        当用户点击添加
//        当用户点击添加 ，进行添加学生书籍，沟槽类StuAddDialog,进行数据库中的
//        添加操作，添加完成之后再空控制台进行显示，也就是更新数据在那个控件中
        else if (arg0.getSource()==jb2){
            StuAddDialog sa = new StuAddDialog(this,"添加学生",true);


//            更新数据，添加控件
            sm = new StuModel();
            String[] para = {"1"};
            sm.queryStu("select * from stu where 1=?",para);
            jt.setModel(sm);

//            如果用户点击删除，我们的获得要删除的数据喊得下标
//            得到下标在进行删除操作

        }else if (arg0.getSource()==jb4){

//            说明用户希望删除记录
//            1，看到该学生的id
//            getSelectedRou或返回用户点中的行
//            如果该用户一行都没选择，就返回-1,返回多少行
            int rowHum = this.jt.getSelectedRow();
            if (rowHum==-1){
//
                JOptionPane.showMessageDialog(this,"请选择一行");
                return;
            }
//            得到学生的编号
            String stuId = (String)sm.getValueAt(rowHum,0);
//            在控制台输出学生的编号

//            System.out.println("id="+stuId);
//            创建一个SQL语句
            String sql = "delete from stu where stuId=?";
            String[] paras = {stuId};
            StuModel so = new StuModel();
            so.editStu(sql,paras);


//                更新数据模型
//                将构建的数据模型进行更新
                sm=new StuModel();
                String[] para = {"1"};
                sm.queryStu("select * from stu where 1=?",para);
                jt.setModel(sm);

//              修改操作
        }else if (arg0.getSource()==jb3){
            System.out.println("用户希望修改操作");
//用户希望修改操作
            int rowHums = this.jt.getSelectedRow();
            if (rowHums==-1){
                JOptionPane.showMessageDialog(this,"请选择一行");
                return;
            }
            new StuUpdDialog(this,"修改学生",true,sm,rowHums);
            sm=new StuModel();
            String[] para= {"1"};
            sm.queryStu("select * from stu where 1=?", para);
            jt.setModel(sm);
        }
    }

//    @Override
//    public void actionPerformed(ActionEvent arg0) {
//        判断是那个按钮先点击
//        if(arg0.getSource()==jb1){

//            System.out.println("用户希望查询");
//            String name = this.jtf.getText().trim();

//            写一个sql语句
//            String sql = "select * from stu where stuName='"+name+"'";
//
//            StuModel sm =new StuModel(sql);
//
//            jt.setModel(sm);
//
//
//
////            查询数据库，更新Jtable
////            封装数据库，中间的代码
//
//        }
//    }
}
