# Contacts
This application is built using Java Spring boot, PostgreSQl, and Redis.
Its deployed on AWS EC2 instance using Docker, also PostgreSQL and Redis are being installed using the docker images form docker hub.
Please find below Dependencies, deployment process, and work through on some of the key aspect of the project.
<pre>
*Deployed Version on AWS: https://35.171.62.145:8088/api/<endpoint>
</pre>
<pre>
*Deployed Version API Documentation: https://35.171.62.145:8088/swagger-ui.html
</pre>
<pre>
*Note this project runs on port: 8088
</pre>
<pre>
*Incase you want to quickly access endpoint with documentation visit 127.0.0.1:8088/swagger-ui.html on your browser after running the project.
</pre>
## Dependencies/technologies required
-Java development kit(Jdk) 17
-Netbeans or Intellij IDE.
-Postgres.
-Redis.
-Postman(Optional).

## Installation Guid
During my development process i took advantage of Docker Desktop to quickly install Redis, on Postgres my personal computer at home.
you do not need docker to run this application successfully.
- Install JDK17 if you dont have. Dont forget to save java installation Path in you inviroment variable.

- Install Netbeans(Preferably) or Intellij on you pc

- Make you have Redis running on port: 6379 and Postgres on port: 5432. Restore the contact DB available in document shared.

- Open the project in your prefered IDE and build the project using maven.

- Run the application

- API end point is: 127.0.0.1:8088 

## End points.
We have two end points and we used Basic Authentication.
End points and documentation can be found by visiting http://127.0.0.1:8088/swagger-ui.html 
### Authentication 
![Capture](https://user-images.githubusercontent.com/20817089/166673154-2cc3cc4d-5fc4-4f5c-a71d-9a6ca5112431.PNG)
The above image shows the username and password field need for our API authentication. To access the authentication form on postman, choose the authorization tab, and select Basic Auth.
### Inbound SMS
Set method to POST - and enter the following url
http://127.0.0.1:8088/api/inbound/sms

Payload
{
  "from":"0982372398",
  "to":"47343892392",
  "text":"STOP"
}

### Outbound SMS
Set method to POST - and enter the following url
http://127.0.0.1:8088/api/outbound/sms

Payload
{
  "from":"0982372398",
  "to":"47343892392",
  "text":"Hello India."
}


