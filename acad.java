import com.sun.source.tree.WhileLoopTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class acad {
    private static String username = "";
    private static Scanner input = new Scanner(System.in);
    private static Statement db = null;
    private static Connection con = null;
    private static final clearCMD clear = new clearCMD();

    private static String int_input(){
        while(true)
        {
            String in  = input.nextLine();
            if(in.equals("-1"))
            {
                return "Back";
            }
            try {
                Integer.parseInt(in);

                return in;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }
    private static void back_input(){
        System.out.println("-1. Back");
        while(true)
        {
            String in = input.nextLine();
            if(in.equals("-1"))
            {
                return ;
            }
            else
            {
                System.out.println("Invalid Input!");
            }
        }
    }

    private static void Admission()throws SQLException {
        while(true)
        {
            String name;
            System.out.println("At any time you want to exit process press -1");
            System.out.println("Enter name of the Candidate");
            name = input.nextLine();
            if(name.equals("-1")){
                return ;
            }
            String entry_no = "";
            String query = "";
            ResultSet res;

            while(true)
            {
                System.out.println("Enter Entry Number of Candidate");
                entry_no = input.nextLine();
                if(entry_no.equals("-1"))
                {
                    return;
                }
                query = "select * from student where entry_no = '"+ entry_no + "' ;";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()==0)
                {
                    break;
                }
                else
                {
                    System.out.println("This Entry number is already assigned to other Student");
                }
            }

            String batch = "";
            String program = "";
            while(true)
            {
                System.out.println("Enter Batch");
                batch = input.nextLine();
                if(batch.equals("-1"))
                {
                    return;
                }
                System.out.println("Enter Program");
                program = input.nextLine();
                if(program.equals("-1"))
                {
                    return;
                }
                query = "select * from batch where year = '"+ batch + "' and program = '"+program+"';";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()==0)
                {
                    System.out.println("This combination does not exit in database");
                }
                else if(res.getString("batch_status").equals("OPEN"))
                {
                    break;
                }
                else{
                    System.out.println("This batch and program closed for admission");
                }
            }
            String dep = "";
            while(true)
            {
                System.out.println("Enter Department of the Candidate");
                dep = input.nextLine();
                if(dep.equals("-1"))
                {
                    return;
                }
                query = "select * from department where dep = '" + dep + "' ;";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()==0)
                {
                    System.out.println("No Such department in Database");
                }
                else
                {
                    break;
                }
            }

            String phone = "",email = "";
            System.out.println("Enter Phone Number");
            phone = input.nextLine();
            if(phone.equals("-1"))
            {
                return;
            }
            System.out.println("Enter Email");
            email = input.nextLine();
            if(email.equals("-1"))
            {
                return;
            }
            query = "INSERT INTO student(entry_no,student_name,department,batch,program,phone,email) VALUES('"+entry_no+"','"+name+"','"+dep+"','"+batch+"','"+program+"','"+phone+"','"+email+"') ;";
            int x = db.executeUpdate(query);


            // ------------------------  CREATE TABLE
            query = "CREATE TABLE IF NOT EXISTS course_"+entry_no+"( "
                    +"course_id TEXT NOT NULL,"
                    +"course_name TEXT NOT NULL,"
                    + "course_type TEXT NOT NULL, "
                    +"instructor_name TEXT NOT NULL,"
                    +"instructor_id TEXT NOT NULL,"
                    +"L_T_P TEXT NOT NULL,"
                    +"credit TEXT NOT NULL,"
                    +"current_session TEXT NOT NULL,"
                    +"grade TEXT,"
                    +"current_status TEXT NOT NULL,"
                    +"PRIMARY KEY(course_id,current_session),"
                    +"FOREIGN KEY(course_id) REFERENCES course_catalog(course_id),"
                    +"FOREIGN KEY(instructor_id) REFERENCES faculty(faculty_id)"
                    +");";
            Boolean y = db.execute(query);


            // INSERT INTO USER TABLE
            query = "INSERT INTO student_user(username,pass) values('"+entry_no+"','"+entry_no+"')";

            int z = db.executeUpdate(query);
            if(x==1&&y==false&&z==1)
            {
                con.commit();
                System.out.println("Successfully Added");
            }
            else
            {
                System.out.println("Insertion fail Contact Administration x = "+x+ " y ="+y + " z = "+z);
            }




            System.out.println("1. Add Another Student");
            System.out.println("-1. Back");
            String in = input.nextLine();
            while(true)
            {
                if(in.equals("1"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return ;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }

    }

    private static void Add_faculty()throws SQLException {
        while(true)
        {
            String name = "" ;
            System.out.println("At any time you want to exit process press -1");
            System.out.println("Enter name of the Candidate");
            name = input.nextLine();
            if(name.equals("-1")){
                return ;
            }
            String faculty_id = "";
            String query = "";
            ResultSet res;

            while(true)
            {
                System.out.println("Enter Faculty ID of Candidate");
                faculty_id = input.nextLine();
                if(faculty_id.equals("-1"))
                {
                    return;
                }
                query = "select * from faculty where faculty_id = '"+ faculty_id + "' ;";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()==0)
                {
                    break;
                }
                else
                {
                    System.out.println("This ID is already assigned to other Faculty");
                }
            }


            String dep = "";
            while(true)
            {
                System.out.println("Enter Department of the Candidate");
                dep = input.nextLine();
                if(dep.equals("-1"))
                {
                    return;
                }
                query = "select * from department where dep = '" + dep + "' ;";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()==0)
                {
                    System.out.println("No Such department in Database");
                }
                else
                {
                    break;
                }
            }

            String phone = "",email = "";
            System.out.println("Enter Phone Number");

            phone = input.nextLine();
            if(phone.equals("-1"))
            {
                return;
            }
            System.out.println("Enter Email");
            email = input.nextLine();
            if(email.equals("-1"))
            {
                return;
            }


            //----------------------------------------------
            query = "INSERT INTO faculty(faculty_id,faculty_name,department,phone,email) VALUES('"+faculty_id+"','"+name+"','"+dep+"','"+phone+"','"+email+"') ;";

            int x = db.executeUpdate(query);

            String q1 = "INSERT INTO faculty_user(username,pass) VALUES('"+faculty_id+"','"+faculty_id+"')";
            int y = db.executeUpdate(q1);
            if(x==1&&y==1)
            {
                con.commit();
                System.out.println("Successfully Added");
            }
            else
            {
                System.out.println("Insertion fail Contact Administration");
            }

            // ------------------------  CREATE TABLE

            // INSERT INTO USER TABLE


            System.out.println("0. Add Another Faculty");
            System.out.println("-1. Back");
            String in = input.nextLine();
            while(true)
            {
                if(in.equals("0"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return ;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }
    }


    private static void new_batch() throws SQLException
    {
        System.out.println("For going back press '-1' any time");
        while(true)
        {
            System.out.println("Enter Year");
            String year = input.nextLine();
            if(year.equals("-1"))
            {
                return;
            }
            System.out.println("Enter Program");
            String program = input.nextLine();
            if(program.equals("-1"))
            {
                return;
            }
            String query = "select * from batch where year = '" + year + "' and program = '" + program + "' ;";
            ResultSet res = db.executeQuery(query);
            res.next();
            if(res.getRow()!=0)
            {
                System.out.println("This batch is already declare");
                System.out.println("0. Add Another Batch");
                System.out.println("-1. Back");
                String in = "";
                while(true)
                {
                    in = input.nextLine();
                    if(in.equals("0"))
                    {
                        clear.clearConsole();
                        break;
                    }
                    else if(in.equals("-1"))
                    {
                        return ;
                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                }
                continue;
            }
            else
            {
                query = "select * from program where pg ='" + program + "' ;";

                res = db.executeQuery(query);

                res.next();
                if(res.getRow()==0)
                {
                    System.out.println("No such Program - start process again");
                    continue;

                }
            }

            // COURSE CURRICULAM
            File file;
            FileReader fr;
            BufferedReader br;

            while(true)
            {
                System.out.println("Enter Address of the file for Course Curriculam");
                String in  = input.nextLine();
                StringBuilder address = new StringBuilder(in);
                if(in.equals("-1"))
                {
                    return;
                }
                for(int i=0;i<in.length();i++)
                {
                    if(in.charAt(i)=='\\') //  this work has a single '\';
                    {
                        address.setCharAt(i, '/');
                    }
                }
                try
                {
                    file = new File(address.toString());
                    fr = new FileReader(file);
                    br = new BufferedReader(fr);
                    System.out.println("File Opened");
                    String line = "";
                    try
                    {
                        int j=0;
                        line = br.readLine();
                        j++;
                        boolean flag = true;
                        while((line = br.readLine()) != null)
                        {
                            j++;
                            String [] s =line.split(",");
                            if(s.length==3)
                            {
                                boolean flag1=false,flag2=false,flag3=false;
                                String credit="",l_t_p="",prerequisites="",course_name="";
                                // s[0] = course_id s[1] = department s[2] = type

                                query = "select * from course_catalog where course_id ='"+s[0] + "';";
                                res  = db.executeQuery(query);
                                res.next();
                                if(res.getRow()!=0)
                                {
                                    flag1=true;
                                    prerequisites = res.getString("prerequisites");
                                    credit = res.getString("credit");
                                    l_t_p = res.getString("L_T_P");
                                    course_name = res.getString("course_name");
                                }
                                query = "select * from department where dep = '" + s[1]+ "';";
                                res  = db.executeQuery(query);
                                res.next();
                                if(res.getRow()!=0)
                                {
                                    flag2=true;
                                }
                                query = "select * from course_type where c_type = '" + s[2]+ "';";
                                res  = db.executeQuery(query);
                                res.next();
                                if(res.getRow()!=0)
                                {
                                    flag3=true;
                                }
                                if(flag1&&flag2&&flag3)
                                {
                                    try {
                                        query = "INSERT INTO course_curriculam(batch,program,department,course_id,course_name,course_type,credit,L_T_P,prerequisites) VALUES('"+year+"','"+program+"','"+s[1]+"','"+s[0]+"','"+course_name+"','"+s[2]+"','"+credit+"','"+l_t_p+"','"+prerequisites+"');";
                                        if(db.executeUpdate(query)!=1)
                                        {
                                            flag=false;
                                            con.rollback();
                                            System.out.println("Rolled Back 1 --- Not inserted line: "+ j);
                                            break;
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        System.out.println("May be duplicate line : " + j);
                                        flag = false;
                                        con.rollback();
                                        break;
                                    }

                                }
                                else
                                {
                                    flag=false;
                                    con.rollback();
                                    System.out.println("Rolled Back 2 ------ There is a problem in file in line: "+ j +" May be course not present in Course Ctalog or No Department present as listed or No Course_Type present as listed");
                                    break;
                                }

                            }
                            else
                            {
                                flag=false;
                                con.rollback();
                                System.out.println("Rolled Back 3--- There is a problem in file in line: "+ j + " Either More data  or less present, not exact 3" );
                                break;
                            }
                        }

                        if(flag==false)
                        {
                            System.out.println("Problem in File");
                            System.out.println("-1. Back");
                            while(true)
                            {
                                in = input.nextLine();
                                if(in.equals("-1"))
                                {
                                    return;
                                }
                                else
                                {
                                    System.out.println("Invalid Input");
                                }
                            }
                        }
                        br.close();
                    } catch (Exception e) {
                        System.out.println(e);

                        System.out.println("There a problem in reading file, Contacrt your administration");

                        System.out.println("-1. Back");
                        while(true)
                        {
                            in = input.nextLine();
                            if(in.equals("-1"))
                            {
                                return;
                            }
                            else
                            {
                                System.out.println("Invalid Input");
                            }
                        }
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Invalid File Address");
                }
            }
            // -----------------ask for requirement for completion of program
            System.out.println("Enter Credit limit for Course Type For completion of the Batch");
            System.out.println("Program Core");
            String PC = int_input();
            if(PC.equals("Back"))
            {
                return;
            }
            System.out.println("Program Elective");
            String PE = int_input();
            if(PE.equals("Back"))
            {
                return;
            }
            System.out.println("Capstone");
            String CP = int_input();
            if(CP.equals("Back"))
            {
                return;
            }
            System.out.println("Science Core");
            String SC = int_input();
            if(SC.equals("Back"))
            {
                return;
            }
            System.out.println("Science Elective");
            String SE = int_input();
            if(SE.equals("Back"))
            {
                return;
            }
            System.out.println("General Engineering Core");
            String GC = int_input();
            if(GC.equals("Back"))
            {
                return;
            }
            System.out.println("General Engineering Elective");
            String GE = int_input();
            if(GE.equals("Back"))
            {
                return;
            }
            System.out.println("Humanities Core");
            String HC = int_input();
            if(HC.equals("Back"))
            {
                return;
            }
            System.out.println("Humanities Elective");
            String HE = int_input();
            if(HE.equals("Back"))
            {
                return;
            }
            System.out.println("Open Core");
            String OC = int_input();
            if(OC.equals("Back"))
            {
                return;
            }
            System.out.println("Open Elective");
            String OE = int_input();
            if(OE.equals("Back"))
            {
                return;
            }
            System.out.println("NSS/NCC/NSO");
            String NN = int_input();
            if(NN.equals("Back"))
            {
                return;
            }
            String q4 = "INSERT INTO program_requirement(program,batch,PC,PE,CP,GC,GE,SC,SE,HC,HE,OC,OE,NN) values('"+program+"','"+year+"','"+PC+"','"+PE+"','"+CP+"','"+GC+"','"+GE+"','"+SC+"','"+SE+"','"+HC+"','"+HE+"','"+OC+"','"+OE+"','"+NN+"')";
            String credit1="";
            String credit2="";
            while(true)
            {

                System.out.println("Enter Credit Limit For first session");
                credit1 = input.nextLine();
                try {
                    if(Double.parseDouble(credit1)>0)
                    {
                        break;
                    }else
                    {
                        System.out.println("Invalid Input");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                }
            }

            while(true)
            {

                System.out.println("Enter Credit Limit For Second session");
                credit2 = input.nextLine();
                try {
                    if(Double.parseDouble(credit1)>0)
                    {
                        break;
                    }else
                    {
                        System.out.println("Invalid Input");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                }
            }
            String q1 = "INSERT INTO credit_limit_1st_year(batch,program,session_number,credit_limit) values('"+year+"','"+program+"','1','"+credit1+"') ;";
            String q2 = "INSERT INTO credit_limit_1st_year(batch,program,session_number,credit_limit) values('"+year+"','"+program+"','2','"+credit2+"') ;";
            String q3 = "INSERT INTO institute_status(batch,program,can_add,can_drop) VALUES('"+year+"','"+program+"','YES','YES')";
            query = "INSERT INTO batch(year,program,batch_status) VALUES('"+year+"','"+program+"','OPEN')";
            if(db.executeUpdate(query)==1 && db.executeUpdate(q1)==1 && db.executeUpdate(q2)==1 && db.executeUpdate(q4)==1 && db.executeUpdate(q3)==1)
            {
                con.commit();
                System.out.println("Successfully added this batch, its default status is open");
            }
            else
            {
                con.rollback();
                System.out.println("Rolled Back --- Batch not added, There is some problem");
            }

            System.out.println("0. Add Another Batch");
            System.out.println("-1. Back");
            String in = "";
            while(true)
            {
                in = input.nextLine();
                if(in.equals("0"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return ;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }

        }


    }

    private static void status_batch() throws SQLException{

        System.out.println("Enter -1 for back anytime");
        while(true)
        {
            System.out.println("Enter Year");
            String year = input.nextLine();
            if(year.equals("-1"))
            {
                return;
            }
            System.out.println("Enter Program");
            String program = input.nextLine();
            if(program.equals("-1"))
            {
                return;
            }
            String query  = "select * from batch where year = '"+ year + "' and program = '" + program + "' ;";
            ResultSet res = db.executeQuery(query);
            res.last();
            if(res.getRow()==1)
            {
                clear.clearConsole();
                System.out.println("Current Status of batch : " + year + ", Program : " + program + " is " + res.getString("batch_status") );
                System.out.println("1. Change to OPEN");
                System.out.println("2. Change to CLOSE");
                System.out.println("-1. Back");

                while(true)
                {
                    String in = input.nextLine();
                    if(in.equals("-1"))
                    {
                        clear.clearConsole();
                        System.out.println("Enter -1 for back anytime");
                        break;


                    }
                    else if(in.equals("1"))
                    {
                        query = "UPDATE batch SET batch_status = 'OPEN' where year = '" + year +"' and program = '" +program + "';";
                        clear.clearConsole();
                        if(db.executeUpdate(query)==1)
                        {

                            con.commit();
                            System.out.println("Status Successfully Change to 'OPEN'");

                        }
                        else{
                            con.rollback();
                            System.out.println("Some Unsual thing happen. contact to administration");
                        }
                        back_input();
                        clear.clearConsole();
                        System.out.println("Enter -1 for back anytime");
                        break;
                    }
                    else if(in.equals("2"))
                    {
                        query = "UPDATE batch SET batch_status = 'CLOSE' where year = '" + year +"' and program = '" +program + "';";
                        clear.clearConsole();
                        if(db.executeUpdate(query)==1)
                        {

                            con.commit();
                            System.out.println("Status Successfully Change to 'CLOSE'");

                        }
                        else{
                            con.rollback();
                            System.out.println("Some Unsual thing happen. contact to administration");
                        }
                        back_input();
                        clear.clearConsole();
                        System.out.println("Enter -1 for back anytime");
                        break;
                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                }
            }
            else
            {
                System.out.println("No such batch is there in database");
            }

        }
    }

    public static void add_session()throws SQLException
    {
        System.out.println("Enter -1 for back anytime");
        String query="";
        ResultSet res = null;
        while(true)
        {

            String name = "";
            while(true)
            {
                System.out.println("Enter name of the session");
                name = input.nextLine();
                if(name.equals("-1"))
                {
                    return;
                }
                query = "select * from institue_session where session_name ='" + name + "';";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()!=0)
                {
                    System.out.println("A session with this name is already declared");
                }
                else{
                    break;
                }
            }

            System.out.println("Enter Start date");
            String start_date = input.nextLine();
            if(start_date.equals("-1"))
            {
                return;
            }
            System.out.println("Enter End date");
            String end_date = input.nextLine();
            if(end_date.equals("-1"))
            {
                return;
            }

            query = "INSERT INTO institue_session(session_name,start_dat,end_dat,current_status) values('"+name+"','"+start_date+"','"+end_date+"','Running') ;";
            if(db.executeUpdate(query)==1)
            {
                con.commit();
                System.out.println("Successfully Added new Session");
            }
            else{
                System.out.println("Something is not right, Contact Administration");
            }

            System.out.println("1.Add another session");
            System.out.println("-1. Back");
            while(true)
            {
                String in  = input.nextLine();
                if(in.equals("1"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }

    }

    private static boolean complete_course(String course_id, String faculty_id, String session) throws SQLException
    {
        String query = "select * from enroll_"+course_id+"_"+faculty_id+"_"+session +" where enroll_status = 'Enrolled'";
        ResultSet res = db.executeQuery(query);
        res.last();
        int n = res.getRow();
        if(n>0)
        {
            res.absolute(1);
            String[] entry_no = new String[n];
            for(int i=0;i<n;i++)
            {
                entry_no[i] = res.getString("entry_no");
                res.next();
            }
            for(int i=1;i<=n;i++)
            {
                String q1 = "UPDATE course_"+ entry_no[i-1]+ " SET current_status='Complete' where course_id = '"+course_id +"' and current_session ='"+session+"' and current_status = 'Running' ;";
                int x = db.executeUpdate(q1);
                if(x!=1)
                {

                    System.out.println("Course: "+course_id + " Entry Number: "+ entry_no[i-1] + "  There is Some problem in update");
                    return false;
                }
                // res.next();
            }
        }
        String q2 = "UPDATE float_courses SET current_status = 'Complete' where course_id = '"+course_id+"' and instructor_id = '"+faculty_id+"' and current_session ='"+session+"' and current_status = 'Running';";
        int x = db.executeUpdate(q2);
        if(x!=1)
        {
            System.out.println("Floate course id : " + course_id + " some problem in updating");
            return false;
        }
        else{
            return true;
        }
    }

    private static void complete_session() throws SQLException{

        String query = "";
        String name="";

        while(true)
        {
            System.out.println("Enter -1 for back anytime");
            System.out.println("Enter name of Session");
            name = input.nextLine();
            if(name.equals("-1"))
            {
                return;
            }
            query= "select * from institue_session where session_name = '" + name + "' and current_status = 'Running'";

            ResultSet res = db.executeQuery(query);
            res.next();

            if(res.getRow()==1)
            {
                query = "select * from float_courses where current_session = '"+name+"' and current_status = 'Running';";
                res = db.executeQuery(query);
                res.last();

                int n = res.getRow();
                Boolean flag = true;
                if(n>0)
                {
                    System.out.println("Are you sure? You want to complete the Session : "+name+", Type 'YES' or 'NO'");
                    boolean signal = false;
                    while(true)
                    {

                        String in = input.nextLine();
                        if(in.toUpperCase().equals("YES"))
                        {
                            signal = true;
                            break;
                        }
                        else if(in.toUpperCase().equals("NO"))
                        {
                            signal=false;
                            break;
                        }
                        else{
                            System.out.println("Invalid Input");
                        }
                    }
                    if(!signal)
                    {
                        System.out.println("Process Canceled - Session Not Completed");
                        continue;
                    }

                    res.absolute(1);

                    String[] cid = new String[n];
                    String[] iid = new String[n];
                    for(int i=1;i<=n;i++)
                    {
                        cid[i-1] = res.getString("course_id");
                        iid[i-1] = res.getString("instructor_id");
                        res.next();
                    }
                    for(int i=1;i<=n;i++)
                    {
                        flag = complete_course(cid[i-1],iid[i-1],name);
                        if(flag==false)
                        {
                            break;
                        }
                    }
                }
                if(flag==false)
                {
                    con.rollback();
                    System.out.println("Session Complete - Operation failed");
                }
                else{
                    query = "UPDATE institue_session SET current_status = 'Complete' where session_name = '" + name + "';";
                    String q1 = "UPDATE batch_session SET session_status = 'Complete' where session_name = '" + name + "';";
                    if(db.executeUpdate(query)==1&&flag)
                    {
                        db.executeUpdate(q1);
                        con.commit();
                        System.out.println("Successfully Complete Session");
                        System.out.println("1. Complete another session");
                        System.out.println("-1. Back");
                        while(true)
                        {
                            String in  = input.nextLine();
                            if(in.equals("1"))
                            {
                                clear.clearConsole();
                                break;
                            }
                            else if(in.equals("-1"))
                            {
                                return;
                            }
                            else{
                                System.out.println("Invalid Input");
                            }
                        }

                    }else
                    {
                        con.rollback();
                        System.out.println("Operation failed -  Some Problem in Update Status of Session");
                    }
                }
            }
            else
            {
                System.out.println("Either Session Already Complete or not present");
            }

        }
    }

    private static void assign_session() throws SQLException{

        while(true)
        {
            System.out.println("Enter -1 for back anytime");
            String query = "";
            ResultSet res;
            String batch = "";
            String program = "";
            String name ="";
            while(true)
            {
                System.out.println("Enter Batch");
                batch = input.nextLine();
                if(batch.equals("-1"))
                {
                    return;
                }
                System.out.println("Enter Program");
                program = input.nextLine();
                if(batch.equals("-1"))
                {
                    return;
                }
                query = "select * from batch where year ='"+batch+"' and program ='"+program + "' ;";
                res = db.executeQuery(query);
                res.next();

                if(res.getRow()==0)
                {
                    System.out.println("No Such Combination");
                }
                else{
                    query = " select * from batch_session where batch ='" +batch+"' and program = '"+program + "' and session_status = 'Running'" ;
                    res= db.executeQuery(query);
                    res.next();
                    if(res.getRow()==0)
                    {
                        break;
                    }
                    else{
                        System.out.println("This batch Already has a session in Running");
                    }

                }
            }
            while(true)
            {
                System.out.println("Enter session name");
                name = input.nextLine();
                if(name.equals("-1"))
                {
                    return;
                }
                query = "select * from institue_session where session_name ='"+name +"' and current_status = 'Running'";
                res = db.executeQuery(query);
                res.next();
                if(res.getRow()==0)
                {
                    System.out.println("Either there no session with this name or its not Running");
                }
                else{
                    break;
                }
            }
            String date = java.time.LocalDate.now().toString();
            query = "INSERT INTO batch_session(batch,program,session_name,session_status,assign_date) VALUES('"+batch+"','"+program+"','"+name+"','Running','"+date+"')";

            if(db.executeUpdate(query)==1)
            {
                con.commit();
                System.out.println("Successfully assigned Session");
            }
            else
            {
                con.rollback();
                System.out.println("There some Problem, contact adminstration");
            }


            System.out.println("1. Assigned Another Session");
            System.out.println("-1. Back");
            while(true)
            {
                String in = input.nextLine();
                if(in.equals("1"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    private static void report() throws SQLException{
        System.out.println("Enter -1 for back anytime");
        while(true)
        {
            // clear.clearConsole();
            System.out.println("Enter Entry Number of the Student");
            String entry_no = "";
            String query = "";
            String batch = "";
            String program = "";
            String Student_name = "";
            String department = "";

            ResultSet res;
            while(true)
            {
                entry_no = input.nextLine();
                if(entry_no.equals("-1"))
                {
                    return;
                }
                query = "select * from student where entry_no = '"+entry_no+"' ;";
                res = db.executeQuery(query);
                res.next();

                if(res.getRow()==1)
                {
                    program = res.getString("program");
                    batch = res.getString("batch");
                    Student_name = res.getString("student_name");
                    department = res.getString("department");
                    break;
                }
                else{
                    System.out.println("No Such student is there");
                }

            }

            System.out.println("Enter serial Number of session of which you want to generate Transcript");
            query = "select * from batch_session where batch = '" + batch + "' and program = '"+program+"' and session_status = 'Complete' ORDER BY session_name DESC;";
            res = db.executeQuery(query);
            res.last();
            int n = res.getRow();
            if(n>0)
            {
                // res.absolute(1);
                for(int i=1;i<=n;i++)
                {
                    System.out.println(i+". Semester "+i);
                }
                // for(int i=1;i<=n;i++)
                // {
                //     System.out.println(res.getString("session_name"));
                //     res.next();
                // }

                String in = "";
                int j=0;
                while(true)
                {
                    in = input.nextLine();
                    if(in.equals("-1"))
                    {
                        return;
                    }

                    try {
                        j = Integer.parseInt(in);

                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                        continue;
                    }
                    if(j>=1&&j<=n)
                    {
                        break;
                    }
                    else{
                        System.out.println("Invalid Input");
                        continue;
                    }

                }
                int f = j;
                j=(n-j)+1;
                res.absolute(j);
                String sname = res.getString("session_name");
                try {
                    File f1 = new File("C:/Users/kumar/Desktop/mini/Record/"+entry_no+"_Semester"+f+".txt");
                    // System.out.println("----");
                    Boolean flag1 = true;
                    if(f1.exists())
                    {
                        flag1 =false;
                        System.out.println("File Already Exit");
                        if(f1.delete())
                        {
                            flag1 = true;
                            System.out.println("File Deleted Succesfully");
                        }
                        else{
                            flag1 = false;
                            System.out.println("Not able to Delete File");

                        }
                    }
                    if(flag1==false)
                    {
                        System.out.println("There is Some problem in Deleting Old Existing File");
                    }
                    else{
                        if(f1.createNewFile())
                        {
                            FileWriter writer = new FileWriter("C:/Users/kumar/Desktop/mini/Record/"+entry_no+"_Semester"+f+".txt");

                            writer.append("************************************************************************************************************************************************************\n");
                            writer.append("                                                       INDIAN INSTITUTE OF TECHNOLOGY, ROPAR\n");
                            writer.append("************************************************************************************************************************************************************\n");
                            writer.append("                                                               SEMESTER GRADE REPORT\n");
                            writer.append("NAME        " + Student_name.toUpperCase()+"\n");
                            writer.append("ENTRY NO    " + entry_no.toUpperCase()+"\n");
                            writer.append("BATCH       " + batch.toUpperCase()+ " "+"\n");
                            writer.append("PROGRAMME   " + program.toUpperCase() + " IN " + department.toUpperCase()+"\n" );
                            writer.append("SEMESTER    " + f+"\n\n\n\n\n");

                            query = "select * from course_"+entry_no + " where current_session = '"+sname+"';";
                            res = db.executeQuery(query);
                            writer.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                            writer.append("    COURSE CODE                                              COURSE TITLE                                            CREDITS            GRADE              \n");
                            writer.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                            res.last();
                            int k= res.getRow();
                            if(k!=0)
                            {
                                res.absolute(1);
                                for(int a=1;a<=k;a++)
                                {
                                    String e = res.getString("course_name");
                                    int l = e.length();
                                    int padleft = (93 - l)/2;
                                    int padright = 93 - (l + padleft);
                                    String format = "%12s%7s";
                                    format = format + "%"+ String.valueOf(padleft+l) + "s%" + String.valueOf(padright-1)+"s";
                                    l = res.getString("credit").length();
                                    padleft = (19 - l)/2;
                                    padright = 19 - (l + padleft);
                                    format = format + "%"+ String.valueOf(padleft+l) + "s%" + String.valueOf(padright-1)+"s%8s\n";
                                    String s = String.format(format,res.getString("course_id")," ",e," ",res.getString("credit")," ",res.getString("grade"));
                                    writer.append(s);
                                    res.next();
                                }


                            }
                            else{
                                writer.append("                                                                 NOT ENROLL IN ANY COURSES\n");


                            }
                            writer.append("\n\n\n\n");
                            String[] s = SGPA(sname, entry_no, "=").split(" ");
                            writer.append("SGPA                       = "+s[0]+"\n");
                            writer.append("EARNED CREDITS             = "+s[1]+"\n");
                            s=SGPA(sname, entry_no, "<=").split(" ");
                            writer.append("CGPA                       = "+s[0]+"\n");
                            writer.append("CUMULATIVE EARNED CREDITS  = "+s[1]+"\n");
                            writer.close();

                            System.out.println("Transcript Generated");
                        }
                        else{
                            System.out.println("There is Some problem in Creating New File --  May be File Already Exists");
                        }
                    }
                } catch (Exception e)
                {
                    System.out.println(e);
                    System.out.println("Some problem in Creting or writing in file");

                }

            }else{
                System.out.println("Batch haven't Completed any session yet");
            }

            System.out.println("1. Make Transcript of Another Student");
            System.out.println("-1. Back");
            String in = input.nextLine();
            while(true)
            {
                if(in.equals("1"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return ;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }



        }
    }
    private static int intGrade(String s)
    {
        int grade = 10 - (s.charAt(0)-'A') + (-1)*((s.length()-1)%2);

        return grade;
    }

    private static String SGPA(String s, String entry, String sign) throws SQLException
    {
        double credit=0,cg=0;
        String q1="select * from course_"+entry+" where current_status = 'Complete' and current_session "+sign+" '"+s+"' and grade!='NA' and grade!='E' and grade!='F';";
        ResultSet r1 = db.executeQuery(q1);
        r1.last();
        int x = r1.getRow();
        r1.absolute(1);

        Double z;
        for(int i=0; i<x;i++)
        {
            int y = intGrade(r1.getString("grade"));
            try {
                z = Double.parseDouble(r1.getString("credit"));
            } catch (Exception e) {
                System.out.println("6- Credit can not able to convert into Double course : " + r1.getString("course_id"));
                return "NA";
            }
            cg += z*y;
            credit+=z;
            r1.next();
        }
        if(credit==0)
        {
            return "0 0";
        }
        z = cg/credit;
        String k = String.valueOf(z) + " " + String.valueOf(credit);
        return k ;
    }

    private static void add_course_catalog()  throws SQLException{
        while(true)
        {
            System.out.println("Press -1 for Back Anytime");
            System.out.println("Enter Course Code");
            String course_id = input.nextLine();
            if(course_id.equals("-1"))
            {
                return;
            }
            String query = "select * from course_catalog where course_id = '" + course_id + "' ;";
            ResultSet res = db.executeQuery(query);
            res.next();
            if(res.getRow()!=0)
            {
                System.out.println("Course is Already present in Database");
                continue;
            }
            System.out.println("Enter Course Name");
            String course_name = input.nextLine();

            if(course_name.equals("-1"))
            {
                return;
            }
            System.out.println("Enter Course Credit");


            String credit = "";
            while(true)
            {
                credit = input.nextLine();
                if(credit.equals("-1"))
                {
                    return;
                }
                try{
                    double a =Double.parseDouble(credit);
                    if(a>0)
                    {
                        break;
                    }
                }catch (Exception e)
                {

                }
                System.out.println("Invalid Input");
            }

            System.out.println("Enter Course L-T-P");
            String l_t_p = input.nextLine();
            if(l_t_p.equals("-1"))
            {
                return;
            }
            System.out.println("Enter Course Prerequisites if no then type NA ");
            String pre = input.nextLine();
            if(pre.equals("-1"))
            {
                return;
            }


            query = "INSERT INTO course_catalog(course_id,course_name,credit,L_T_P,prerequisites) values('"+course_id+"','"+course_name+"','"+credit+"','"+l_t_p+"','"+pre+"');";
            if(db.executeUpdate(query)==1)
            {
                con.commit();
                System.out.println("Succesfully added course");
            }
            else{
                con.rollback();
                System.out.println("Problem in Insertion - Contact Adminstration");
            }

            System.out.println("1. Add another course");
            System.out.println("-1. Back");
            while(true)
            {
                String in = input.nextLine();
                if(in.equals("1"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{

                }
            }

        }

    }


    private static void edit_course_catalog() throws SQLException{
        while(true)
        {
            System.out.println("Press -1 for Back Anytime");
            System.out.println("Enter Course Code");
            String course_id = input.nextLine();
            if(course_id.equals("-1"))
            {
                return;
            }
            String query = "select * from course_catalog where course_id = '" + course_id + "' ;";
            ResultSet res = db.executeQuery(query);
            res.next();
            if(res.getRow()==0)
            {
                System.out.println("Course is Not  present in Database");
                continue;
            }
            System.out.println("Enter New Course Name");
            String course_name = input.nextLine();

            if(course_name.equals("-1"))
            {
                return;
            }
            System.out.println("Enter New Course Credit");
            String credit = "";
            while(true)
            {
                credit = input.nextLine();
                if(credit.equals("-1"))
                {
                    return;
                }
                try{
                    double a =Double.parseDouble(credit);
                    if(a>0)
                    {
                        break;
                    }
                }catch (Exception e)
                {

                }
                System.out.println("Invalid Input");
            }

            System.out.println("Enter New Course L-T-P");
            String l_t_p = input.nextLine();
            if(l_t_p.equals("-1"))
            {
                return;
            }
            System.out.println("Enter New Course Prerequisites if no then type NA ");
            String pre = input.nextLine();
            if(pre.equals("-1"))
            {
                return;
            }


            query = "UPDATE course_catalog SET course_name ='"+course_name+"',credit = '"+credit+"', L_T_P = '"+l_t_p+"',prerequisites = '"+pre+"' Where  course_id = '"+course_id+"';";
            if(db.executeUpdate(query)==1)
            {

                con.commit();
                System.out.println("Succesfully Edit the course");
            }
            else{
                con.rollback();
                System.out.println("Contact Adminstration");
            }

            System.out.println("1. Edit another course");
            System.out.println("-1. Back");
            while(true)
            {
                String in = input.nextLine();
                if(in.equals("1"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{

                }
            }
        }

    }
    private static void view_course_catalog() throws SQLException{
        while(true)
        {
            String query = "select * from course_catalog ;";
            ResultSet r = db.executeQuery(query);
            r.last();
            int n = r.getRow();
            if(n>0)
            {
                String format = "%3s %10s %50s %20s %20s %20s";
                System.out.format(format,"S.No" , "Course Code", "Course Name", "Credit","L-T-P","Prerequisites");
                System.out.println();
                r.absolute(1);
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i , r.getString("course_id"), r.getString("course_name"),r.getString("credit"),r.getString("L_T_P"),r.getString("prerequisites"));
                    System.out.println();

                    r.next();
                }
                System.out.print("List of all courses");
            }
            else{
                System.out.print("List is Empty");

            }
            System.out.println("1. Refresh");
            System.out.println("-1. Back");
            while (true)
            {
                String in = input.nextLine();
                if(in.equals("1")){
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1")) {
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }

        }
    }

    private static void course_catalog() throws SQLException{
        System.out.println("Enter -1 for back anytime");

        String in;
        while(true)
        {
            System.out.println("1. Add a New Course");
            System.out.println("2. Edit Existing Course");
            System.out.println("3. View List of Course Catalog");
            System.out.println("-1. Back");
            in = input.nextLine();
            if(in.equals("1"))
            {
                clear.clearConsole();
                add_course_catalog();
                clear.clearConsole();
            }
            else if(in.equals("2"))
            {
                clear.clearConsole();
                edit_course_catalog();
                clear.clearConsole();

            }
            else if(in.equals("3"))
            {
                clear.clearConsole();
                view_course_catalog();
                clear.clearConsole();
            }
            else if(in.equals("-1"))
            {
                return;
            }
            else{
                System.out.println("Invalid Input");
            }
        }
    }

    private static void permission_add_drop() throws SQLException{

        while(true)
        {
            System.out.println("Enter -1 for back anytime");
            String batch="";
            String program = "";
            String query = "";
            ResultSet r;
            while(true)
            {
                System.out.println("Enter Batch");
                batch = input.nextLine();
                if(batch.equals("-1"))
                {
                    return;
                }
                System.out.println("Enter Program");
                program = input.nextLine();
                if(program.equals("-1"))
                {
                    return;
                }
                query = "select * from batch where year ='"+batch+"' and program ='"+program + "' ;";
                r = db.executeQuery(query);
                r.next();
                if(r.getRow()==0)
                {
                    System.out.println("No Such Combination");
                }
                else{
                    break;
                }
            }
            System.out.println("Can Add a Course");
            System.out.println("1. YES");
            System.out.println("2. NO");
            String can_add="";
            while(true)
            {
                can_add = input.nextLine();

                if(can_add.equals("1"))
                {
                    can_add="YES";
                    break;
                }
                else if(can_add.equals("-1"))
                {
                    return;
                }
                else if(can_add.equals("2"))
                {
                    can_add = "NO";
                    break;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
            System.out.println("Can Drop a Course");
            System.out.println("1. YES");
            System.out.println("2. NO");
            String can_drop="";
            while(true)
            {
                can_drop = input.nextLine();
                if(can_drop.equals("1"))
                {
                    can_drop="YES";
                    break;
                }
                else if(can_drop.equals("-1"))
                {
                    return;
                }
                else if(can_drop.equals("2"))
                {
                    can_drop = "NO";
                    break;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }

            query = "UPDATE institute_status SET can_add = '"+can_add+"', can_drop = '" +can_drop + "' where batch = '"+batch+"' and program = '"+program+"';";

            if(db.executeUpdate(query)==1)
            {
                con.commit();
                System.out.println("Permission Updated!");
            }
            else{
                con.rollback();
                System.out.println("Some Problem Occured");
            }
        }

    }
    private static void view_sessions() throws SQLException{
        while(true)
        {
            String query = "select * from institue_session";
            ResultSet r =db.executeQuery(query);
            r.last();
            int n = r.getRow();
            if(n!=0)
            {
                String format = "%3s %20s %20s %20s %20s";
                System.out.format(format,"S.No" , "Session Name", "Start Date", "End Date","Status");
                System.out.println();
                r.absolute(1);
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i , r.getString("session_name"), r.getString("start_dat"),r.getString("end_dat"),r.getString("current_status"));
                    System.out.println();

                    r.next();
                }
                System.out.println("That's the List of sessions");
            }
            else{
                System.out.println("List is Empty -- No session issued");
            }
            System.out.println("0. Refresh");
            System.out.println("-1. Back");
            while(true)
            {
                String in = input.nextLine();
                if(in.equals("0"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }


    }
    private static void view_Batch() throws SQLException{
        while(true)
        {
            String query = "select * from batch";
            ResultSet r =db.executeQuery(query);
            r.last();
            int n = r.getRow();
            if(n!=0)
            {
                String format = "%3s %20s %20s %20s";
                System.out.format(format,"S.No" , "Batch", "Program","Admission status");
                System.out.println();
                r.absolute(1);
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i , r.getString("year"), r.getString("program"),r.getString("batch_status"));
                    System.out.println();

                    r.next();
                }
                System.out.println("That's the List of Batch's");
            }
            else{
                System.out.println("List is Empty -- No Batch issued");
            }
            System.out.println("0. Refresh");
            System.out.println("-1. Back");
            while(true)
            {
                String in = input.nextLine();
                if(in.equals("0"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }
    }


    private static void view_Assigned_Session() throws SQLException{
        while(true)
        {
            String query = "select * from batch_session";
            ResultSet r =db.executeQuery(query);
            r.last();
            int n = r.getRow();
            if(n!=0)
            {
                String format = "%3s %20s %20s %20s %20s %20s";
                System.out.format(format,"S.No" , "Batch", "Program", "Session","Status","Assign Date");
                System.out.println();
                r.absolute(1);
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i , r.getString("batch"), r.getString("program"),r.getString("session_name"),r.getString("session_status"),r.getString("assign_date"));
                    System.out.println();

                    r.next();
                }
                System.out.println("That the List of All seasons that assign to a Batch");
            }
            else{
                System.out.println("List is Empty - No Session is assign to any batch");
            }
            System.out.println("0. Refresh");
            System.out.println("-1. Back");
            while(true)
            {
                String in = input.nextLine();
                if(in.equals("0"))
                {
                    clear.clearConsole();
                    break;
                }
                else if(in.equals("-1"))
                {
                    return;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
        }


    }
    private static void session() throws SQLException{
        while(true)
        {
            clear.clearConsole();
            System.out.println("1. Add a Session");
            System.out.println("2. Complete a Session");
            System.out.println("3. View Sessions");
            System.out.println("-1. Back");
            String in = "";
            int x=0;
            while(true)
            {
                in = input.nextLine();
                if(in.equals("-1"))
                {
                    return;
                }
                else if(in.equals("1")||in.equals("2")||in.equals("3"))
                {
                    try {
                        x = Integer.parseInt(in);
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                    }
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
            if(x==1)
            {
                add_session();
            }
            else if(x==2)
            {
                complete_session();
            }
            else if(x==3)
            {
                view_sessions();
            }
        }
    }



    public static void start()throws SQLException {

//        { commented this block when test this file
        authentication a = new authentication();
        String s[] = a.login(3);
        if(s[0].equals("-1"))
        {
            return;
        }
        else
        {
            username = s[1];
        }
//    }

//        username = "Staff_Dean"; //uncommented this line when testing this code
        connect c = new connect();
        db = c.pgstart();
        con = c.con();
        String q = "select * from special_access where username = '"+username+"';";
        ResultSet r = db.executeQuery(q);
        r.last();
        Boolean access = false;
        if(r.getRow()==0)
        {
            access = false;
        }
        else{
            access = true;
        }

        while(true)
        {
            clear.clearConsole();
            System.out.println("Login as a Administration, Welcome :" + username);
            System.out.println("Home Dashboard!");
            System.out.println("1. Add a Student");
            System.out.println("2. Add a Faculty");//show information regarding current session.
            System.out.println("3. View Sessions");
            System.out.println("4. View Batch");
            System.out.println("5. View Batch Assined Sessions");
            System.out.println("6. Student Details"); // call function in faculty class
            System.out.println("7. Generate Transcript of students");
            System.out.println("8. Log out");
            if(access)
            {
                System.out.println("9. Add new batch");
                System.out.println("10. Sessions");
                System.out.println("11. Change Status of a batch for admission");// user specific
                System.out.println("12. Update Course Catalog");// username specific
                System.out.println("13. Assign session to a batch");
                System.out.println("14. Permission to add and drop course");
            }

            String in  = "";
            int x=0;
            while(true)
            {
                in = input.nextLine();

                try {
                    x = Integer.parseInt(in);

                } catch (Exception e) {
                    System.out.println("Invalid Input");
                    continue;
                }
                if(x>=1&&x<=15&&access)
                {
                    break;
                }
                if(x>=1&&x<=8)
                {
                    break;
                }
                else{
                    System.out.println("Invalid Input");
                }
            }
            if(x==1)
            {
                clear.clearConsole();
                Admission();
                clear.clearConsole();
            }
            else if(x==2)
            {
                clear.clearConsole();
                Add_faculty();
                clear.clearConsole();
            }
            else if(x==3)
            {
                clear.clearConsole();
                view_sessions();
                clear.clearConsole();
            }else if(x==4)
            {
                clear.clearConsole();
                view_Batch();
                clear.clearConsole();
            }else if(x==5)
            {
                clear.clearConsole();
                view_Assigned_Session();
                clear.clearConsole();
            }else if(x==6)
            {
                clear.clearConsole();
                faculty f = new faculty();
                f.acad_student_detail();
                clear.clearConsole();
            }else if(x==7)
            {
                clear.clearConsole();
                report();
                clear.clearConsole();
            }
            else if(x==8)
            {
                return;
            }else if(x==9)
            {
                clear.clearConsole();
                new_batch();
                clear.clearConsole();

            }else if(x==10)
            {
                clear.clearConsole();
                session();
                clear.clearConsole();
            }
            else if(x==11)
            {
                clear.clearConsole();
                status_batch();
                clear.clearConsole();
            }else if(x==12)
            {
                clear.clearConsole();
                course_catalog();
                clear.clearConsole();
            }
            else if(x==13)
            {
                clear.clearConsole();
                assign_session();

                clear.clearConsole();
            }else if(x==14)
            {
                clear.clearConsole();
                permission_add_drop();
                clear.clearConsole();
            }
        }
    }
}
