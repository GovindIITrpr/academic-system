import java.sql.*;
import java.util.Scanner;


public class stu {
    private static String username = "";
    private static String batch ="";
    private static String program ="";
    private static String department="";
    private static String Student_name = "";
    private static clearCMD clear = new clearCMD();
    private static Statement db = null;

    private static Connection con = null;
    private static Scanner input = new Scanner(System.in);

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




    private static String refresh_serial_input(){
        System.out.println("0. Refresh");
        System.out.println("-1. back");
        String in = input.nextLine(); //=================================there it is ==================== all input must be a string ---------- good way
        if(in.equals("0"))
        {
            return "Refresh";
        }
        if(in.equals("-1"))
        {
            return "Back";
        }
        // boolean flag = true;
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
        System.out.println("-1. Back");
        while(true)
        {
            String in = input.nextLine();
            if(in.equals("-1"))
            {
                return "Back";
            }
            else if(in.equals("0"))
            {
                return "Refresh";
            }
            else
            {
                System.out.println("Invalid Input!");
            }
        }
    }



    private static void curriculam() throws SQLException{
        String query = "select * from course_curriculam where batch = '"+batch + "' and department = '"+department+"' and program = '"+program +"';";
        ResultSet result = db.executeQuery(query);
        result.last();
        int n = result.getRow();
        if(n==0)
        {
            System.out.println("Nothing to show! Contact your adminstration");
            back_input();
            return;

        }

        String format = "%3s %15s %50s %15s %15s %15s %15s";

        System.out.println();
        System.out.format(format,"S.No","Course Code", "Course Name","course_type","L-T-P" ,"Credit","Prerequisites");
        System.out.println();
        result.absolute(1);
        for(int i=1;i<=n;i++)
        {
            System.out.format(format,i,result.getString("course_id"), result.getString("course_name"),result.getString("course_type"),result.getString("L_T_P") ,result.getString("Credit"),result.getString("prerequisites"));
            result.next();
            System.out.println();
        }

        System.out.println("\n\n");
        System.out.println("Program Requirement: ");
        query = "select * from program_requirement where program ='"+program+"' and batch ='"+batch+"';";
        result = db.executeQuery(query);

        result.next();

        if(result.getRow()==0)
        {
            System.out.println("Nothing to show! Contact your adminstration");
            back_input();
            return;
        }
        ResultSetMetaData r = result.getMetaData();
        n = r.getColumnCount();
        // System.out.println(n+"-----");
        for(int i=1;i<=n;i++)
        {
            if(r.getColumnLabel(i).equals("program")||r.getColumnLabel(i).equals("batch"))
            {
                ;
            }
            else{
                String x = r.getColumnLabel(i);
                System.out.println(x.toUpperCase()+"      :     " +result.getString(x) );
                // System.out.println("==========");
            }

        }
        System.out.println("\n");
        System.out.println("That's Your Course Curriculum, Specific to your Batch and Program");
        back_input();
        return;

    }

