@echo off
setlocal enabledelayedexpansion

rem define variables
set "appName=Crypto"
set "webappsPath=D:\ITU\Server\apache-tomcat-10.1.7\webapps\"
set "tempPath=Temp"
set "webPath=web"
set "indexFile=index.jsp"

rem suppression de temp
rmdir /s /q "%tempPath%"

rem re-création de temp
mkdir "%tempPath%"

rem architecture de temp
mkdir "%tempPath%\web"
mkdir "%tempPath%\WEB-INF"

rem copier de web, lib, web.xml, index.jsp
xcopy "%webPath%" "%tempPath%\web\" /E /Y /Q
xcopy "%indexFile%" "%tempPath%" /Q

rem archivage du contenu de Temp
pushd "%tempPath%"
jar cf "%appName%.war" *
popd

xcopy "%tempPath%\%appName%.war" %webappsPath% /Y

echo Deployment successfully done