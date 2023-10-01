import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;


class facultyTest {




    static private final InputStream systemIn = System.in;

    static private final PrintStream systemOut = System.out;

    static private ByteArrayInputStream testIn;

    static private ByteArrayOutputStream testOut;
    static int a = 30;

    static int b = 1000;
    static int c = 2021;
    static String session_name = "2020_1";
    static int d = 56;//course code

    void provideInput(String inputString) {
        testIn = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testIn);
    }

    void setUpOutput() {
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
    public void Start() {
        setUpOutput();
        Try.print();

        assertTrue(testOut.toString().contains("Sumit"));
        restoreStreams();
    }

//    F1 - faculty 1 -
//    F2 - faculty 2 -


    @Test //17
    public void Faculty1MyCourse() throws Exception{ //when no course is flosted
        final String testString = "F1\n1\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("you have not Floated any courses"));
    }
    @Test //18
    public void Faculty1UploadMarks() throws Exception{ //when no course is in Complete State
        final String testString = "F2\n2\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("You don't have any Course in complete State"));
    }
    @Test//19
    public void Faculty1DropCourse() throws Exception{ //when no course is Running
        final String testString = "F1\n4\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("No Course in Running status -- You don't have any course to drop"));
    }

    // go to stuTest
    //------------------------------------------------------------



    @Test //24 ---------------------- Session 1 ----------------
    public void Faculty1FloatCourse1() throws Exception{
        final String testString = "F1\n3\nXYZ\n-1\n" + "3\nCS201\nXYZ\n10.999\n-9\n-1\n" + "3\nCS201\nNA\nXYZ\n-1\n" + "3\nCS201\nNA\n2020_1\nXYZ\nNO\n-2\n-1\n" +"3\nCS201\nNA\n2020_1\nYES\n-2\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //25
    public void Faculty1FloatCourse1AlreadyFloat() throws Exception{
        final String testString = "F1\n" +"3\nCS201\nNA\n2020_1\nYES\n1\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("You have already floated this course"));
    }
    @Test //26
    public void Faculty1FloatCourse2() throws Exception{
        final String testString = "F1\n" +"3\nMA201\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //27
    public void Faculty1FloatCourse3() throws Exception{
        final String testString = "F1\n" +"3\nHS201\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    @Test //28
    public void Faculty1FloatCourse4() throws Exception{
        final String testString = "F1\n" +"3\nCS304\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //29
    public void Faculty1FloatCourse5() throws Exception{
        final String testString = "F1\n" +"3\nCS204\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //30
    public void Faculty1FloatCourse6() throws Exception{
        final String testString = "F1\n" +"3\nGE107\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //31
    public void Faculty1FloatCourse7() throws Exception{
        final String testString = "F1\n" +"3\nCS202\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }



    @Test //32
    public void Faculty1MyCourseFloatCourse() throws Exception{ //when Some course is floated
        final String testString = "F1\n1\n-2.7\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Upward is the List of All the Courses that You have Floated"));
    }
    @Test // 33
    public void Faculty1ViewEnrollListNoEnroll() throws Exception{ //No enroll
        final String testString = "F1\n1\n1\n-2\n0\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("No Student is in this Course"));
    }

    // go to StuTest

    @Test //60
    public void Faculty1ViewEnrollList() throws Exception{ //when Some course is floated
        final String testString = "F1\n1\n1\n-2\n0\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Upward is the list of All student are Enrolled in this Course"));
    }

    //drop a course
    @Test //61
    public void Faculty1ViewDropList() throws Exception{ //when Some course is floated // sea it again
        final String testString = "F1\n4\nXYZ\n100\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Upward is the list of all the courses You Can drop"));
    }

    @Test //62    // Test Case which change the database make sure that it is run when no other session is interacting with database
    public void Faculty1DropCourse1CS201() throws Exception{ //when Some course is floated // sea it again
        final String testString = "F1\n4\nXYZ\n100\n0\n-1\n"+ "4\n1\nXYZ\nNO\n1\nYES\n1\n-1\n6\n"; // dropping (CS201) 1ST number course
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully Dropped the course CS201"));
    }

    @Test //63
    public void Faculty1FloatCourse1ReFloat() throws Exception{
        final String testString = "F1\n" +"3\nCS201\nNA\n2020_1\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    //go to stu

    // -----------------Session 2020_1 Completed------------------------------

    @Test //68
    public void Faculty1UploadMarksEntireClassCS201InvalidAddress() throws Exception{
        final String testString = "F1\n" +"2\n-2\n4\n3\n2\n-1\n2\nXYZ\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Invalid File Address"));
    }
    @Test //69
    public void Faculty1UploadMarksEntireClassCS201InvalidGrade() throws Exception{
        final String testString = "F1\n" +"2\n-2\n4\n3\n2\nC:/Users/kumar/Desktop/mini/InvalidGrade.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Not a valid grade"));
    }

    @Test //70
    public void Faculty1UploadMarksEntireClassCS201InvalidNUmberOfColumnInFile() throws Exception{
        final String testString = "F1\n" +"2\n-2\n4\n3\n2\nC:/Users/kumar/Desktop/mini/TooManyColumn.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("One of the column is empty or more than required column is filled"));
    }

    @Test //71
    public void Faculty1UploadMarksEntireClassCS201() throws Exception{
        final String testString = "F1\n" +"2\n-2\n7\n3\n2\nC:/Users/kumar/Desktop/mini/Marks.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successful Updated Grades"));
    }
    @Test //72
    public void Faculty1UploadMarksSingleStudentCS201() throws Exception{
        final String testString = "F1\n" +"2\n-2\n7\n3\n1\n-1\n1\nXYZ\n2020csb1130\nXYZ\n-1\n1\n2020csb1130\nA-\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully Updated grade"));
    }
    @Test //71
    public void Faculty1UploadMarksEntireClassMA201() throws Exception{
        final String testString = "F1\n" +"2\n-2\n1\n3\n2\nC:/Users/kumar/Desktop/mini/Marks.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successful Updated Grades"));
    }
    @Test //71
    public void Faculty1UploadMarksEntireClassHS201() throws Exception{
        final String testString = "F1\n" +"2\n-2\n2\n3\n2\nC:/Users/kumar/Desktop/mini/Marks.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successful Updated Grades"));
    }

    @Test //71
    public void Faculty1UploadMarksEntireClassCS204() throws Exception{
        final String testString = "F1\n" +"2\n-2\n4\n3\n2\nC:/Users/kumar/Desktop/mini/Marks.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successful Updated Grades"));
    }
    @Test //71
    public void Faculty1UploadMarksEntireClassGE107() throws Exception{
        final String testString = "F1\n" +"2\n-2\n5\n3\n2\nC:/Users/kumar/Desktop/mini/Marks.csv\n-1\n-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successful Updated Grades"));
    }


    // ------------------------ Release new Session 2020_2 But Not Assigned to Batch 2020------------------------

    // go to acadTest

    @Test //73
    public void Faculty1FloatCourseSummerTime() throws Exception{
        final String testString = "F1\n" +"3\nBM101\nNA\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    // go to stu and try to enroll ----

    // drop BM101----

    @Test //74   // Test Case which change the database make sure that it is run when no other session is interacting with database
    public void Faculty1DropCourse1SummerTimeBM101() throws Exception{
        final String testString = "F1\n4\n1\nYES\n-1\n6\n"; // dropping (BM101) 1ST number course
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully Dropped the course BM101"));
    }

    // ----------- Start New Semester -------------- Assign session to batch ---------------
    // go to acad

    @Test //76
    public void Faculty1FloatCourse12020_2() throws Exception{
        final String testString = "F1\n" +"3\nCS202\n8\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //77
    public void Faculty1FloatCourse22020_2() throws Exception{
        final String testString = "F1\n" +"3\nMA202\n8\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //78
    public void Faculty1FloatCourse32020_2() throws Exception{
        final String testString = "F1\n" +"3\nCS303\n8\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    @Test //79
    public void Faculty1FloatCourse42020_2() throws Exception{
        final String testString = "F1\n" +"3\nCS203\n8\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    @Test //80
    public void Faculty1FloatCourse52020_2() throws Exception{
        final String testString = "F1\n" +"3\nCS305\n8\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }
    @Test //81
    public void Faculty1FloatCourse62020_2() throws Exception{
        final String testString = "F1\n" +"3\nHS104\n8\n2020_2\nYES\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    // go to stu ---- to enroll

    @Test //89
    public void Faculty1UploadMarksSingleStudentSem2() throws Exception{
        final String testString = "F1\n2\n"+"8\n1\n2020csb1130\nA-\n-1\n-1\n" + "9\n1\n2020csb1130\nA-\n-1\n-1\n" +"10\n1\n2020csb1130\nA-\n-1\n-1\n"+ "11\n1\n2020csb1130\nA-\n-1\n-1\n" +"12\n1\n2020csb1130\nA-\n-1\n-1\n" +"-1\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully Updated grade"));
    }

    //go to acad

    // Semester 3 2021_1-------------------



    @Test //92
    public void Faculty1FloatALLCourse2021_1() throws Exception{
        final String testString = "F1\n" +"3\nEE201\n8\n2021_1\nYES\n1\nCS301\n8\n2021_1\nYES\n1\nCS302\n8\n2021_1\nYES\n1\nCS306\n8\n2021_1\nYES\n1\nCS304\n8\n2021_1\nYES\n1\nBM101\n10\n2021_1\nYES\n1\nCS307\n8\n2021_1\nYES\n-1\n-1\n" + "6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully floated the course!"));
    }

    @Test //93
    public void Faculty1StudentDetail() throws Exception{
        final String testString = "F1\n5\nXYZ\n2020csb1130\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        faculty.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("That is all the Detail Related to Student"));
    }

//    go to stu




}