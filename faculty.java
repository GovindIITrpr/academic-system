// import java.sql.Connection;
// import java.sql.DriverManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;


public class faculty {
    private static String username;
    private static clearCMD clear = new clearCMD();
    private static Scanner input = new Scanner(System.in);
    private static Statement db = null;
    private static String instructor_name = "";
    private static String department = "";

    private static Connection con =null;


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
    private static String serial_input(){
        System.out.println("0. Refresh");
        System.out.println("-1. back");
        String in = input.nextLine();
        if(in.equals("-1"))
        {
            clear.clearConsole();
            return "Back";
        }
        else if(in.equals("0"))
        {
            return "Refresh";
        }
        try {
            Integer.parseInt(in);
        } catch (Exception e) {
            System.out.println("Invalid Input!");
            return "continue";
        }
        return in;
    }

    private static String refresh_back_input(){
        System.out.println("0. Refresh");
        System.out.println("-1. back");
        while(true)
        {
            String in = input.nextLine(); //=================================there it is ==================== all input must be a string ---------- good way
            if(in.equals("0"))
            {
                return "Refresh";
            }
            if(in.equals("-1"))
            {
                return "Back";
            }
            System.out.println("Invalid Input!");
        }

        // return "continue";
    }


    private static String valid_grade(String s){

        String[] a = s.split(" ");
        if(a.length==1)
        {
            if(a[0].equals("A")||a[0].equals("A-")||a[0].equals("B")||a[0].equals("B-")||a[0].equals("C")||a[0].equals("C-")||a[0].equals("D")||a[0].equals("E")||a[0].equals("F"))
            {
                return a[0];
            }
            else
            {
                return "NOT";
            }
        }
        else
        {
            return "NOT";
        }

    }
    private static void grade_single_student(String course_id, String course_name, String session)throws SQLException
    {
        clear.clearConsole();
        System.out.println(" Course Code : " + course_id + " Course Nmae : " + course_name + " Session : "+session );

        while(true)
        {
            System.out.println("For Going Back Press '-1'");
            System.out.println("Enter Entry number of student");
            // System.out.println("-1. Back");
            String query = "";
            String in="";
            ResultSet r=null;
            while(true)
            {
                in  = input.nextLine();
                if(in.equals("-1"))
                {
                    return;
                }
                query = "select * from enroll_"+course_id+"_"+username+"_"+session+" where  entry_no = '"+in+"' and enroll_status ='Enrolled' ;";
                r = db.executeQuery(query);
                r.last();
                if(r.getRow()!=0)
                {
                    break;
                }
                else{
                    System.out.println("No enrolled Student With Entry number : "+in);
                }
            }

            String entry_no = in;
            System.out.println("Enter Grade");
            // System.out.println("-1. Back");
            String grade="";
            while(true)
            {
                in  = input.nextLine();
                if(in.equals("-1"))
                {
                    return;
                }
                grade = valid_grade(in);
                if(grade.equals("NOT"))
                {
                    System.out.println("Not a valid grade");
                }
                else{
                    break;
                }
            }


            query = "UPDATE enroll_"+course_id+"_"+username+"_"+session+" SET grade ='"+grade+"' Where entry_no = '"+entry_no+"' and enroll_status ='Enrolled'";
            int k = db.executeUpdate(query);
            if(k==0)
            {
                con.rollback();
                System.out.println("Some problem in Update marks of student in enroll table");
                continue;
            }
            query = "UPDATE course_"+entry_no+" set grade ='"+grade+"' where course_id='"+course_id+"' and current_session='"+session+"' ;";
            k = db.executeUpdate(query);
            if(k==0)
            {
                con.rollback();
                System.out.println("Grade do not update in student table");
                continue;
            }
            con.commit();
            show_enroll_list(course_id, course_name, session, "Complete");
            System.out.println("Successfully Updated grade");
        }
    }

