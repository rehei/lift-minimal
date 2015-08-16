# Overview

This project aims to providing a) a base for initial project setup and 
b) a software development process definition. So for creatiung a new project,
you may just fork (or copy) this project. Build, Deployment and IDE-Setup can be
 handled using the provided SBT build tool.


# IDE-Setup (Eclipse)

## Get Scala-IDE

```
http://scala-ide.org/
```


## Generating project files

Just generate the Eclipse project files using. Pleas enote this command will 
also fetch all necessary sources from third-party libraries.

```
$ ./sbt eclipse
```

## Running

Just start Tomcat using a run configuration. Please note, that within this 
repository there is a run configuration attached,that could be used. But you
could also create your own using the following configuration:

- Main-Class: `webapp.runner.launch.Main`
- Programm-Arguments: `./bin/ --path / --port 10080`
- VM-Arguments (Optional): `-Drun.mode=production`

Please note that you can start the application in Debug mode that allows for 
code changes during application runtime. 

# Quick-Start from Console

Start the SBT-Shell, start Tomcat7 (and stop it if needed) 
```
$ ./sbt
> tomcat7:start
> tomcat7:stop
```

# Dependencies

## Java

Dependencies can be managed using the build.sbt file. Please not that you can 
reference files from common library repositories, e.g. mvnrepository.com.

You can also reference libraries using URLs. 

Have a look at built.sbt for more detailed information. 

## JavaScript

JavaScript libraries are managed using WebJars. WebJars package JavaScript 
libraries into Jars. Therefore you can use common java dependency management 
processes for managing JavaScript libraries.

# Testing

Testing can be handled from IDE or using 
```
$ ./sbt test
```

# Logging

Logging is handled via SLF4J and Logback. Configuration can be handled via  

```
/src/main/resources/logback.xml
```


# Deployment

There are several options that you can use for deployment

## Create a WAR file

```
$ ./sbt package 
```

## Create a WAR file, ship it with Tomcat7 for Windows & Linux including JRE8

Please note that within the generated bundle there are additional .bat scripts 
provided for easy service installation on Windows. 

Deployment is handled using a bash script. Therefore you need (something like) 
Linux to run the script.

```
$ ./deploy.sh
```

# Open Issues

- Manage Third-Party-Libraries licenses
- Put webjars-serving logic into a lift plugin?
- Put Tomcat7 bundle deployment logic into a sbt plugin? 
