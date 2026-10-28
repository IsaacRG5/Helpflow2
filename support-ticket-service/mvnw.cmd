@echo off
setlocal
set MAVEN_VERSION=3.9.9
set MAVEN_HOME=%USERPROFILE%\.maven\apache-maven-%MAVEN_VERSION%
if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  echo Maven %MAVEN_VERSION% no encontrado. Descargando Apache Maven...
  set ZIP=%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ProgressPreference='SilentlyContinue'; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip'; Expand-Archive -Force '%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip' '%USERPROFILE%\.maven'"
  if errorlevel 1 exit /b 1
)
call "%MAVEN_HOME%\bin\mvn.cmd" %*
