CHARGING SESSION MANAGEMENT REST API

This project is developed for charging session management of one specific platform.
This is a spring boot application which includes some endpoints for some operations of this platform.
These operations are: create new session, change status of session, get all session list and get summary of session list.
Backend code is developed by considering some limitations.
The most critical part is complexity of  getting summary and creating new session. They should be in logn time.
So to meet these requirements I use tree map data structure to keep sessions. I distribute sessions by local date time and by this way I can reach the last minute sessions in logn time. Also creating a new session became logn time complexity.
But in this data structure, getting session by id became n time complexity. This is the disadvantage of this data structure. 

Code is developed by considering layered architecture. The layers are Controller, Service and Repository.
All these layers have test methods and test coverage of application is 96%.

BUILD AND RUN

This is maven application which uses spring boot. You can build and run application by using maven.
You can build the application by using 'mvn package' command.
After build you can run the jar file using 'java -jar charging-session-management-0.0.1-SNAPSHOT.jar' 