    private static void grade_entire_class(String course_id, String course_name, String session)throws SQLException
    {
        clear.clearConsole();

        System.out.println(" Course Code : " + course_id + " Course Name : " + course_name + " Session : "+session );

        File file;
        FileReader fr;
        BufferedReader br;
        while(true)
        {
            System.out.println("Enter Address of the file");
            System.out.println("-1. Back");
            String in  = input.nextLine();
            StringBuilder address = new StringBuilder(in);
            if(in.equals("-1"))
            {
                return;
            }
            for(int i=0;i<in.length();i++)
            {
                if(in.charAt(i)=='\\')
                {
                    address.setCharAt(i, '/');
                }
            }
            try {
                file = new File(address.toString());
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String line = "";
                String [] s ;
                int j=1;
                line = br.readLine();//living first line -----------
                j++;
                line = br.readLine();
                boolean flag = true;
                while(line!=null)
                {
                    s = line.split(",");

                    if(s.length==2)
                    {

                        String entry_no = s[0];
                        String grade = valid_grade(s[1]);
                        if(grade.equals("NOT"))
                        {
                            con.rollback();
                            flag = false;
                            System.out.println("line: "+j+", Not a valid grade");
                            break;
                        }

                        String query = "UPDATE enroll_"+course_id+"_"+username+"_"+session+" SET grade ='"+grade+"' Where entry_no = '"+entry_no+"' and enroll_status ='Enrolled'";
                        int k = db.executeUpdate(query);
                        if(k==0)
                        {
                            con.rollback();
                            System.out.println("line:"+j+" Grade Update Failure ---- May be No enrolled Student With Entry number : ["+entry_no+"]");
                            flag = false;
                            break;

                        }
                        query = "UPDATE course_"+entry_no+" set grade ='"+grade+"' where course_id='"+course_id+"' and current_session='"+session+"' ;";
                        k = db.executeUpdate(query);
                        if(k==0)
                        {
                            con.rollback();
                            flag = false;
                            System.out.println("line: "+j+",Grade Update Failure ---- May be course not present in Student Table with Entry Number [" + entry_no+"]");
                            break;
                        }
                    }
                    else{
                        con.rollback();
                        flag = false;
                        System.out.println("line : "+j +", One of the column is empty or more than required column is filled");
                        break;
                    }

                    j++;
                    line = br.readLine();
                }


                // show enroll list

                br.close();
                if(flag==true)
                {
                    con.commit();
                    show_enroll_list(course_id, course_name, session, "Complete");
                    System.out.println("Successful Updated Grades");
                }
                else{
                    con.rollback();
                    System.out.println("UnSuccessful Operation");
                }

            } catch (Exception e) {
                System.out.println("Invalid File Address");
            }
        }

    }


