# thesis
# node is a bitch
# for node install
mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v10.16.0"

User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

CustomUserDetails userDetails =
(CustomUserDetails) SecurityContextHolder
.getContext()
.getAuthentication()
.getPrincipal();


for production uncomment this:
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-tomcat</artifactId>
<scope>provided</scope>
</dependency>


mvn compile
vaadin:prepare-frontend
vaadin:build-frontend
mvn package