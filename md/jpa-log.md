## 简易版

```bash
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.properties.hibernate.use_sql_comments=false
logging.level.org.hibernate.SQL=debug
logging.level.org.hibernate.type.descriptor.sql=trace
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG
logging.level.org.springframework.jdbc.core.StatementCreatorUtils=TRACE
```

## 高级版

pom依赖:

```xml
<!--监控sql日志-->
        <dependency>
            <groupId>org.bgee.log4jdbc-log4j2</groupId>
            <artifactId>log4jdbc-log4j2-jdbc4.1</artifactId>
            <version>1.16</version>
        </dependency>
```

```bash
## 显示sql开关
spring.jpa.properties.hibernate.show_sql=true
## 格式化sql
spring.jpa.properties.hibernate.format_sql=true  
##
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
```


```yaml
spring:
  datasource:
    url: ${AI_CLOUD_DATASOURCE_URL:jdbc:log4jdbc:h2:mem:demo}
    username: ${AI_CLOUD_DATASOURCE_USERNAME:sa}
    password: ${AI_CLOUD_DATASOURCE_PASSWORD:123123}
    driver-class-name: ${AI_CLOUD_DATASOURCE_DRIVER:net.sf.log4jdbc.sql.jdbcapi.DriverSpy}
```



logback-spring-docker.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true">
    <!-- 从application.yml 中注入变量  -->
    <!-- <springProperty scope="context" name="LOG_PATH" source="log.home"/> -->
    <!-- <springProperty scope="context" name="APPDIR" source="spring.application.name"/> -->
    <property name="LOG_PATH" value="./logs"/>
    <property name="APPDIR" value="graceLogs"/>
 
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>1-%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger - %msg%n</pattern>
            <charset>GBK</charset>
        </encoder>
    </appender>
 
 
    <!-- error级别日志文件输出,按日期时间滚动记录输出 -->
    <appender name="FILEERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/${APPDIR}/log_error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH}/${APPDIR}/error/log-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>500MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <append>true</append>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>
            <charset>utf-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>error</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
    <!-- warn级别日志文件输出,按日期时间滚动记录输出 -->
    <appender name="FILEWARN" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/${APPDIR}/log_warn.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH}/${APPDIR}/warn/log-warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>2MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <append>true</append>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>
            <charset>utf-8</charset>        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
 
    <!-- info级别日志文件输出,按日期时间滚动记录输出 -->
    <appender name="FILEINFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/${APPDIR}/log_info.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH}/${APPDIR}/info/log-info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>2MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <append>true</append>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>
            <charset>utf-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger Line:%-3L - %msg%n</pattern>
        </encoder>
    </appender>
 
    <!--设置为OFF,即屏蔽; 留下sqltiming作为INFO级别输出-->
    <logger name="jdbc.connection" level="OFF"/>
    <logger name="jdbc.resultset" level="OFF"/>
    <logger name="jdbc.resultsettable" level="OFF"/>
    <logger name="jdbc.audit" level="OFF"/>
    <logger name="jdbc.sqltiming" level="INFO"/>
    <logger name="jdbc.sqlonly" level="OFF"/>
 
    <!--设置日志打印级别为INFO-->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILEINFO"/>
        <appender-ref ref="FILEWARN"/>
        <appender-ref ref="FILEERROR"/>
    </root>
 
</configuration>
```