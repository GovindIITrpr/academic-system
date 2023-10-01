import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Statement;

public class MakeItNew {
    static Connection connect = null;
    static Statement db = null;
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "12345");
            if(connect==null)
            {
                System.out.println("Connection Failed!");
            }
            connect.setAutoCommit(true);
            db = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
        }
        System.out.println("Connection Done!");
        String query = "DROP DATABASE IF EXISTS mini;";
        try {
            db.execute(query);
        } catch (Exception e) {
            System.out.println("Database not drop");
            System.out.println(e);
            
            System.exit(69);
        }
        query = "Create database mini ;";
        try {
            db.execute(query);
            System.out.println("Database Create");
        } catch (Exception e) {
            System.out.println("Database not Create");
            System.out.println(e);
            
            System.exit(70);
        }
        try {
            db.close();
            connect.close();
            System.out.println("Connection Close");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            System.exit(99);
        }
        

        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mini","postgres", "12345");
            if(connect==null)
            {
                System.out.println("Connection Failed!");
            }
            connect.setAutoCommit(true);
            db = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
        }
        System.out.println("Database Connected to mini");

        StringBuilder sb = new StringBuilder();
        String line;
        String file="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("database.sql"));
            
            while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
            }
            reader.close();
            file = sb.toString();
        } catch (Exception e) {
            System.out.println("Problem in reading file");
            System.out.println(e);
            System.exit(72);
        }
        
        

        // Execute the SQL file
        try {
            db.execute(file);
        } catch (Exception e) {
            System.out.println("File not Execute");
            System.out.println(e);
            System.exit(73);
        }
        
        System.out.println("Database Reset");
        try {
            db.close();
            connect.close();
            System.out.println("Connection Close");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            System.exit(99);
        }


    }
}
