## An example project with Spring Boot

### A note for curious colleagues viewing this repository

This project is not meant to implement all the best practices. It's for practice and for playing with some ideas.

I'm not using Flyway or Liquibase here. I'm trying to avoid fragmentation of SQL scripts during active development.
In this particular project, it's perfectly OK to drop and create all the tables from scratch.

Test Containers are awesome but it's not an option on the machine I'm currently using. Otherwise, I would definitely use it.
Currently, I'm using a local PostgreSQL. Not the best practice, but it works for me in this particular case.
