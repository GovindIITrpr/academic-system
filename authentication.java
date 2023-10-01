// import java.sql.Connection;
// import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class authentication {
    static String approve ="1";
    static String back="-1";
    static Scanner input = new Scanner(System.in);
    public static String[] login(int usertype) throws SQLException
    {
        connect c = new connect();
        Statement db = c.pgstart();
        ResultSet res=null;


        clearCMD clear = new clearCMD();

        clear.clearConsole();

        String username = "",passWord="";

        String query="";
        String[] s = new String[2];

        while(true)
        {
            String p = "Logging As a ";
            if(usertype==1)
            {
                p = p + "Student";
            }
            else if(usertype==2)
            {
                p = p + "Faculty";
            }
            else if(usertype==3)
            {
                p = p + "Admin";
            }
            System.out.println(p);
            System.out.println("Press -1 for goes back!");
            System.out.println("Please Enter Your Username");
            System.out.print("Username: ");
            username = input.nextLine();

            if(username.equals("-1"))
            {
                s[0] = back;

                break;
            }
            if(usertype==1)
            {
                System.out.println(p);
                query = "select * from student_user where username = '" + username + "' ;";
            }
            else if(usertype==2)
            {
                System.out.println(p);
                query = "select * from faculty_user where username = '" + username + "' ;";
            }
            else if(usertype ==3)
            {
                System.out.println(p);
                query = "select * from academic_user where username = '" + username + "' ;";
            }

            // System.out.println(query);
            res = db.executeQuery(query);
            res.next();
            // System.out.println(res.getRow()+"-----");
            if(res.getRow()==1)
            {
                clear.clearConsole();
                System.out.println("Please Enter Your Password");
                System.out.println("Username: "+username);

                while(true)
                {
                    System.out.print("Password: ");
                    passWord = input.nextLine();
                    if(passWord.equals("-1"))
                    {
                        s[0] = back;
                        clear.clearConsole();
                        return s;
                    }
                    if(passWord.equals(res.getString("pass")))
                    {
                        s[0] = approve;
                        s[1] = username;
                        clear.clearConsole();

                        return s;
                    }
                    else{
                        System.out.println("Wrong Password");
                    }

                }
            }
            else
            {
                clear.clearConsole();
                // System.out.println(res.getRow()+"-----"+ username.length()+"    ="+query);
                System.out.println("There is no user with username "+ username);
                continue;
            }
        }
        c.pgend();
        clear.clearConsole();
        return s;

    }
}
