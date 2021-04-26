#GeoTaggrAPI

GeoTaggrAPI is REST API backend for the GeoTaggr Android App.
<br><br>
Usage:
<br>
1. Add an application.properties file in the resources directory with the following configurations<br>
```
spring.datasource.url=database-url
spring.datasource.username=database-username
spring.datasource.password=database-password
secretKey=secret-key
server.port=port-number
firebase.conf.path=path-to-firebase-admin-config-file
```
<br>
2. Build the project<br>

```
mvn clean package
```

<br>
3. Run the application container
