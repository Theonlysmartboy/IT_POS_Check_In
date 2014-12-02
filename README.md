IT_POS_Check_In
===============

A simple app to clock in and out of IT POS system that also shows a list of computers needing attention to employees. This is a class project for MSIS 4003 at Oklahoma State University that continues the work started for Geekabytes here [IT Point of Sale](https://github.com/RyanDawkins/it-point-of-sale)

A video demonstration of the baseline app functionality can be found bellow.

<div align="center">
  <a href="http://www.youtube.com/watch?feature=player_embedded&v=L51B-wW8wlk" target="_blank"><img         src="http://img.youtube.com/vi/L51B-wW8wlk/0.jpg" alt="Video Demonstration" width="640" height="360" border="10"   /></a>
<div/>

## Baseline Project Overview

### SYSTEMS PLANNING SECTION

#### PRIORITIES FOR SYSTEM AND PROJECT

1. The system must allow employees to clock in remotely using the mobile application for Android. The application must list what computer tickets are still active for the convenience and feasibility of Geekabytes employees.  
2. The data supplied to the employees must be accurate and simple in nature. The application will use a front facing graphic user interface that displays information from the clock-in server located in New York City. 
3. A Geekabytes employee should be able to clock-in to the application, and view which tasks or tickets they have open to complete for the day’s work. The employee will then be able to clock-out for lunch and at the end of the day.

#### Architecture for data, networks, hardware, and IS management 
- See page 5

#### EMPLOYEE WORK PLAN

1. Kyle Riedemann
 - Creation of the application
 - Development of the ERD.

2. Aaron Wright
  - Creation of the DFD.

3. Travis Dever
  - Creation of network architecture diagram
  - Language Support

4. Hali Deubler
  - Geekabytes research
  - Baseline Project Overview
  - Language Support

5. Keith Wright
  - Creation of System Scope Statement
  - Baseline Project Overview
  - Language Support

#### SYSTEM SCOPE AND FEASIBILITY
- See page 4

### SYSTEMS ANALYSIS

#### SYSTEM DESCRIPTION

1. Currently, if an offsite job is available, the employee must first drive to the repair shop in Stillwater, Oklahoma and then drive to the offsite location. This is costing Geekabytes, LLC extra expenses and costing their employees extra gas.

#### GENERAL RECOMMENDATIONS

1. An entity relationship diagram is attached on page ? The E-R diagram will store the data logged from Geekabytes, LLC.’s website as well as the application we are making in the database.

2. The application we are making has a simple design and allows the employees to clock in remotely. This application also shows the employees what computers are at the store waiting to be worked on.

3. When an employee clocks in, the application will talk to the database and update the information. 

4. At the store, when an employee enters notes about a computer, the database will update. After the data is entered, the application will show the current status of what needs done at the store.

#### SYSTEM JUSTIFICATION AND ALTERNATIVE SYSTEM POSSIBILITIES 

1. Clocking in at Geekabytes, LLC could include building a web application where the employees would use the web browser on any device to clock-in. 

2. The employees could call the secretary Arlene, and have her clock in for each employee. This system is composed of possible issues with the exception of employees that start offsite jobs before Geekabytes opens. 

3. The proposed Android application is the best solution at this time. All employees own Android phones and can download the free app. 

### SYSTEMS DESIGN

#### SPECIFICATION DETAILS

1. Screen Design (GUI)
2. Forms and Report Design
3. Logical Design
4. Conceptual Design
5. Database Design

#### ACQUISITION PLAN

1. To use this system all employees must download the application on their Android phone from the Google Play Store. 

### SYSTEMS IMPLEMENTATION AND OPERATION

#### Code
1. Kyle Riedemann coded the clock-in application for Geekabytes. The application will be hosted on the Google Play Store and its source code is located on Github at https://github.com/kylealanr/IT_POS_Check_In.

#### Documentation
1. Developer documentation will be located on Github. Information about the user facing application will be in the description on the Google Play Store once the app is uploaded.

#### Training procedures and support capabilities
1. No training is necessary for this application. The timesheet portion of the application has users update their timesheet in exactly the same manner as the desktop application used at the store. 

#### New versions or releases of software with associated updates to documentation, training, and support
1. New versions will be available on the Google Play Store and update notes will be included in the store description.

## App UI

![Clock in screenshot](/requirements/GUI Mockup/finished_check_in.png?raw=true "Design of the clock in screen")

![Computer list screenshot](/requirements/GUI Mockup/finished_computer_list.png?raw=true "Design of the list of computers needing immediate attention")

## Context Data Flow Diagram

This diagram shows everything involved in the flow of data through our system.

![Data Flow Diagram](/requirements/DFD/MSIS4003ProjectDFD.png?raw=true "DFD of entire system")

## ERD

The app uses a database built for IT POS, but only manipulates and displays a subset of entities.

![Entity Relationship Diagram](/requirements/ERD/ERD0-92.png?raw=true "")
