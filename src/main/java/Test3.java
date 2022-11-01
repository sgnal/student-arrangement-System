import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class Test3 extends JFrame implements ActionListener{
    static String driver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/spdb1?useSSL=false";
    static String username = "root";
    static String password = "123456";
    String sql = "select * from stu";
    JPanel jp1,jp2;
    JLabel jl1;
    JButton jb1,jb2,jb3,jb4;
    JTable jt;
    JScrollPane jsp;
    JTextField jtf;










    public static void main(String[] args) {
        Test3 test3=new Test3();


    }
    public Test3(){
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
        jb4 = new JButton("删除");

//        把各个按钮加入到就jp2
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);

//       中间
        stumodel sm=new stumodel();
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
        if (arg0.getSource()==jb1){
            System.out.println("yonghuxiwangchaxun");
//            因为把对表的数据封装到stumodel中，我们就可以比较简单的完成查询任务
            String name =this.jtf.getText().trim();

            String sql = "select * from stu where stuName='"+name+"'";

            stumodel sm = new stumodel(sql);

                   jt.setModel(sm);



        }
//        当用户点击添加
        else if (arg0.getSource()==jb2){
            StuAddDialog sa = new StuAddDialog(this,"添加学生",true);

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
//            stumodel sm =new stumodel(sql);
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
