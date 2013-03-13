As the name suggests there is a relationship between Autumn and Spring. Autumn is

 * inspired by the successes of spring,
 * not trying to compete with spring,
 * motivated by the limitations of spring (i.e. pressure to maintain backwards compatibility)

Autumn is an alternative to the "traditional" java web/rest stack. Autumn is a helpful, opinionated, configurable, light web stack. Autumn fuses technologies together making programming fast and fun again. Autumn hopes to be as clean, crisp and cool as the autumn air.

Here is a comparison of a traditional web stack vs what the Autumn stack may look like

| Specification   | Typical Stack       | Autumn Goodness          |
|-----------------|---------------------|--------------------------|
| JSR- 47 Logging | log4j               | logback/slf4j            |
| JSR-222 JAXB    | jackson             | jackson or gson*         |
| JSR-243 JDO2    |                     | datanucleus              |
| JSR-250         | spring-security     | apache shiro             |
| JSR-303 V8n     | hibernate-validator | hibernate or datanucleus |
| JSR-311 JAX-RS  | spring-webmvc*      | jersey                   |
| JSR-315 Servlet | tomcat              | jetty 9                  |
| JSR-317 JPA2    | hibernate           | datanucleus              |
| JSR-330 DI      | spring-core         | guice                    |

_*=does not conform to the spec but fills the same space_

TODO: see if we can migrate these lists below up to the table above

A traditional web stack might include:

  * Spring-core
  * Spring-webmvc
  * Jackson
  * Log4j
  * Tomcat
  * many Apache commons utils
  * Hibernate
  * Spring-security
  * Junit3
  * Spring-batch

The Autumn web stack may attempt:

  * Guice | GuicyFruit
  * Jersey
  * Gson
  * Logback/slf4j
  * Jetty 9 (on java 7)
  * Guava, Lombok, joda-time
  * Spring-data | jdbi | Gora 
  * Apache shiro
  * junit4+hamcrest
  * Quartz

Goals:

  * Sensical configuration (i.e. annotations) over ambiguous convention
  * run on GAE, heroku, cloudfoundry, openshift with minimal to no special config
  * Use annotation only servlet specs (@WebServlet, @WebFilter)
  * Support easily being built with maven, ant, gradle
  * no Autumn XML, use json where Autumn config is needed. 
    * however if a lib we are using already uses XML config then for the sanity of the user we will encourage that libs native config. the user needs to be able to read the docs of the underlying library and make changes as needed
  * produce a simple straight up war
  * able to easily include container (jetty-runner?) for heroku
  * No need for DTOs, models/pojos can be serialized into JSON (sorry hibernate)

