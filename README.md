# SRSApp (Student Registration System)
This system provide console as well as Swing UI to handle operations for student management system for a university.

This includes provides functionality like:
* Adding new student
* Delete new Student
* Create a new course
* Delete course
* Create a Semester and courses offered
* Enroll student to particular course for given semester
* Unenroll student to particular course for given semester


Master branch has code of console application.
guiSRS has branch has code for UI application.


# Design Doc
Design Document
STUDENT REGISTRATION SYSTEM
CS532 – Database Systems
Submitted by:
Chaitanya Bhasme
Sanket Panhale
2 | D e s i g n D o c u m e n t
Explanation of all Oracle Objects used:
 PROCEDURES
All procedures are declared in package name ‘SRS’.
1) show_students
- Objective: To display the tuples in ‘students’ table.
- Usage: SRS.show_students();
- Approach:
o Open a cursor to select all the tuples from students table.
o Fetch a row using the cursor until a row is found and print row when it is
found.
2) show_courses
- Objective: to display the tuples in ‘courses’ table.
- Usage: SRS.show_courses();
- Approach:
o Open a cursor to select all the tuples from courses table.
o Fetch a row using the cursor until a row is found and print row when it is
found.
3) show_prereq
- Objective: to display the tuples in ‘prerequisites’ table.
- Usage: SRS.show_prereq ();
- Approach:
o Open a cursor to select all the tuples from prerequisites table.
o Fetch a row using the cursor until a row is found and print row when it is
found.
4) show_classes
- Objective: to display the tuples in ‘classes’ table.
- Usage: SRS.show_classes ();
- Approach:
o Open a cursor to select all the tuples from classes table.
o Fetch a row using the cursor until a row is found and print row when it is
found.
5) show_enrollments
- Objective: to display the tuples in ‘enrollments’ table.
- Usage: SRS.show_enrollments ();
- Approach:
o Open a cursor to select all the tuples from enrollments table.
o Fetch a row using the cursor until a row is found and print row when it is
found.
3 | D e s i g n D o c u m e n t
6) show_logs
- Objective: to display the tuples in ‘logs’ table.
- Usage: SRS.show_logs ();
- Approach:
o Open a cursor to select all the tuples from logs table.
o Fetch a row using the cursor until a row is found and print row when it is
found.
7) insertstudent
- Objective: to insert a student into students table from given input information.
- Usage: SRS.insertstudent(‘SID’, ‘FirstName’, ‘LastName’, ‘Status’, GPA,’Email’);
- Approach:
o First check for the input arguments and raise exception in each of the
following cases-
 GPA < 0 or GPA > 4.0
 SID is not like “B%” or SID is already used
 FirstName is null
 LastName is null
 Email is already used
 Status is not in('freshman','sophomore','junior','senior','graduate')
o If all the arguments are correct insert the student info in ‘students’ table.
8) show_course_for_student
- Objective: To show all the courses taken by a input student.
- Usage: show_course_for_student(‘SID’);
- Approach:
o First check for the input argument and raise exception in each of the
following cases-
 SID in not in students table
 There is not courses in ‘enrollments’ table for this SID.
o Open a cursor to select SID, Firstname, DeptCode, Course# and Tile with a
join on students, enrollments, courses and classes tables.
o Fetch a row using the cursor until a row is found and print the details of
the row.
9) get_prereq
- Objective: To show all the prerequisites of a given course.
- Usage: get_prereq(‘dept_code’, ‘course#’);
- Approach:
o Open a cursor to select records from prerequisites table for input
dept_code and course#
o Fetch the record using cursor and print its prerequisites and recursively
call the get_prereq(‘pre_dept_code’, pre_course#);
4 | D e s i g n D o c u m e n t
10) class_details
- Objective: to show the details of the class provided.
- Usage: class_details(‘classid’);
- Approach:
o First check of the classid is present in ‘classes’ table if not then raise an
exception
o Get the title from ‘courses’ table with the course# and dept_code from
the classes
o Open a cursor to select SIDs from enrollments for the input classid.
o Fetch the record using cursor until a row is found
 Select firstname from students table for SID from enrollments
 Display the details classid, title, sid, firstname.
11) enroll_student
- Objective: to enroll a given student in a given class
- Usage: enroll_student(‘SID’, ‘classid’);
- Approach:
o First check the conditions for enrolling a student and raise an exception
in each of following case-
 SID is not found in ‘students’ table
 Classid is not found in ‘classes’ table
 If ‘class_size’ == ‘limit’ in classes table for classid given. i.e. if the
