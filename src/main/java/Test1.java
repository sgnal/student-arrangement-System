import javax.swing.*;

import javax.swing.*;
import java.util.Vector;
import java.sql.*;
import java.awt.*;
public class Test1 extends JFrame {
    Vector rowData, columnNames;
    JTable jt = null;
    JScrollPane jsp = null;


    public static void main(String[] args) {

        Test1 test1 = new Test1();

    }
    public Test1(){
        columnNames = new Vector();
        columnNames.add("学号");
        columnNames.add("名字");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("籍贯");
        columnNames.add("系别");

        rowData=new Vector();
        Vector hang= new Vector();
        hang.add("spoo1");
        hang.add("孙悟空");
        hang.add("男");
        hang.add("500");
        hang.add("花果山");
        hang.add("少林派");

        rowData.add(hang);

        jt = new JTable(rowData,columnNames);

        jsp =new JScrollPane(jt);

        this.add(jsp);

        this.setSize(400,300);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

}
