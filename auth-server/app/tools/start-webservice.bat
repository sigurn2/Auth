@echo off
echo [INFO] start webservice
cd ..\
cd target\
call java -jar si-syjm-authserver-1.0.0-SNAPSHOT.jar --spring.profiles.active=ws,webservice,security-captcha > log.log
pause