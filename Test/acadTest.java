import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class acadTest {

    static private final InputStream systemIn = System.in;

    static private final PrintStream systemOut = System.out;

    static private ByteArrayInputStream testIn;

    static private ByteArrayOutputStream testOut;
    static int a = 30;

    static  int b= 1000;
    static int c = 2021;
    static String session_name = "2020_1";
    static int d = 56;//course code
    void provideInput (String inputString) {
        testIn = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testIn);
    }
    void setUpOutput () {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    private String getOutput() {
        return testOut.toString();
    }
//    @AfterAll
    static private void restoreStreams() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void Start(){
        setUpOutput();
        Try.print();

        assertTrue(testOut.toString().contains("Sumit"));
        restoreStreams();
    }
    @Test // TEST1
    public void viewSessions3Empty() throws Exception{
        final String testString = "3\nXYZ\n0\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("List is Empty -- No session issued"));
    }
    @Test //2
    public void viewBatch4Empty() throws Exception{
        final String testString = "4\nXYZ\n0\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("List is Empty -- No Batch issued"));
    }

    @Test //3
    public void viewAssignSession5Empty() throws Exception{
        final String testString = "5\nXYZ\n0\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("List is Empty - No Session is assign to any batch"));
    }
    @Test //4
    public void addBatch2020() throws Exception{
        final String testString = "9\n-1\n" +"9\n2020\nXYZ\n2020\nB.Tech\nXYZ\n-1\n"+"9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/fault1.csv\nXYZ\n-1\n"+"9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/fault2.csv\nXYZ\n-1\n"+ "9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/fault3.csv\nXYZ\n-1\n"+"9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/fault4.csv\nXYZ\n-1\n"+"9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/fault5.csv\nXYZ\n-1\n" +"9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/2020curi.csv\nXYZ\n-1\n"+ "9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/2020curi.csv\n12\nXYZ\n-1\n"+"9\n2020\nB.Tech\nC:/Users/kumar/Desktop/mini/2020curi.csv\n12\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n19\n19\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully added this batch, its default status is open"));
    }
    @Test //5
    public void addBatch2020Already() throws Exception{
        final String testString = "9\n2020\nB.Tech\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("This batch is already declare"));
    }
    @Test //5
    public void addBatch2019() throws Exception{
        final String testString = "9\n2019\nB.Tech\nC:/Users/kumar/Desktop/mini/2020curi.csv\n12\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n19\n19\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully added this batch, its default status is open"));
    }
    @Test //5
    public void _2019BatchAddDropPermit() throws Exception{
        final String testString = "14\n-1\n14\nXYZ\n-1\n14\nXYZ\nXYZ\n-1\n" + "14\n2019\nB.Tech\nXYZ\n-1\n" +"14\n2019\nB.Tech\n1\nXYZ\n-1\n" +"14\n2019\nB.Tech\n1\n1\n-1\n" + "14\n2019\nB.Tech\n2\n2\n-1\n" + "8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Permission Updated!"));
    }

    @Test //5
    public void add2019Student1() throws Exception{
        final String testString = "1\nXYZ\n2019csb1130\n2019\nB.Tech\nCSE\n12345\n1234@gmail.com\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added"));
    }



    @Test //6
    public void addSession2020_1() throws Exception{
        final String testString = "10\n4\n1\n-1\n"+"1\n2020_1\n-1\n"+"1\n2020_1\n01-01-2020\n-1\n"+"1\n2020_1\n01-01-2020\n01-06-2020\n-1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added new Session"));
    }

    @Test //7
    public void addSession2020_1Already() throws Exception{
        final String testString = "10\n1\n2020_1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("A session with this name is already declared"));
    }

    @Test //8
    public void assignSessionToBatch2020_1() throws Exception{
        final String testString = "13\n2021\nB.Tech\n2020\nB.Tech\nxyz\n2020_1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully assigned Session"));
    }
    @Test //9
    public void assignSessionToBatch2020_1Already() throws Exception{
        final String testString = "13\n2021\nB.Tech\n2020\nB.Tech\nxyz\n2020_1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("This batch Already has a session in Running"));
    }


    @Test // 10
    public void addFaculty() throws Exception{
        final String testString = "2\nFaculty1\nF1\n78\nCSE\n3456\n4567\n0\n-1\n8\n";

        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added"));
    }
    @Test //11
    public void addFaculty2() throws Exception{
        final String testString = "2\n-1\n2\n\nF1\nF2\n78\n-1\n2\nFaculty2\nF1\nF2\nCSE\n-1\n2\nFaculty2\nF1\nF2\nCSE\n456\n-1\n2\nFaculty2\nF1\nF2\nCSE\n456\n45678\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added"));
    }

    @Test //12
    public void addStudent1() throws Exception{
        final String testString = "1\n-1\n" +"1\nSumit Kumar\n-1" + "1\nSumit Kumar\n2020csb1130\n-1\n"+"1\nSumit Kumar\n2020csb1130\n2020\n-1\n"+"1\nSumit Kumar\n2020csb1130\n2019\nB.Tech\n2020\nB.Tech\n-1\n" + "1\nSumit Kumar\n2020csb1130\n2020\nB.Tech\nXYZ\nCSE\n-1\n" + "1\nSumit Kumar\n2020csb1130\n2020\nB.Tech\nXYZ\nCSE\n12345\n-1\n"+"1\nSumit Kumar\n2020csb1130\n2020\nB.Tech\nXYZ\nCSE\n12345\n1234@gmail.com\n-1\n"+"1\nSumit Kumar\n2020csb1130\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully Added"));
    }
    @Test //13
    public void addStudent1Already() throws Exception{
        final String testString = "1\nSumit Kumar\n2020csb1130\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("This Entry number is already assigned to other Student"));
    }


    @Test //14
    public void addStudent2() throws Exception{
        final String testString = "1\nNitin Agarwal\n2020csb1104\n2020\nB.Tech\nCSE\n12345\n1234@gmail.com\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added"));
    }
    @Test //15
    public void addStudent3() throws Exception{
        final String testString = "1\nGovind Shaky\n2020mcb1169\n2020\nB.Tech\nCSE\n12345\n1234@gmail.com\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added"));
    }
    @Test //16
    public void _2020BatchAddDropPermitNO() throws Exception{
        final String testString = "14\n2020\nB.Tech\n2\n2\n-1\n" + "8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Permission Updated!"));
    }
    // go to faculty

    //----------------------------------------------------------------

    @Test //35
    public void _2020BatchAddDropPermitYes() throws Exception{
        final String testString = "14\n2020\nB.Tech\n1\n1\n-1\n" + "8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Permission Updated!"));
    }

    // go to stu
    @Test //39
    public void _2020BatchAddDropPermitNO2() throws Exception{
        final String testString = "14\n2020\nB.Tech\n2\n2\n-1\n" + "8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Permission Updated!"));
    }
    // go to stu
    @Test //41
    public void _2020BatchAddDropPermitYes2() throws Exception{
        final String testString = "14\n2020\nB.Tech\n1\n1\n-1\n" + "8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Permission Updated!"));
    }
    // go to stu
    // ------------Complete the Session 2020_1----------------
    @Test //67
    public void completeSession2020_1() throws Exception{
        final String testString = "10\n4\n2\nXYZ\n2020_1\nXYZ\nNO\n2020_1\nYES\n1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Complete Session"));
    }


    // go to faculty

    // -------------------------Release new Session 2020_2 -----------------------
    @Test //72
    public void addSession2020_2() throws Exception{
        final String testString = "10\n4\n1\n-1\n1\n2020_2\n01-01-2020\n01-06-2020\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added new Session"));
    }
    // go to faculty


    // ----------------Start New Semester -- Assign session to batch ----------------
    @Test //75
    public void assignSessionToBatch2020_2() throws Exception{
        final String testString = "13\n2021\nB.Tech\n2020\nB.Tech\nxyz\n2020_2\n1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully assigned Session"));
    }

    // go to faculty

    // ------------------- Complete Semester 2 2020_2 ----------------------------
    @Test //88
    public void completeSession2020_2() throws Exception{
        final String testString = "10\n2\n2020_2\nYES\n1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully Complete Session"));
    }

    // go to faculty


    //-------------------- Release new Session 2021_1 --------------------------------
    @Test //90
    public void addSession2021_1() throws Exception{
        final String testString = "10\n4\n1\n2021_1\n01-01-2021\n01-06-2021\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully Added new Session"));
    }

    // ----------------Start New Semester -- Assign session to batch ----------------
    @Test //91
    public void assignSessionToBatch2021_1() throws Exception{
        final String testString = "13\n2020\nB.Tech\n2021_1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully assigned Session"));
    }

    // go to faculty


    @Test //104 //"That's the List of Batch's"
    public void viewSessions3() throws Exception{
        final String testString = "3\nXYZ\n0\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("That's the List of sessions"));
    }
    @Test //105
    public void viewBatch3() throws Exception{
        final String testString = "4\nXYZ\n0\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("That's the List of Batch's"));
    }

    //"That the List of All seasons that assign to a Batch"
    @Test //106
    public void viewAssignSession5() throws Exception{
        final String testString = "5\nXYZ\n0\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("That the List of All seasons that assign to a Batch"));
    }

    @Test //107
    public void transcript() throws Exception{
        final String testString = "7\nXYZ\n2020csb1130\n-2\n-1\n" + "7\n2020csb1130\n1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Transcript Generated"));
    }
    @Test //108
    public void ViewSession2() throws Exception{
        final String testString = "10\n4\n3\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("That's the List of sessions"));
    }

    @Test //109
    public void AdmissionBatchStatus() throws Exception{
        final String testString = "11\nXYZ\nXYZ\n2019\nB.Tech\n3\n1\n-1\n-1\n"+"11\nXYZ\nXYZ\n2019\nB.Tech\n3\n2\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Status Successfully Change to"));
    }
    //110
    @Test //110
    public void viewCourseCatalog() throws Exception{
        final String testString = "12\n4\n3\n4\n0\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("List of all courses"));
    }
    @Test //111
    public void AddCourseCatalog() throws Exception{
        final String testString = "12\n1\nCS201\n-1\n1\nZXC\n-1\n1\nZXC\nzxc\nZX\n-3\n-1\n1\nZXC\nzxc\n4\n-1\n1\nZXC\nzxc\n4\n1-2-3\n1\nZXC\nzxc\n4\n1-2-3\nNA\n-1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Succesfully added course"));
    }
     @Test   //112
    public void EditCourseCatalog() throws Exception{
        final String testString = "12\n2\nXYZ\nZXC\n-1\n2\nZXC\nzxc\nZX\n-3\n-1\n2\nZXC\nzxc\n4\n-1\n2\nZXC\nzxc\n4\n1-2-3\n2\nZXC\nzxc\n4\n1-2-3\nNA\n-1\n-1\n-1\n8\n";
        provideInput(testString);
        setUpOutput();
        acad.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Succesfully Edit the course"));
    }

    @Test //113 authentication
    public void authentication1() throws Exception{
        final String testString = "XYZ\n-1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(1);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("-1"));
    }
    @Test //114
    public void authentication2() throws Exception{
        final String testString = "XYZ\n2020csb1130\nXYZ\n-1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(1);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("-1"));
    }
    @Test //115
    public void authentication3() throws Exception{
        final String testString = "XYZ\n2020csb1130\nXYZ\n2020csb1130\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(1);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("1"));
    }

    @Test //116 authentication
    public void authentication4() throws Exception{
        final String testString = "XYZ\n-1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(2);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("-1"));
    }
    @Test //117
    public void authentication5() throws Exception{
        final String testString = "XYZ\nF1\nXYZ\n-1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(2);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("-1"));
    }
    @Test //118
    public void authentication6() throws Exception{
        final String testString = "XYZ\nF1\nXYZ\nF1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(2);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("1"));
    }
    @Test //119 authentication
    public void authentication7() throws Exception{
        final String testString = "XYZ\n-1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(3);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("-1"));
    }
    @Test //120
    public void authentication8() throws Exception{
        final String testString = "XYZ\nStaff_Dean\nXYZ\n-1\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(3);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("-1"));
    }
    @Test //121
    public void authentication9() throws Exception{
        final String testString = "XYZ\nStaff_Dean\nXYZ\n12345\n";
        provideInput(testString);
        setUpOutput();
        String[] s =  authentication.login(3);
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(s[0].equals("1"));
    }





}