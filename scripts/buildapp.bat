@ECHO OFF

rem ------ ENVIRONMENT --------------------------------------------------------
rem The script depends on various environment variables to exist in order to
rem run properly. The java version we want to use, the location of the java
rem binaries (java home), and the project version as defined inside the pom.xml
rem file, e.g. 1.0-SNAPSHOT.
rem
rem PROJECT_VERSION: version used in pom.xml, e.g. 1.0-SNAPSHOT

rem set SDK paths
set PWIN="C:\Users\Florian\Documents\workspace\jar & docs\JDK\zulu15.28.13-ca-fx-jdk15.0.1-win_x64"
set PNUX="C:\Users\Florian\Documents\workspace\jar & docs\JDK\zulu15.28.13-ca-fx-jdk15.0.1-linux_x64"
set PMAC="C:\Users\Florian\Documents\workspace\jar & docs\JDK\zulu15.28.13-ca-fx-jdk15.0.1-macosx_x64\zulu-15.jdk\Contents\Home"


rem APP_VERSION: the application version, e.g. 1.0.0, shown in "about" dialog
set APP_VERSION=0.0.1

rem Set the project dir (scripts -1)
set PROJECT=..
rem project compiled jar path
set MAIN_JAR=out\artifacts\PynetemGui\PynetemGui.jar
rem project main class location (in packages)
set ENTRYPOINT=v2.vue.entrypoint.DemoApp
set APPNAME=PynetEmGui
rem Set the output dir (scripts -1\builds)
set TARGET=..\builds
set TARGETLEVEL=..\

set JAVA_VERSION=15



rem Set desired installer type: "app-image" "msi" "exe".
set INSTALLER_TYPE_WIN=app-image
set INSTALLER_TYPE_NUX=app-image
set INSTALLER_TYPE_MAC=app-image



rem ------ SETUP DIRECTORIES AND FILES ----------------------------------------
rem Remove previously generated java runtime and installers. Copy all required
rem jar files into the input/libs folder.

IF EXIST %TARGET%\java-runtime rmdir /S /Q  .\%TARGET%\java-runtime
IF EXIST %TARGET%\java-runtimeWin rmdir /S /Q  .\%TARGET%\java-runtimeWin
IF EXIST %TARGET%\java-runtimeNux rmdir /S /Q  .\%TARGET%\java-runtimeNux
IF EXIST %TARGET%\java-runtimeMac rmdir /S /Q  .\%TARGET%\java-runtimeMac
IF EXIST %TARGET%\installer rmdir /S /Q %TARGET%\installer

xcopy /S /Q %PROJECT%\lib\* %TARGET%\installer\input\libs\
copy %PROJECT%\%MAIN_JAR% %TARGET%\installer\input\libs\

rem ------ REQUIRED MODULES ---------------------------------------------------
rem Use jlink to detect all modules that are required to run the application.
rem Starting point for the jdep analysis is the set of jars being used by the
rem application.

echo +-----------------detecting required modules------------------------------

  %JAVA_HOME%\bin\jdeps ^
  -q ^
  --multi-release %JAVA_VERSION% ^
  --ignore-missing-deps ^
  --class-path %TARGET%\installer\input\libs ^
  --print-module-deps %PROJECT%\%MAIN_JAR% > temp.txt

set /p detected_modules=<temp.txt
del temp.txt
echo +-------------------------------------------------------------------------


echo +-----------------detected modules----------------------------------------
echo %detected_modules%
echo +-------------------------------------------------------------------------

rem ------ MANUAL MODULES -----------------------------------------------------
rem jdk.crypto.ec has to be added manually bound via --bind-services or
rem otherwise HTTPS does not work.
rem
rem See: https://bugs.openjdk.java.net/browse/JDK-8221674

set manual_modules=jdk.crypto.ec

echo +-----------------manual modules------------------------------------------
echo %manual_modules%
echo +-------------------------------------------------------------------------

rem ------ RUNTIME IMAGE ------------------------------------------------------
rem Use the jlink tool to create a runtime image for our application. We are
rem doing this is a separate step instead of letting jlink do the work as part
rem of the jpackage tool. This approach allows for finer configuration and also
rem works with dependencies that are not fully modularized, yet.


echo +-----------------creating java runtime image-----------------------------

echo runtime image WINDOWS
call %JAVA_HOME%\bin\jlink ^
--no-header-files ^
--compress=0 ^
--add-modules %detected_modules%,%manual_modules% ^
--no-man-pages ^
--module-path %PWIN%\jmods ^
--output %TARGET%/java-runtimeWin

echo runtime image LINUX
call %JAVA_HOME%\bin\jlink ^
--no-header-files ^
--compress=0 ^
--add-modules %detected_modules%,%manual_modules% ^
--no-man-pages ^
--module-path %PNUX%\jmods ^
--output %TARGET%/java-runtimeNux

echo runtime image OSX
call %JAVA_HOME%\bin\jlink ^
--no-header-files ^
--compress=0 ^
--add-modules %detected_modules%,%manual_modules% ^
--no-man-pages ^
--module-path %PMAC%\jmods ^
--output %TARGET%/java-runtimeMac
echo +-------------------------------------------------------------------------


rem ------ PACKAGING ----------------------------------------------------------
rem In the end we will find the package inside the target/installer directory.


echo +-----------------packaging app-------------------------------------------
set TARGET=%TARGET:\=/%
set TARGETLEVEL=%TARGETLEVEL:\=/%

echo making windows app ....
call %JAVA_HOME%\bin\jpackage ^
  --type %INSTALLER_TYPE_WIN% ^
  --dest %TARGET%/installer/win ^
  --input %TARGET%/installer/input/libs ^
  --name %APPNAME% ^
  --main-class %ENTRYPOINT% ^
  --main-jar %TARGETLEVEL%../../../%MAIN_JAR% ^
  --java-options -Xmx2048m ^
  --runtime-image %TARGET%/java-runtimeWin ^
  --app-version %APP_VERSION%

echo making linux app ....
  call %JAVA_HOME%\bin\jpackage ^
    --type %INSTALLER_TYPE_NUX% ^
    --dest %TARGET%/installer/nux ^
    --input %TARGET%/installer/input/libs ^
    --name %APPNAME% ^
    --main-class %ENTRYPOINT% ^
    --main-jar %TARGETLEVEL%../../../%MAIN_JAR% ^
    --java-options -Xmx2048m ^
    --runtime-image %TARGET%/java-runtimeNux ^
    --app-version %APP_VERSION%

echo making osx app ....
    call %JAVA_HOME%\bin\jpackage ^
      --type %INSTALLER_TYPE_MAC% ^
      --dest %TARGET%/installer/mac ^
      --input %TARGET%/installer/input/libs ^
      --name %APPNAME% ^
      --main-class %ENTRYPOINT% ^
      --main-jar %TARGETLEVEL%../../../%MAIN_JAR% ^
      --java-options -Xmx2048m ^
      --runtime-image %TARGET%/java-runtimeMac ^
      --app-version %APP_VERSION%
rem      --win-dir-chooser ^
rem      --win-shortcut ^
rem      --win-per-user-install ^
rem      --win-menu
echo +-------------------------------------------------------------------------
