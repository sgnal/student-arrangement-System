package com.newStudent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

;

public class StuUpdDialog extends JDialog implements ActionListener {
    //    定义我需要的控件
    JLabel jl1, jl2, jl3, jl4, jl5, jl6;
    JButton jb1, jb2;
    JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf6;
    JPanel jp1, jp2, jp3;

    //    owner代表它的父窗口，title就是窗口名，modal指定是模态还是非模态窗口
    public StuUpdDialog(Frame owner, String title, boolean modal, StuModel sm, int rowHums) {
        super(owner, title, modal);
        jl1 = new JLabel("学号");
        jl2 = new JLabel("姓名");
        jl3 = new JLabel("性别");
        jl4 = new JLabel("年龄");
        jl5 = new JLabel("籍贯");
        jl6 = new JLabel("派别");


//        初始化数据

        jtf1 = new JTextField();
        jtf1.setText((String) sm.getValueAt(rowHums, 0));
//        让jtf1,不能修改
        jtf1.setEditable(false);

        jtf2 = new JTextField();
        jtf2.setText((String) sm.getValueAt(rowHums, 1));
        jtf3 = new JTextField();
        jtf3.setText((String) sm.getValueAt(rowHums, 2));


        jtf4 = new JTextField();
        jtf4.setText(sm.getValueAt(rowHums, 3).toString());

        jtf5 = new JTextField();
        jtf5.setText((String) sm.getValueAt(rowHums, 4));

        jtf6 = new JTextField();
        jtf6.setText((String) sm.getValueAt(rowHums, 5));


        jb1 = new JButton("修改");


        jb2 = new JButton("取消");

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

//        设置布局
        jp1.setLayout(new GridLayout(6, 1));
        jp2.setLayout(new GridLayout(6, 1));

//        添加组件
        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jl5);
        jp1.add(jl6);

        jp2.add(jtf1);
        jp2.add(jtf2);
        jp2.add(jtf3);
        jp2.add(jtf4);
        jp2.add(jtf5);
        jp2.add(jtf6);

        jp3.add(jb1);
        jp3.add(jb2);

        this.add(jp1, BorderLayout.WEST);
        this.add(jp2, BorderLayout.CENTER);
        this.add(jp3, BorderLayout.SOUTH);

        jb1.addActionListener(this);
        jb2.addActionListener(this);

//        展现
        this.setSize(300, 200);
        this.setLocation(200, 300);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
//            连接数据库
            String sql = "update stu set stuName=?,stuSex=?,stuAge=?,stuJg=?,stuDept=? where stuId=?";
            String[] paras = {jtf2.getText().trim(), jtf3.getText().trim(), jtf4.getText().trim(), jtf5.getText().trim(), jtf6.getText().trim(), jtf1.getText().trim()};
            StuModel sm = new StuModel();
            sm.editStu(sql, paras);

            this.dispose();


        } else if (e.getSource() == jb2) {
            this.dispose();
        }
    }


}