    //=============================================================================================================================================
    private static void drop() throws SQLException{
        //check that enrollment is permitted by academic or not

        while(true)
        {
            clear.clearConsole();
            String query = "select * from course_"+username+" where current_status = 'Running'";
            ResultSet result = db.executeQuery(query);
            result.last();
            int n = result.getRow();
            if(n==0)
            {
                System.out.println("No Course in Running Status, or You haven't Enroll in any Course!");
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
                String format = "%3s %15s %40s %25s %15s %15s %15s %15s";
                System.out.format(format,"S.No","Course Code", "Course Name","Instructor name","Session","L-T-P" ,"Credit","Status");
                result.absolute(1);
                System.out.println();
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i,result.getString("course_id"),result.getString("course_name"),result.getString("instructor_name"),result.getString("current_session"),result.getString("L_T_P"),result.getString("credit"),"Running");
                    result.next();
                    System.out.println();

                }
                String q = "select * from institute_status where batch = '" + batch +"' and program = '" +program + "';";

                ResultSet r = db.executeQuery(q);
                r.next();

                if(r.getString("can_drop").equals("NO")){

                    System.out.println("You Don't have permission to Drop a course");

                    String in = refresh_back_input();
                    if(in.equals("Refresh"))
                    {
                        continue;
                    }
                    else{
                        return;
                    }

                }

                System.out.println("For Drop a course input the S.No of that course!");

                while(true)
                {
                    result = db.executeQuery(query);
                    String in = refresh_serial_input();

                    if(in.equals("Refresh"))
                    {
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
                    if(x>=1 && x<=n)
                    {
                        result.absolute(x);

                        String course_id = result.getString("course_id");
                        System.out.println("Are you sure? You want to drop course : "+course_id+" 'YES' OR 'NO'");
                        boolean flag = true;
                        while(true)
                        {
                            in = input.nextLine();
                            if(in.toUpperCase().equals("YES"))
                            {
                                flag = true;
                                break;
                            }
                            else if(in.toUpperCase().equals("NO"))
                            {
                                flag = false;
                                System.out.println("Dropping Cancel");
                                break;
                            }
                            else{
                                System.out.println("Invalid Input");
                            }
                        }

                        if(!flag)
                        {
                            continue;
                        }
                        String session = result.getString("current_session");
                        String instructor_id = result.getString("instructor_id");
                        String q2 ="DELETE FROM course_"+username + " where course_id = '" + course_id + "' and current_status = 'Running';";



                        String q1 = "DELETE FROM enroll_"+course_id+"_"+instructor_id+"_"+session+" where entry_no = '"+username +"';" ;
                        if(db.executeUpdate(q2)==1&&db.executeUpdate(q1)==1)
                        {
                            con.commit();
                            System.out.println("Course successfully Dropped!");

                        }
                        else{
                            con.rollback();
                            System.out.println("Course Drop failed!");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Input!");
                    }
                }

            }
        }
    }

    //=============================================================================================================================================

    //  -------------------------can be better ---------(if we change at one place, effect at other place)-------------------------




    private static void degree_status() throws SQLException{

        String y = cg();
        if(y.equals("NA"))
        {
            back_input();
            return;
        }
        System.out.println("\n\nYour CGPA --" + y);

        String query = "select * from program_requirement where program ='"+program+"' and batch = '"+batch+"';"; // include batch also in program_requirement
        ResultSet result = db.executeQuery(query);

        result.next();

        if(result.getRow()==0)
        {
            System.out.println("Nothing to show! Contact your adminstration");
            back_input();
            return;
        }
        ResultSetMetaData r = result.getMetaData();
        int n = r.getColumnCount();
        System.out.println("\n\n\n\n");
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
            String q1 = "select * from course_"+username + " where current_status ='Complete' and course_type = '" +x+"' and grade != 'NA' and grade != 'E' and grade != 'F';";

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
        System.out.println("\n");
        System.out.println("Upward is Your Degree Status");
        back_input();
        return;
    }

    //=============================================================================================================================================

    private static String course_type(String course_id)throws SQLException {
        String course_type="";
        String query = "select * from course_curriculam where batch = '"+batch+"' and program ='"+program +"'and department = '"+department+"' and course_id = '"+course_id+"'";
        ResultSet result = db.executeQuery(query);

        result.last();



        if(result.getRow()==0)
        {
            String dep = department.substring(0, 2);
            String courseDep = course_id.substring(0,2);
            if(dep.equals(courseDep))
            {
                course_type="PE";
            }
            else if(courseDep.equals("PH")||courseDep.equals("CY")||courseDep.equals("MA")||courseDep.equals("BM")){
                course_type="SE";
            }
            else if(courseDep.equals("HS")){
                course_type="HE";
            }
            else if(courseDep.equals("CP")){
                course_type="CP";
            }
            else if(courseDep.equals("NS")||courseDep.equals("NC")||courseDep.equals("NO"))
            {
                course_type="NN";
            }
            else{
                course_type = "OE";
            }
        }
        else
        {
            course_type = result.getString("course_type");
        }
        return course_type;
    }

    //===========================================================================================================================================

