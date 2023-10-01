
import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;



public class mini {
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args)  throws IOException , SQLException{


        clearCMD clear = new clearCMD();
        clear.clearConsole();
        System.out.println("Welcome! to My SE mini project");
        while(true)
        {
            // clear.clearConsole();
            System.out.println("Log in as!");
            System.out.println("1. Student");
            System.out.println("2. Faculty");
            System.out.println("3. Academic Office");
            System.out.println("-1. Exit");
            String in = input.nextLine();
            int x=0;
            try {
                x =Integer.parseInt(in);
            } catch (Exception e) {
                System.out.println("Invalid Input");

                continue;
            }

            if(x==-1)
            {
                break;
            }
            else if(x==1)
            {
                stu s = new stu();
                s.start();

                clear.clearConsole();
            }
            else if(x==2)
            {
                faculty f = new faculty();
                f.start();

                clear.clearConsole();
            }
            else if(x==3)
            {
                acad a = new acad();
                a.start();

                clear.clearConsole();
            }
            else{
                System.out.println("Invalid Input");
            }


        }

        return;

    }
}