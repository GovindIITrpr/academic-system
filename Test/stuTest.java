import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class stuTest {
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
    @Test //20
    public void Student1MyCourseNoEnroll() throws Exception{ // when no course is Enrolled
        final String testString = "2020csb1130\n" + "1\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("You have not enrolled in any courses"));
    }

    @Test //21
    public void Student1enrollNoFloat() throws Exception{ // when no course is Floated
        final String testString = "2020csb1130\n" + "2\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("No course available for enrollment!"));

    }

    @Test //23
    public void Student1DropNoRunningCourse() throws Exception{ // when no course is Floated
        final String testString = "2020csb1130\n" + "3\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("No Course in Running Status, or You haven't Enroll in any Course!"));

    }

    // go to FacultyTest

    //-------------------------------------------------------

    @Test //34
    public void Student1EnrollNoPermit() throws Exception{
        final String testString = "2020csb1130\n" + "2\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("You Don't have permission to Enroll course"));
    }
    // go to acad
    @Test //36      //Course successfully Enrolled!
    public void Student1EnrollCourse1() throws Exception{ //CS201 -- if all test run in a sequence
        final String testString = "2020csb1130\n"+"2\n-2\n0\n-1\n" + "2\n1\nXYZ\nNO\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    @Test //37     //Upward is the List of All the Courses that You have Enrolled
    public void Student1MyCourse() throws Exception{ // when Some course is Enrolled
        final String testString = "2020csb1130\n" + "1\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        assertTrue(testOut.toString().contains("Upward is the List of All the Courses that You have Enrolled"));
    }

    @Test //38
    public void Student1AlreadyEnrollCourse1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("You have already enrolled the course!"));
    }
    // go to acad
    @Test //40
    public void Student1DropNoPermit() throws Exception{
        final String testString = "2020csb1130\n" + "3\n-2\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("You Don't have permission to Drop a course"));
    }
    //go to acad
    @Test //42
    public void Student1DropCourse1() throws Exception{
        final String testString = "2020csb1130\n"+"3\n-2\n1\nXYZ\nNO\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Dropped!"));
    }
    @Test //43
    public void Student1ReEnroll() throws Exception{
        final String testString = "2020csb1130\n"+"2\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //44
    public void Student1EnrollCourse2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n2\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //45
    public void Student1EnrollCourse3() throws Exception{
        final String testString = "2020csb1130\n"+"2\n3\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //46
    public void Student1EnrollCourse4() throws Exception{
        final String testString = "2020csb1130\n"+"2\n5\nYES\n-1\n6\n"; //fifth number course
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //47
    public void Student1EnrollCourse5() throws Exception{
        final String testString = "2020csb1130\n"+"2\n6\nYES\n-1\n6\n"; //sixth number course
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //48   // Credit Limit Exceed
    public void Student1EnrollCourse6ExpectCreditLimitExceed() throws Exception{
        final String testString = "2020csb1130\n"+"2\n4\nYES\n-1\n6\n"; // try to enroll 4th number course CS304
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course not enrolled! -- Credit limit exceeded!"));
    }
    @Test //49 // Prerequisites not completed
    public void Student1EnrollCourse7ExpectPrerequisites() throws Exception{
        final String testString = "2020csb1104\n"+"2\n7\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course not enrolled! -- Prerequisites not completed"));
    }
    @Test //50
    public void Student2EnrollCourse1() throws Exception{
        final String testString = "2020csb1104\n"+"2\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //51
    public void Student2EnrollCourse2() throws Exception{
        final String testString = "2020csb1104\n"+"2\n2\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //52
    public void Student2EnrollCourse3() throws Exception{
        final String testString = "2020csb1104\n"+"2\n3\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //53
    public void Student2EnrollCourse4() throws Exception{
        final String testString = "2020csb1104\n"+"2\n5\nYES\n-1\n6\n";//Fifth number course
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //54
    public void Student2EnrollCourse5() throws Exception{
        final String testString = "2020csb1104\n"+"2\n6\nYES\n-1\n6\n"; //sixth number course
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    @Test //55
    public void Student3EnrollCourse1() throws Exception{
        final String testString = "2020mcb1169\n"+"2\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //56
    public void Student3EnrollCourse2() throws Exception{
        final String testString = "2020mcb1169\n"+"2\n2\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //57
    public void Student3EnrollCourse3() throws Exception{
        final String testString = "2020mcb1169\n"+"2\n3\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //58
    public void Student3EnrollCourse4() throws Exception{
        final String testString = "2020mcb1169\n"+"2\n5\nYES\n-1\n6\n";//Fifth number course
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //59
    public void Student3EnrollCourse5() throws Exception{
        final String testString = "2020mcb1169\n"+"2\n6\nYES\n-1\n6\n"; //sixth number course
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    //back to faculty

    @Test //64
    public void Student1ReEnrollCourse1CS201AfterCourseIsDropByInstructor() throws Exception{
        final String testString = "2020csb1130\n"+"2\n7\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //65
    public void Student2ReEnrollCourse1CS201AfterCourseIsDropByInstructor() throws Exception{
        final String testString = "2020csb1104\n"+"2\n7\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    @Test //66
    public void Student3ReEnrollCourse1CS201AfterCourseIsDropByInstructor() throws Exception{
        final String testString = "2020mcb1169\n"+"2\n7\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }


    //------------Complete Session(Semester 1) ----------------------
    // go to acadTest


    //---------------------New Session release by not Assigned to batch 2020 ---------------

    //--Summer time ----
    @Test //74
    public void Student1EnrollCourse1SummerTime() throws Exception{
        final String testString = "2020csb1130\n"+"2\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Your semester is not going on! So you can only enroll the course in which you have backlog"));
    }
    // --- go to faculty ----

//    Semester 2 started

    @Test //82
    public void Student1EnrollCourse12020_2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n1\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //83
    public void Student1EnrollCourse22020_2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n2\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    @Test //84
    public void Student1EnrollCourse32020_2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n3\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    @Test //85
    public void Student1EnrollCourse42020_2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n4\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }

    @Test //86
    public void Student1EnrollCourse52020_2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n5\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //87
    public void Student1EnrollCourse62020_2() throws Exception{
        final String testString = "2020csb1130\n"+"2\n6\nYES\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course not enrolled! -- Credit limit exceeded!"));
    }

    // go to acad
    // to complete the semester 2

    // ------------ Semester 3 Started ----------------------------

    // enroll courses

    @Test //94
    public void Student1EnrollCourse12021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n1\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //95
    public void Student1EnrollCourse22021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n2\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //96
    public void Student1EnrollCourse32021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n3\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //97
    public void Student1EnrollCourse42021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n4\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //98
    public void Student1EnrollCourse52021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n5\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course successfully Enrolled!"));
    }
    @Test //99
    public void Student1EnrollCourse62021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n6\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course not enrolled! -- CGPA criteria not satisfied."));
    }
    @Test //100
    public void Student1EnrollCourse72021_1() throws Exception{
        final String testString = "2020csb1130\n"+"2\n7\nYES\n-1\n"+  "6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());

        assertTrue(testOut.toString().contains("Course not enrolled! -- Credit limit exceeded!"));
    }

    @Test //101
    public void Student1MyCourses() throws Exception{
        final String testString = "2020csb1130\n1\n4\n0\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Upward is the List of All the Courses that You have Enrolled"));
    }
    @Test //102
    public void Student1DegreeStatus() throws Exception{
        final String testString = "2020csb1130\n5\n4\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Upward is Your Degree Status"));
    }
    //
    @Test //103
    public void Student1CourseCurriculum() throws Exception{
        final String testString = "2020csb1130\n4\n4\n-1\n6\n";
        provideInput(testString);
        setUpOutput();
        stu.start();
        restoreStreams();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("That's Your Course Curriculum, Specific to your Batch and Program"));
    }

}