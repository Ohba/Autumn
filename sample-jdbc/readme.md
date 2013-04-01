Want to use the Autumn Stack to liberate your web development?

# add `autumn-core` dependency to your `pom.xml`

```xml
<repositories>
    <repository>
        <id>ohba</id>
        <url>https://bitbucket.org/ohba/autumn/raw/master/repo/</url>
    </repository>
</repositories>
<dependency>
    <groupId>co.ohba.autumn</groupId>
    <artifactId>autumn-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

# write a controller method
# # annotated with `@Path` and `@GET`
# # and returning a POJO
# write an `autumn.json` telling Autumn where to find your `@Path`s
```json
{
    pathPackage: 'com.exmaple.pacakge.to.scan'
}
```
Thats it! and because your so awesome you are already compiling with *Java 7*
and launching your wars in a *Servlet 3.0* container (Tomcat 7 or our favorite Jetty 9).
When you access your `@Path` in a web browser you'll see your POJO returned as JSON.

Want to put some HTML/JS/CSS on your server? 

# add static files to ./src/main/webapp/public/ 

Want to add persistence? It's easy since Autumn is using JPA.

# annotate your POJO with `@Entity`
# `@Inject` into your controller (or anywhere really) an `EntityManager`
# add to `autumn.json` your jdbc info
# # jdbc driver class
# # jdbc connection string
# # user and password
```json
{
    pathPackage: 'com.exmaple.pacakge.to.scan',
    entityPackage: 'com.exmaple.pacakge.with.entities',
    
    jdbc: {
        driver: 'org.database.drive',
        url:'http://www.connectionstring.com',
        user:'autumnUser',
        password:'superSecretPassword'
    }
}
```
Bam! Now you can `.persist()` your `@Entity` into your `EntityManager`. That was sweet.

other topics to discuss:
* project lombok