    private static boolean credit_limit(double x, String s) throws SQLException
    {
        String q1 = "select * from batch_session where batch = '" + batch + "' and program = '" + program +"' and session_status = 'Running' ";
        ResultSet r1 =db.executeQuery(q1);
        r1.last();
        if(r1.getRow()==1)  // between the semester
        {

            q1 = "select * from batch_session where batch = '" + batch + "' and program = '"+ program + "' and session_status = 'Complete' ORDER BY session_name DESC ; ";
            r1 = db.executeQuery(q1);
            r1.last();
            int z = r1.getRow();
//            if(z>=2)
//            {
//                System.out.println(z+"----");
//            }
//            else{
//                System.out.println(z+"--I dont Know");
//            }
            if(z>=2)
            {
                String session1 = "",session2="";
                double credits=0;
                r1.absolute(1);
                session1 = r1.getString("session_name");
                r1.next();
                session2 = r1.getString("session_name");

                q1 = "select * from course_"+username +" where current_session = '" +session1 + "' OR current_session = '" + session2 +"' ;";
                r1 = db.executeQuery(q1);

                r1.last();
                int n=r1.getRow();
                for(int i=1;i<=n;i++)
                {
                    r1.absolute(i);
                    try {
                        Double a = Double.parseDouble(r1.getString("credit"));
                        credits+=a;

                    } catch (Exception e) {
                        System.out.println("2- Credit Not Convert Into double course :" + r1.getString("course_id"));
                        return false;

                    }

                }
                double credit_limit = (credits/2)*1.25;
                q1 = "select * from course_"+username +" where current_status = 'Running' ;";

                r1 = db.executeQuery(q1);
                r1.last();

                n= r1.getRow();
                credits=0;
                for(int i=1;i<=n;i++)
                {
                    r1.absolute(i);
                    try {
                        Double a = Double.parseDouble(r1.getString("credit"));
                        credits+=a;

                    } catch (Exception e) {
                        System.out.println("3- Credit Not Convert Into double course :" + r1.getString("course_id"));
                        return false;

                    }
                }
                credits+=x; // course credit added
                if(credit_limit>=credits)
                {
                    return true;
                }
                else
                {
                    System.out.println("1- Course not enrolled! -- Credit limit exceeded!");
                    return false;
                }
            }

            // ---------------------------- 1st year student -----------------------------------------
            else
            {
                String k = String.valueOf(z+1);
                double credits=0;
                double credit_limit=0;
                q1 = "select * from credit_limit_1st_year where session_number = '"+k+"'and batch = '"+batch+"' and program = '"+program+"' ;";
                ResultSet r2 = db.executeQuery(q1);
                r2.last();
                try {
                    Double a = Double.parseDouble(r2.getString("credit_limit"));
                    credit_limit=a;

                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("4- Credit Not Convert Into double");
                    return false;
                }

                q1 = "select * from course_"+username +" where current_status = 'Running' ;";

                r2 = db.executeQuery(q1);
                r2.last();
                int n= r2.getRow();
                credits=0;
                for(int i=1;i<=n;i++)
                {
                    r2.absolute(i);
                    try {
                        Double a = Double.parseDouble(r2.getString("credit"));
                        credits+=a;

                    } catch (Exception e) {
                        System.out.println("5- Credit Not Convert Into double course :" + r2.getString("course_id"));
                        return false;
                    }
                }
                credits+=x;
                if(credit_limit>=credits)
                {
                    return true;
                }
                else
                {
                    System.out.println("Course not enrolled! -- Credit limit exceeded!");
                    return false;
                }
            }
        }
        else
        {
            // summer session time
            q1 = "select * from course_"+username + " where course_id = '" + s +"' and grade='F'";
            ResultSet r3 = db.executeQuery(q1);

            r3.last();
            if(r3.getRow()!=0)
            {
                return true;
            }
            else
            {
                System.out.println("Your semester is not going on! So you can only enroll the course in which you have backlog");

                return false;
            }

        }
    }

    //=========================================================================================================================================

    private static int intGrade(String s)
    {
        int grade = 10 - (s.charAt(0)-'A') + (-1)*((s.length()-1)%2);

        return grade;
    }


    //=========================================================================================================================================


