@echo off
echo [INFO] build and install modules.
cd ..\
call mvn clean install -Dmaven.test.skip=true
pause