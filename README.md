# AttendanceApp
This is a basic Android Attendance App 

Android’s “Attendance Management System” deals with the maintenance of the
student’s attendance details. It generates the attendance report of the student on the
basis of their presence in class. It is maintained on the daily basis of their attendance.
The teacher will be provided with the separate username and password to maintain
student’s status. The teachers handling the particular subjects is responsible to make
the attendance. The attendance status of students can also be checked by their
parents as individual parents will be provided separate username and password. We
choose to develop this application for the convenience of college administration in
taking attendance of students, remove the problem of manual attendance system, to
collect feedback from parents, and to provide additional features to improve
productivity of educational system.

1. METHODOLOGY
This project is developed using android studio and firebase as a database. It is
divided into two phases. In first phase, feasibility study is conducted, requirement
analysis is carried out to understand the need for the system and the necessary
modules such admin module, parent module, student module and teacher module
are identified and explored in section, in the design phase, requirements are
converted into system design. In the second phase, design is converted into coding,
testing is conducted to ensure that the developed project is bug free and it meets the
requirement specification. Finally developed project is installed and it is ready to
use.
1.1 MODULE DESCRIPTION
1.1.1 ADMIN MODULE
In this module admin can logged into the system and he/she can add the student,
teacher and parents to the database with more details which is required by the
system. He/she can also delete the detail he/she added to the database. He/she can
also see the feedback send by parents and students.
1.1.2 TEACHER MODULE
In this module teacher can login to the system with his email and password. He/she
can take the attendance of the particular class related to particular subject. He/she
can see the leave request sent from students and view the student attendance of
particular date.
1.1.3 STUDENT MODULE
In this module student can login to the system using the email and password. He/she
can sent the leave request to the teacher and can view the attendance of the particular
day.
1.1.4 PARENT MODULE
In this module parents can login to the system using the email and password. He/she
can see the attendance of their child on the particular day and also can give feedback
about their child and about the application.6
1.2 ANDROID STUDIO
We have used method “setOnClicklistner()” to perform appropriate action when a
button is pressed in application. We have used “menuInflater” to show or inflate
menu at top of activity. We have used “Value Event Listner “ for taking snapshot
of particular node of Database. We have tried to make as much as use of different
XML layouts like Textview , EditText , Spinners , AutoCompleteTextView , etc.
We have used ” Arraylist<String> “ to store snapshot value of nodes child and
“ArrayAdapter “to view arraylist in XML layouts and method like
“getText().toString()” to get selected string value. We have used various getters and
setters to store snapshot data in arraylist variable. We have made use of various
toasts to provide visual comments to user.
1.3 FIREBASE
Instead of to create table we have various nodes and child in Firebase Database.
Based on the key of each node we can create a relationship between different nodes
and perform database related tasks. the Child contains the values that we store or
retrieve from database.
Firebase Authentication provides backend services, easy-to-use SDKs, and readymade UI libraries to authenticate users to our app. It supports authentication using
passwords, phone numbers, popular federated identity providers like Google,
Facebook and Twitter, and more. We have used “FirebaseAuth” to authorize the
logging in user using email and password. Users pass these credentials to the
Firebase Authentication SDK. Firebase’s backend services will then verify those
credentials and return a response to the client Also with use of method “sigOut() “
users can log out users from system.
Public class DatabaseReference extends Query . A Firebase reference represents a
particular location in your Database and can be used for reading or writing data to
that Database location. We have used “DatabaseReference” to point or reference to
any node on Firebase Database. We have also used method remove() to remove
particular node from Firebase Database.7
Public class DataSnapshot extends Object. A DataSnapshot instance contains data
from a Firebase Database location. Any time you read Database data, you receive
the data as a DataSnapshot. DataSnapshots are passed to the methods in listeners
that you attach with addValueEventListener(ValueEventListener),
addChildEventListener(ChildEventListener) or
addListenerForSingleValueEvent(ValueEventListener). They are efficientlygenerated immutable copies of the data at a Firebase Database location.