    private static String cg() throws SQLException
    {
        double credit=0,cg=0;
        String q1="select * from course_"+username+" where current_status = 'Complete' and grade!='NA' and grade!='E' and grade!='F';";
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
            return "You Have Not Completed Any Course or Grade not Uploaded in Completed Course";
        }
        z = cg/credit;
        String s = String.valueOf(z);
        return s ;
    }

    //=========================================================================================================================================

    private static boolean check_cg_criteria(String criteria, String course, double credit) throws SQLException
    {
        String cg = cg();
        if(criteria.equals("NA"))
        {
            return check_already_enroll(course,credit);
        }
        if(cg.equals("NA")||cg.equals("You Have Not Completed Any Course or Grade not Uploaded in Completed Course"))
        {
            return false;
        }
        double a=0,b=0;
        try {
            a = Double.parseDouble(cg);
            b = Double.parseDouble(criteria);
        } catch (Exception e) {
            System.out.println("7- Credit can not able to convert into Double");
            return false;
        }
        if(a>=b)
        {

            return check_already_enroll(course,credit);// first convert into double then compare it,if not then it will give wrong answer in some cases 
        }
        else{

            System.out.println("Course not enrolled! -- CGPA criteria not satisfied.");
            return false;
        }
    }

    //===========================================================================================================================================


    private static boolean check_already_enroll(String course, double credit)throws SQLException{
        String q1 = "Select * from course_"+username + " where course_id = '" +course + "' and grade!='F'";
        ResultSet r1 = db.executeQuery(q1);
        r1.last();
        if(r1.getRow()==0)
        {

            return credit_limit(credit,course);
        }
        else
        {
            System.out.println("You have already enrolled the course!");
            return false;
        }
    }

    //===========================================================================================================================================

    private static boolean check_prerequisites(String criteria, String pre, String course, double credit) throws SQLException{
        if(pre.equals("NA"))
        {
            return check_cg_criteria(criteria,course,credit);
        }
        else
        {
            String[] c = pre.split(", ", 0);        //--------------------------------------------
            int x=c.length;
            String q1 = "Select * from course_"+username + " where ( course_id = '" + c[0];
            for(int i=1;i<x;i++)
            {
                q1 = q1 + "' OR course_id = '" + c[i];
            }
            q1 = q1 + "' ) AND grade!='E' AND grade!= 'F' ";
            ResultSet r1 = db.executeQuery(q1);
            r1.last();

            if(x==r1.getRow())
            {

                return check_cg_criteria(criteria,course,credit); //-------------------------- might be problem
            }

            System.out.println("Course not enrolled! -- Prerequisites not completed");
            return false;

        }
    }

    //=========================================================================================================================================



    private static void enroll() throws SQLException
    {

        while(true)
        {
            clear.clearConsole();

            String query = "select * from float_courses where current_status = 'Running'";
            ResultSet result = db.executeQuery(query);
            result.last();
            int n = result.getRow();
            if(n==0)
            {
                System.out.println("No course available for enrollment!");
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
                String format = "%3s %10s %40s %15s %15s %15s %15s %10s %10s %15s";
                System.out.format(format,"S.No","Course Code", "Course Name","Department","Instructor name","Session","L-T-P", "Credit","CGPA Criteria","Prerequisites");
                System.out.println();
                result.absolute(1);
                for(int i=1;i<=n;i++)
                {
                    System.out.format(format,i,result.getString("course_id"),result.getString("course_name"),result.getString("department"),result.getString("instructor_name"),result.getString("current_session"),result.getString("L_T_P"), result.getString("credit"),result.getString("cg_criteria"),result.getString("prerequisites"));
                    System.out.println();
                    result.next();
                }

                String q = "select * from institute_status where batch = '" + batch +"' and program = '" +program + "';";
                result = db.executeQuery(q);
                result.next();

                if(result.getString("can_add").equals("NO")){
                    System.out.println("You Don't have permission to Enroll course");
                    String in = refresh_back_input();
                    if(in.equals("Refresh"))
                    {
                        continue;
                    }
                    else{
                        return;
                    }
                }

                System.out.println("For enroll a course input the S.No of that course!");

                while(true)
                {
                    result = db.executeQuery(query);
                    String in = refresh_serial_input();

                    if(in.equals("Refresh"))
                    {
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
                    if(x>=1 && x<=n)
                    {

                        result.absolute(x);

                        String course_id = result.getString("course_id");
                        System.out.println("Are you sure? You want to Enroll course : "+course_id+" 'YES' OR 'NO'");
                        boolean flag = true;
                        while(true)
                        {
                            in = input.nextLine();
                            if(in.toUpperCase().equals("YES"))
                            {
                                flag = true;
                                break;
                            }
                            else if(in.toUpperCase().equals("NO"))
                            {
                                flag = false;
                                System.out.println("Enrolling Process Cancel - Course Not Enrolled");
                                break;
                            }
                            else{
                                System.out.println("Invalid Input");
                            }
                        }

                        if(flag==false)
                        {
                            continue;
                        }

                        String pre = result.getString("prerequisites");
                        String criteria = result.getString("cg_criteria");
                        double credit = result.getDouble("credit");

                        boolean flag1 = check_prerequisites(criteria,pre,course_id,credit);
                        if(flag1)
                        {
                            result = db.executeQuery(query);
                            result.absolute(x);
                            String session = result.getString("current_session");
                            String course_name = result.getString("course_name");
                            String L_T_P = result.getString("L_T_P");
                            String instructor_id = result.getString("instructor_id");
                            String instructor_name  = result.getString("instructor_name");
                            String course_type = course_type(course_id);


                            // how to check that table is present or not
                            String q2 ="INSERT INTO course_"+username + "(course_id,course_name,course_type,instructor_name,instructor_id,L_T_P,credit,current_session,grade,current_status) VALUES('"+course_id+"','"+course_name+"','"+course_type +"','"+instructor_name+"','"+instructor_id+"','"+L_T_P +"',"+credit+",'"+session +"','NA','Running');";

                            String q1 = "INSERT  INTO enroll_"+course_id+"_"+instructor_id+"_"+session+"(entry_no,stu_name,enroll_status,grade) VALUES('"+username+"','"+Student_name+"','Enrolled','NA');";

                            if(db.executeUpdate(q2)==1&&db.executeUpdate(q1)==1)
                            {
                                con.commit();
                                System.out.println("Course successfully Enrolled!");
                            }
                            else{

                                con.rollback();

                                System.out.println("Some in inserting in tables");
                            }


                        }
                    }
                    else
                    {
                        System.out.println("Invalid Input!");
                    }
                }
            }
        }


    }


    //-------------------


    //=============================================================================================================================================

    private static void record() throws SQLException
    {
        ResultSet  result;
        while(true)
        {
            clear.clearConsole();
            String query = "select * from course_"+username + ";";
            result = db.executeQuery(query);
            result.last();
            if( result.getRow()==0)
            {
                System.out.println("You have not enrolled in any courses");
            }
            else {
                int n = result.getRow(), i = 1;
                String format = "%3s %15s %30s %20s %15s %15s %15s %15s %15s %15s";
                System.out.format(format, "S.No", "Course Code", "Course Name", "Instructor Name", "L-T-P", "Credit", "Course-Type", "Session", "Status", "Grade");
                System.out.println();

                result.absolute(1);
                while (i <= n) {
                    System.out.format(format, i, result.getString("course_id"), result.getString("course_name"), result.getString("instructor_name"), result.getString("L_T_P"), result.getString("credit"), result.getString("course_type"), result.getString("current_session"), result.getString("current_status"), result.getString("grade"));
                    System.out.println();
                    result.next();
                    i++;

                }
                System.out.println("Upward is the List of All the Courses that You have Enrolled");
            }
            String in = refresh_back_input();
            if(in.equals("Refresh"))
            {
                continue;
            }
            else {  // refresh_back_input either return 'Refresh' or 'Back'
                return;
            }
        }
    }


    //=============================================================================================================================================

    public static void start() throws SQLException
    {

//------------------------------Commented this block when testing the code-----
        authentication a = new authentication();
        String s[] = a.login(1);
        if(s[0].equals("-1"))
        {
            return;
        }
        else
        {
            username = s[1];
        }
//-----------------------------------------------------------------------------


// -----------------Commented this when Running the code------
//        username = input.nextLine();
//------------------------------------------------------------


        connect c = new connect();
        db = c.pgstart();

        con = c.con();
        String q = "Select * from student where entry_no = '" + username + "';";
        ResultSet r = db.executeQuery(q);
        r.next();
        batch = r.getString("batch");
        program = r.getString("program");
        department = r.getString("department");
        Student_name = r.getString("student_name");
        while(true)
        {
            clear.clearConsole();
            System.out.println("Login as a Student, Welcome :" + Student_name);
            System.out.println("Home Dashboard!");
            System.out.println("1. My courses");
            System.out.println("2. Course Available for enrollment");
            System.out.println("3. Drop a course");
            System.out.println("4. See course curriculam");
            System.out.println("5. Degree Status");
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
                record();
                clear.clearConsole();
            }
            else if(x==2)
            {
                clear.clearConsole();
                enroll();
                clear.clearConsole();
            }
            else if(x==3)
            {
                clear.clearConsole();
                drop();
                clear.clearConsole();
            }else if(x==4)
            {
                clear.clearConsole();
                curriculam();
                clear.clearConsole();
            }else if(x==5)
            {
                clear.clearConsole();
                degree_status();
                clear.clearConsole();
            }else if(x==6)
            {
                return;
            }
        }
    }
}