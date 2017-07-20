@echo off

set JAVA_HOME=\Program\jdk8
set M2_HOME=\Program\maven3
set ANT_HOME=\Program\ant1.9
set GRADLE_HOME=\Program\gradle

set BASH_HOME=\Program\git2

set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;%ANT_HOME%\bin;%GRADLE_HOME%\bin;%PATH%

cd %CURRENTDIR%\..