class if already full.
 If the SID is already in enrolled for classid in enrollments table.
 If student is already enrolled for the 4 courses.
 If student has not taken any prerequisites course for this classid
 If student has already taken the course of this classid.
o To find if the prerequisites are taken-
 Open a cursor to select dept_code and course# of prerequisites of
a course.
 If that dept_code and course# is not in list of courses details of
the given student then he has not taken that prerequisite course.
o If no exception is found then insert an new entry in ‘enrollments’ table.
12) drop_student
- Objective: to drop a student from given classid
- Usage: drop_student(‘SID’, ‘classid’)
- Approach:
o First check the input argument and raise an exception in following case-
 SID is not found in students table
 Classid is not found in classes table
5 | D e s i g n D o c u m e n t
 SID is not enrolled for that classid in enrollments table
 If the course of classid is prerequisite of any taken course by SID
o To find the prerequisite
 Open a cursor to select the courses from ‘prerequisites’ table
where prerequisite course is the given course.
 Fetch record using cursor until a record is found and if that
dept_code and course# is present in list of courses taken by the
SID then drop is not permitted.
o If no exception is found then delete the record from enrollments.
o Check of the students is enrolled for any other courses if not notify
‘Student is not enrolled for any classes’
o Check if the class has any other students if not notify ‘Class now has no
student’.
13) delete_student
- Objective: to delete a student from students table
- Usage: delete_student(‘SID’);
- Approach:
o Check if the SID is valid and present in students table if not raise an
exception.
o If not exception found then delete the student from the students table.
 TRIGGERS
1) student_trigger
- Objective: to insert tuples into logs table for every change in students table.
- Approach:
o Check the log action whether inserting, deleting or updating.
o Get user name and system date from dual.
o Insert the values into logs table.
2) enrollments_trigger
- Objective: to insert tuples into logs table for every change in enrollments table.
- Approach:
o Check the log action whether inserting, deleting or updating.
o Get user name and system date from dual.
o Insert the values into logs table.
3) stud_enroll_trigger
- Objective: to update the class information in classes table for record update into
enrollments table
- Approach:
o If inserting from enrollments select the class_size from classes where
classid = new.classid
o Increment class_size;
6 | D e s i g n D o c u m e n t
o Else if deleting from enrollments seles the class_size from classes where
classid = old.classid
o Decrement class_size.
4) stud_unenroll_trigger
- Objective: to delete the record from enrollments for a record deleted from
students table
- Approach:
o Delete from enrollments for sid = old.sid.
Relationships Among Objects:
insert, delete,
update in
students
student_trigger
triggers
insert, delete,
update in
enrollments
enrollments_trigger
triggers
stud_unenroll_trigger stud_enroll_trigger
triggers
stud_unenroll_trigger enrollments_trigger
triggers
Delete from students stud_unenroll_trigger
triggers
7 | D e s i g n D o c u m e n t
Java Classes for GUI – Based application:
Packages:
1) srsapp.choices
a. ClassDetailsChoice: to display the class details of the given class. Calls
class_details() procedure.
b. DeleteStudentChoice: to delete a student from students table. Calls
delete_student() procedure.
c. DisplayTableChoice: to display the tuples from a table given as input. Calls
show_students(),show_courses(),show_prereq(),show_classes(),show_enrollme
nts(),show_logs().
d. DropStudentChoice: to drop a student from a class. Calls drop_student()
procedure.
SRS.insertstudent() student _trigger
triggers
SRS.enroll_student() stud_enroll_trigger
triggers
SRS.enroll_student() enrollments_trigger
triggers
SRS.drop_student() stud_enroll_trigger
triggers
SRS.delete_student() stud_unenroll_trigger
triggers
8 | D e s i g n D o c u m e n t
e. EnrolledCoursesChoice: to enroll a student in a class. Calls enroll_student()
procedure.
f. InsertStudentChoice: to insert a student into students table. Calls insertstudent()
procedure.
g. PrerequisiteChoice: to show the prerequisite of a given course. Call get_prereq()
procedure.
2) srsapp.driver
a. MainDriver: creates a dataconnection
3) srsapp.ui
a. LoginForm: shows the login form for username and password.
b. WorkerForm: worker class for GUI user input selection.
4) srsapp.util
a. ChoiceAbstract: Abstract class for all the choices.
b. StudentInfo: Java bean class for StudentInfo.
c. Worker: worker class to handle the database calls for each user input.
