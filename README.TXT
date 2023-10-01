COMMAND TO RUN THE PROGRAM-

javac faculty.java
javac mini.java
javac acad.java
javac stu.java
javac connect.java
javac authentication.java
java mini

-------------------------------------------------------------------------

Note - please run code in windows terminal - not in Inside any IDE

Note - Academic login id  - Staff_Dean
                password - 12345
        id - admin
        password - admin

Note - for testing you have add other jar file that listed in pom.xml

Note - for Testing you have to disconnect autentication, for that fo go start function of faculty.java, stu.java. and acad.java

Note - All Test must run in a Sequence, but first run MakeItNew file to reset database.

Note - you have to extract the 'postgresql-42.5.1.jar' in the same location where the other file is present

Note - the transcript that program is generate is on  a paticular location so when you run the program it might give error, so change it before run the program
        location where file location is used -- in acad.java file line number - 1130 (inside record function)
                                             -- in acad.java file line number - 1155 (inside record function)
        location to change the postgres user info ---  in connect.java line number - 13 

Note - I have also make a file to reset database make MakeItNew.java (you have to also change the postgres user info in line 14 and 60)

Note - in my program i use command to clear screen of terminal which might be OS specific. So you might have to change it (location - in clearCMD.java in line 18)


Note - File use to upload marks should have only 2 column  -  first one is of Entry number and other is of Grade and 1st will not be read it is used for header

Note - File use to upload course curriculam of a batch should only have 3 column   -  first one is of course id and 2nd is of department and third one is course_type and 1st will not be read it is used for header

Note - If you face any problem in my program please feel free to contact me.