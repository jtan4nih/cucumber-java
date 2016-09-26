call setenv.bat

:mvn test
java -classpath .;lib\maven-jar-with-dependencies.jar;target\classes skeleton.RunMeFirst %1