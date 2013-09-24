
SET CLASSPATH=./build/libs/SurvivorPrisonersDilemma.jar
SET CLASSPATH=%CLASSPATH%;./dist/groovy.jar

SET CLASSPATH=%CLASSPATH%;./dist/players/adamo-mosca/
SET CLASSPATH=%CLASSPATH%;./dist/players/andrew-habich-alex-romito/
SET CLASSPATH=%CLASSPATH%;./dist/players/ben-northrop/
SET CLASSPATH=%CLASSPATH%;./dist/players/james-mikesell/
SET CLASSPATH=%CLASSPATH%;./dist/players/javier-ochoa/
SET CLASSPATH=%CLASSPATH%;./dist/players/jeff-belback/
SET CLASSPATH=%CLASSPATH%;./dist/players/patrick-sharbaugh/

java -cp %CLASSPATH% com.summa.summit.spd.SurvivorPrisonersDilemma FrankDux Adamo.Mosca com.summa.summit.spd.Samuel j.m ElSanto JBelback.SpaghettiCoder Patrick.Sharbaugh