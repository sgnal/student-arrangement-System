import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class Test2 extends JFrame{
    static String driver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/spdb1?useSSL=false";
    static String username = "root";
    static String password = "123456";
    static String sql = "select * from stu";

    //    从数据库中取出数据
    Vector rowData, columnNames;
    JTable jt = null;
    JScrollPane jsp = null;
    Connection conn=null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public static void main(String[] args) {
        Test2 test2 = new Test2();
    }
    public Test2() {
        columnNames = new Vector();
        columnNames.add("学号");
        columnNames.add("名字");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("籍贯");
        columnNames.add("系别");

        rowData = new Vector();

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);

            preparedStatement = conn.prepareStatement(sql);

            rs = preparedStatement.executeQuery();
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
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        jt = new JTable(rowData,columnNames);

        jsp =new JScrollPane(jt);

        this.add(jsp);

        this.setSize(400,300);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