    private static void upload_mark() throws SQLException
    {
        // show all the course that are completed

        while(true)
        {
            clear.clearConsole();
            String query = "select * from float_courses where instructor_id = '" + username + "' and current_status = 'Complete';";
            ResultSet result = db.executeQuery(query);
            result.last();
            if(result.getRow()==0)
            {
                System.out.println("You don't have any Course in complete State");
                String in = refresh_back_input();
                if(in.equals("Refresh"))
                {
                    continue;
                }
                else{
                    return;
                }
            }
            else
            {
                int n = result.getRow();
                result.absolute(1);
                String format = "%3s %15s %30s %20s %15s %15s %15s";

                System.out.format(format,"S.No","Course Code", "Course Name","L-T-P" ,"credit","Session", "Status");
                System.out.println();
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i,result.getString("course_id"), result.getString("course_name"), result.getString("L_T_P"),result.getString("credit"),result.getString("current_session"), result.getString("current_status"));
                    result.next();

                    System.out.println();
                }

                // System.out.println("-1. Back");

                while(true)
                {
                    System.out.println("Note -- Enter the Serial Number of Course of which you want to upload/update marks");
                    String in = serial_input();
                    if(in.equals("Refresh"))
                    {
                        clear.clearConsole();
                        break;
                    }
                    else if(in.equals("Back"))
                    {
                        return;
                    }
                    else if(in.equals("continue"))
                    {
                        continue;
                    }
                    int x = Integer.parseInt(in);
                    if(x>=1&&x<=n){
                        clear.clearConsole();
                        result.absolute(x);
                        String course_id = result.getString("course_id");
                        String course_name = result.getString("course_name");
                        String session = result.getString("current_session");

                        while(true)
                        {
                            System.out.println(" Course Code : " + course_id + " Course Nmae : " + course_name + " Session : "+session );
                            System.out.println("1. Upload/Update Marks of a Single Student");
                            System.out.println("2. Upload/Update Marks Entire class");
                            System.out.println("-1. Back");
                            in = input.nextLine();
                            if(in.equals("-1"))
                            {
                                break;
                            }
                            else if(in.equals("1"))
                            {
                                grade_single_student(course_id,course_name,session);
                                clear.clearConsole();

                            }
                            else if(in.equals("2"))
                            {
                                grade_entire_class(course_id,course_name,session);
                                clear.clearConsole();

                            }
                            else
                            {
                                System.out.println("Invalid Input");
                            }
                        }

                        break;
                    }
                    else
                    {
                        System.out.println("Invalid Input");
                    }

                }
            }

        }

    }
    public static void student_detail()throws SQLException
    {
        while(true)
        {
            System.out.println("Enter the Entry no. of Student");
            System.out.println("-1. Back");
            String entry_no = input.nextLine();
            if(entry_no.equals("-1"))
            {
                return;
            }
            String q = "select * from student where entry_no = '" + entry_no + "';";
            ResultSet r = db.executeQuery(q);
            r.next();
            if(r.getRow()==0)
            {
                System.out.println("No Student present with given Entry Number!");
            }
            else
            {
                String s = r.getString("student_name");
                System.out.println("Student Name   : " + s );
                System.out.println("Entry Number   : " + r.getString("entry_no"));
                System.out.println("Batch          : " + r.getString("batch"));
                System.out.println("Program        : " + r.getString("program"));
                System.out.println("Department     : " + r.getString("department"));
                System.out.println("Phone Number   : " + r.getString("phone"));
                System.out.println("Email          : " + r.getString("email"));
                degree_status(entry_no,  r.getString("program"),r.getString("batch"));

                 System.out.println("That is all the Detail Related to Student : " + s + " Entry Number : "+ entry_no);

            }

        }


    }
    private static int intGrade(String s)
    {
        int grade = 10 - (s.charAt(0)-'A') + (-1)*((s.length()-1)%2);

        return grade;
    }


    //=========================================================================================================================================

    private static String cg(String entry_no) throws SQLException
    {
        double credit=0,cg=0;
        String q1="select * from course_"+entry_no+" where grade!='NA' and grade!='E' and grade!='F';";
        ResultSet r1 = db.executeQuery(q1);
        r1.last();
        int x = r1.getRow();
        r1.absolute(1);
        for(int i=0; i<x;i++)
        {
            int y = intGrade(r1.getString("grade"));

            cg += r1.getInt("credit")*y;
            credit+=r1.getInt("credit");
            r1.next();
        }
        if(credit==0)
        {
            return "Haven't Complete any course Yet or Grade is not Upload yet";
        }
        String s = String.valueOf(cg/credit);
        return s ;
    }


    private static void degree_status(String entry_no, String program, String batch) throws SQLException{

        ResultSet  result;
        String query = "";
        query = "select * from course_"+entry_no + ";";
        result = db.executeQuery(query);
        result.last();
        if( result.getRow()==0)
        {
            // clear.clearConsole();
            System.out.println("Student have not enrolled in any courses \n\n");
        }
        else
        {
            int n =  result.getRow(),i=1;
            String format = "%3s %15s %30s %25s %15s %15s %15s %15s %15s";
            System.out.println("\n\n\n");

            System.out.format(format,"S.No","Course Code", "Course Name","Instructor Name","L-T-P" ,"Credit","Course-Type","Status", "Grade");
            System.out.println();
            result.absolute(1);
            while(i<=n)
            {
                System.out.format(format,i, result.getString("course_id"), result.getString("course_name"),result.getString("instructor_name"),result.getString("L_T_P"), result.getString("credit"), result.getString("course_type") ,result.getString("current_status"),result.getString("grade"));
                result.next();
                System.out.println();

                i++;
            }
        }


        System.out.println(" CGPA --" + cg(entry_no)+"\n\n");
        System.out.println("-------------------------- Degree Status ---------------------------------");

        query = "select * from program_requirement where program ='"+program+"' and batch = '"+batch+"';"; // include batch also in program_requirement
        result = db.executeQuery(query);

        result.next();

        if(result.getRow()==0)
        {
            System.out.println("Nothing to show! Contact your adminstration");
            back_input();
            return;
        }
        ResultSetMetaData r = result.getMetaData();
        int n = r.getColumnCount();
        System.out.println("\n");
        String format = "%3s %15s %15s %15s";
        System.out.format(format,"S.No" , "Course Type", "Requirement", "Your Status");
        System.out.println();
        int z=1;
        for(int i=1;i<=n;i++)
        {
            if(r.getColumnName(i).equals("program")||r.getColumnName(i).equals("batch"))
            {
                continue;
            }
            String x  = r.getColumnName(i).toUpperCase();
            String q1 = "select * from course_"+entry_no + " where current_status ='Complete' and course_type = '" +x+"' and grade != 'NA' and grade != 'E' and grade != 'F';";

            ResultSet res =db.executeQuery(q1);
            double credits=0;
            res.last();

            int k= res.getRow();
            if(k!=0)
            {
                for(int j=1;j<=k;j++)
                {
                    res.absolute(j);

                    try {
                        Double a =Double.parseDouble(res.getString("credit")) ;
                        credits+=a;
                    } catch (Exception e) {
                        System.out.println("1- Credit Not Convert Into double course :" + res.getString("course_id") + "  ,Then your status is not correct");
                        back_input();
                        return;
                    }

                }
            }
            result = db.executeQuery(query);
            result.next();
            System.out.format(format,z , x, result.getString(x), credits);
            System.out.println();

            z++;
        }
        System.out.println("-------------------------- ------------- ---------------------------------");

        return;
    }

    private static void show_enroll_list(String course_id, String course_name, String session, String status) throws SQLException{
        System.out.println("Enrollment List  --    "+ course_id + " -- " + course_name + "  -- " +status + " -- " + session +"");
        String query = "select * from enroll_"+course_id+"_"+username+"_"+session+";";
        ResultSet result  = db.executeQuery(query);
        result.last();
        int k = result.getRow();
        result.absolute(1);
        String format = "%3s %15s %15s %15s %15s";
        System.out.format(format,"S.No","Entry Number", "Student Name","Enroll Status","Grade");
        System.out.println();

        for(int i=1;i<=k;i++)
        {
            System.out.format(format,i,result.getString("entry_no"),result.getString("stu_name"),result.getString("enroll_status"),result.getString("grade"));
            result.next();
            System.out.println();

        }
        System.out.println("Upward is the list of All student are Enrolled in this Course");
        if(k==0)
        {
            System.out.println("No Student is in this Course");
        }

    }




    private static void my_course()throws SQLException {

        while(true)
        {
            clear.clearConsole();
            String query = "select * from float_courses where instructor_id = '"+ username +"' ORDER BY current_session DESC;";
            ResultSet result = db.executeQuery(query);
            // ResultSetMetaData resMD = result.getMetaData();
            result.last();
            int n = result.getRow();
            if(n==0)
            {
                clear.clearConsole();
                System.out.println("you have not Floated any courses");
                String in = refresh_back_input();
                if(in.equals("Refresh"))
                {
                    continue;
                }
                else{
                    return;
                }
            }
            else
            {
                result.absolute(1);
                clear.clearConsole();
                String format = "%3s %10s %40s %10s %10s %20s %10s %10s %15s";
                System.out.format(format,"S.No","Course Code", "Course Name","L-T-P" ,"credit","Prerequisites","CGPA - Criteria","Session", "Status");
                System.out.println("");
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i,result.getString("course_id"), result.getString("course_name"), result.getString("L_T_P"),result.getString("credit"),result.getString("prerequisites"),result.getString("cg_criteria"),result.getString("current_session"), result.getString("current_status"));
                    result.next();
                    System.out.println("");

                }
                System.out.println("Upward is the List of All the Courses that You have Floated");
                System.out.println("Note -- Inter the serial number of course if you want to see info on it!");
                System.out.println("Refresh");
                System.out.println("-1. Back");
                while(true)
                {

                    String in  = input.nextLine();
                    try {
                        Integer.parseInt(in);
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                        continue;
                    }
                    int y = Integer.parseInt(in);
                    if(y==-1)
                    {
                        return;
                    }

                    if(y==0)
                    {
                        break;
                    }
                    else if(y>=1&&y<=n)
                    {

                        result.absolute(y);
                        String course_id = result.getString("course_id");
                        String course_name = result.getString("course_name");;
                        String session = result.getString("current_session");
                        String status = result.getString("current_status");
                        while(true)
                        {
                            //Show enrollment list
                            clear.clearConsole();
                            show_enroll_list(course_id, course_name, session,status);
                            in = refresh_back_input();
                            if(in.equals("Refresh"))
                            {
                                continue;
                            }
                            else
                            {
                                break;
                            }
                        }
                        break;          // control come here only when x== back
                    }
                    else
                    {
                        System.out.println("Invalid Input");
                    }
                }
            }
        }
    }

    //=============================================================================================================================================

    private static void float_courses() throws SQLException
    {
        String course_id = "",cg_criteria="",session="",pre = "",l_t_p = "",credit = "", course_name="";
        while(true)
        {
            String s;
            clear.clearConsole();

            String query="";
            ResultSet result;
            while(true)
            {
                System.out.println("Please Enter Course Code");
                s=input.nextLine();
                if(s.equals("-1"))
                {
                    clear.clearConsole();
                    return;
                }

                //--------------------can improve here-------------------------
                query = "select * from course_catalog where course_id = '" + s +"';";
                result = db.executeQuery(query);

                result.next();
                if(result.getRow()==0)
                {
                    System.out.println("Entered Code is not present in course catalog");
                    continue;
                }
                else
                {
                    course_name = result.getString("course_name");
                    credit = result.getString("credit");
                    l_t_p = result.getString("L_T_P");
                    pre = result.getString("prerequisites");

                    course_id = s;
                    break;
                }
            }
            System.out.println("Enter CGPA Criteria out of 10 and 2 digit after decimal is allowed or NA for no CGPA Criteria");
            while(true)
            {
                s=input.nextLine();
                if(s.equals("-1"))
                {
                    return;
                }
                else if(s.equals("NA"))
                {
                    cg_criteria = s;
                    break;
                }

                //--------------------check the format of CGPA input-------------------------
                Double x;
                try {
                    x=Double.parseDouble(s);
                } catch (Exception e) {
                    System.out.println("Invalid format");
                    continue;
                }

                if(x<=10.00&&x>=0)
                {
                    cg_criteria = s;
                    break;
                }
                else{
                    System.out.println("Invalid format");
                    continue;
                }

            }

            System.out.println("Enter the Session under which it is floating");
            while(true)
            {

                s=input.nextLine();
                if(s.equals("-1"))
                {

                    return;
                }
                query = "select * from institue_session where session_name = '" + s + "' and current_status ='Running' ;";
                result = db.executeQuery(query);
                result.next();
                if(result.getRow()==1)
                {
                    session = s;
                    break;
                }
                else
                {
                    System.out.println("There is no session with given name or it is not Running");
                }
            }
            System.out.println("\n\nCourse Code    : " + course_id);
            System.out.println("Course Name    : " + course_name);
            System.out.println("L-T-P          : " + l_t_p);
            System.out.println("Credit         : " + credit);
            System.out.println("Prerequisites  : " + pre);
            System.out.println("CGPA Criteria  : " + cg_criteria);
            System.out.println("Session        : " + session+"\n\n");
            String in="";

            while(true)
            {
                System.out.println("Are you sure you want to float this course, Type 'YES' or 'NO'");
                in = input.nextLine();
                if(in.toUpperCase().equals("YES"))
                {
                    break;
                }
                else if(in.toUpperCase().equals("NO"))
                {
                    System.out.println("Course floating Canceled");
                    System.out.println("-1. Back");
                    while(true)
                    {
                        s = input.nextLine();
                        if(s.equals("-1"))
                        {
                            return;
                        }
                        else{
                            System.out.println("Invalid Input");
                        }
                    }
                }
                else
                {
                    System.out.println("Invalid Input!");
                }
            }
            query = "select * from float_courses where course_id = '" + course_id + "' and instructor_id = '" + username + "' and current_session = '" + session + "' and current_status = 'Running';"; // completed cant be there because session is still running
            result = db.executeQuery(query);

            result.next();
            if(result.getRow()==0)
            {

                query =  "INSERT into float_courses(course_id,course_name,instructor_id,instructor_name,department,L_T_P,credit,prerequisites,cg_criteria,current_session,current_status) VALUES('"+course_id+"','"+course_name+"','"+username+"','"+instructor_name +"','"+department+"','"+l_t_p+"','"+credit+"','"+pre+"','"+cg_criteria+"','"+session+"','Running') ;";
                String q1 = "CREATE TABLE enroll_"+course_id+"_"+username+"_"+session +"("
                        +"entry_no TEXT NOT NULL,"
                        +"stu_name TEXT NOT NULL,"
                        +"enroll_status TEXT NOT NULL,"
                        +"grade TEXT,"
                        +"PRIMARY KEY(entry_no),"
                        +"FOREIGN KEY(entry_no) REFERENCES student(entry_no)"
                        +");";
                // System.out.println(query + "-----"+q1);
                if(db.executeUpdate(query)==1&&db.execute(q1)==false)
                {

                    con.commit();
                    System.out.println("Successfully floated the course!");
                }
                else{
                    con.rollback();
                    System.out.println("Some Problem Acurred");
                }
            }
            else
            {
                System.out.println("You have already floated this course.\n Start again.");

            }
            System.out.println("1. Float Another Course");
            System.out.println("-1. Back");
            while(true)
            {
                in = input.nextLine();
                if(in.equals("1"))
                {
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


    //=============================================================================================================================================


    private static void drop_course() throws SQLException
    {
        while(true) {
            String query = "select * from float_courses where instructor_id = '" + username + "' and current_status = 'Running';"; // completed cant be there because session is still running
            ResultSet result = db.executeQuery(query);
            result.last();
            int k = result.getRow();
            if (k == 0) {
                System.out.println("No Course in Running status -- You don't have any course to drop");
                back_input();
                return;
            } else {
                result.absolute(1);

                String format = "%3s %15s %30s %15s %15s %15s %15s";
                System.out.format(format, "S.No", "Course Code", "Course Name", "Session", "L-T-P", "Credit", "Status");
                System.out.println();
                for (int i = 1; i <= k; i++) {
                    System.out.format(format, i, result.getString("course_id"), result.getString("course_name"), result.getString("current_session"), result.getString("L_T_P"), result.getString("credit"), "Running");
                    result.next();
                    System.out.println();

                }
                System.out.println("Upward is the list of all the courses You Can drop");
                while (true) {
                    System.out.println("For Drop a course input the S.No of that course!");

                    String in = serial_input();
                    if(in.equals("Refresh"))
                    {
                        clear.clearConsole();
                        break;
                    }
                    else if (in.equals("Back")) {
                        return;
                    } else if (in.equals("continue")) {
                        continue;
                    }
                    int x = Integer.parseInt(in);
                    if (x >= 1 && x <= k) {
                        result.absolute(x);
                        String course_id = result.getString("course_id");
                        System.out.println("Are you Sure? You want to drop " + course_id + " course.");
                        boolean flag1 =true;
                        while (true) {
                            in = input.nextLine();
                            if (in.toUpperCase().equals("YES")) {
                                break;
                            } else if (in.toUpperCase().equals("NO")) {
                                System.out.println("Course Drop Cancel!");
                                flag1 = false;
                                break;
                            } else {
                                System.out.println("Invalid Input!");
                            }
                        }
                        if (!flag1)
                        {
                            continue;
                        }
                        String session = result.getString("current_session");
                        String q = "select * from enroll_" + course_id + "_" + username + "_" + session + " where enroll_status = 'Enrolled'";
                        ResultSet r = db.executeQuery(q);
                        r.last();
                        int z = r.getRow();
                        Boolean flag = true;
                        if (z != 0) {
                            r.absolute(1);
                            String[] entry_no = new String[z];
                            for (int j = 1; j <= z; j++) {
                                entry_no[j - 1] = r.getString("entry_no");
                                r.next();
                            }
                            for (int j = 1; j <= z; j++) {
                                q = "DELETE FROM course_" + entry_no[j - 1] + " where course_id ='" + course_id + "' and current_status = 'Running' ;";
                                if (db.executeUpdate(q) != 1) {
                                    flag = false;
                                }
                                if (!flag) {
                                    System.out.println("Course not Delete from " + entry_no[j - 1] + " table");
                                    break;
                                }
                            }
                        }
                        q = "DELETE FROM float_courses where course_id ='" + course_id + "' and instructor_id = '" + username + "' and current_session = '" + session + "' and current_status = 'Running'";
                        if (db.executeUpdate(q) != 1) {
                            flag = false;
                            System.out.println("Course Not deleted From float course table");
                        }
                        q = "DROP TABLE IF EXISTS enroll_" + course_id + "_" + username + "_" + session + " CASCADE";
                        if (db.execute(q)) {
                            flag = false;
                            System.out.println("Course Table not Dropped");
                        }
                        if (!flag) {
                            con.rollback();
                            System.out.println("Course Drop Unsuccessfully");
                        } else {
                            con.commit();
                            System.out.println("Successfully Dropped the course " + course_id);
                        }
                        System.out.println("1. Drop Another Course");
                        System.out.println("-1. Back");
                        while (true) {
                            in = input.nextLine();
                            if (in.equals("1")) {
                                break;
                            } else if (in.equals("-1")) {
                                return;
                            } else {
                                System.out.println("Invalid Input");
                            }
                        }
                        clear.clearConsole();
                        break;
                    } else {
                        System.out.println("Invalid Input!");
                    }

                }
            }
        }
    }

    public void acad_student_detail()throws SQLException{
        connect c = new connect();
        db = c.pgstart();
        con = c.con();
        student_detail();
    }

    //=========================================================================================================================================

    public static void start()throws SQLException {
//------------------------------Commented this block when testing the code
        authentication a = new authentication();
        String s[] = a.login(2);
        if(s[0].equals("-1"))
        {
            return;
        }
        else
        {
            username = s[1];
        }
//------------------------------------------------------------------------

// -----------------Commented this when Running the code------
//        username = input.nextLine();
// -----------------------------------------------------------
        connect c = new connect();
        db = c.pgstart();
        con = c.con();
        String q = "select * from faculty where faculty_id = '"+username+"';";
        ResultSet r = db.executeQuery(q);
        r.next();
        department = r.getString("department");
        instructor_name = r.getString("faculty_name");
        while(true)
        {
            clear.clearConsole();
            System.out.println("Login as a Faculty, Welcome :" + instructor_name);
            System.out.println("Home Dashboard!");
            System.out.println("1. My courses");
            System.out.println("2. Upload/Upgrade the marks of student!");//show information regarding current session.
            System.out.println("3. Float a course");
            System.out.println("4. Drop a running course");
            System.out.println("5. Find detail of student");
            System.out.println("6. Log out");
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
                if(x>=1&&x<=6)
                {
                    break;
                }
                else{
                    System.out.println("Invalid Input");
                    continue;
                }
            }
            if(x==1)
            {
                clear.clearConsole();
                my_course();
                clear.clearConsole();
            }
            else if(x==2)
            {
                clear.clearConsole();
                upload_mark();
                clear.clearConsole();
            }
            else if(x==3)
            {
                clear.clearConsole();
                float_courses();
                clear.clearConsole();
            }else if(x==4)
            {
                clear.clearConsole();
                drop_course();
                clear.clearConsole();
            }else if(x==5)
            {
                clear.clearConsole();
                student_detail();
                clear.clearConsole();
            }else if(x==6)
            {
                return;
            }
        }
    }
}
