@echo off

SET CURRENTDIR="%~dp0"
call %CURRENTDIR%\project-setenv.bat

start cmd

exit