# pic-video-mgr
A demo for managing image files and video files based on Spring Boot
## [Interface document](https://docs.qq.com/doc/DQmRBR2V4dmVFYmJP)
## Third Party Modules
* org.bytedeco.javacv-platform
  ```xml
  <dependency>
      <groupId>org.bytedeco</groupId>
      <artifactId>javacv-platform</artifactId>
      <version>1.3.1</version>
  </dependency>
  ```
* org.mybatis.spring.boot.mybatis-spring-boot-starter
  ```xml
  <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.1.1</version>
  </dependency>
  ```
* org.projectlombok.lombok
  ```xml
  <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
  </dependency>
  ```
* mysql.mysql-connector-java
  ```xml
  <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <scope>runtime</scope>
  </dependency>
  ```
## Properties Definition
You can create a file named *application.yaml* in *src/main/resources* with content as below：
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 100MB            # 单个文件大小
      max-request-size: 500MB         # 单次提交总文件大小
  mvc:
    static-path-pattern: "/static/**" # 静态资源prefix
  web:
    resources:
      static-locations: "file:/upload-files/" # 静态资源磁盘路径
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: your_mysql_host_url
    username: your_mysql_username
    password: your_mysql_password
upload-cfg:
  pic-path: "/upload-files/pics/"     # 图片保存磁盘路径
  video-path: "/upload-files/videos/" # 视频保存磁盘路径
url-cfg:
  pic-url: "http://127.0.0.1:8080/static/pics/"     # 图片url prefix
  video-url: "http://127.0.0.1:8080/static/videos/" # 视频url prefix
```
## How to deploy
Required services:
1. MySQL8.0.21 or latter 
2. Maven3.6.3 or latter
3. JDK1.8 or latter

Command to deploy:
1. `cd pic-video-mgr/`
2. `mvn clean`
3. `mvn install`
4. `nohup java -Dloader.path=./lib -jar fileupload-0.0.1-SNAPSHOT.jar &`
