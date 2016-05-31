REM JIRA commit acceptance java client for SVN
REM Author: poyrazus@gmail.com
REM 20160531
REM www.classcastexception.com

set java-exe=C:\Tools\jdk1.8.0_92\jre\bin\java.exe
set svnlook-path="C:\Program Files\TortoiseSVN\bin\svnlook.exe"
set jira-url=http://192.168.10.10/jira
set project-keys=PRJ1,PRJ2
set jira-username=jira.user
set jira-password=12345678

@%java-exe% -jar %1\hooks\jira-commit-acceptance.jar %1 %2 %svnlook-path% %jira-url% %project-keys% %jira-username% %jira-password% true

IF ERRORLEVEL 1 (
ECHO. 1>&2
ECHO Commit is unsuccessfull! 1>&2
EXIT %ERRORLEVEL%
)
EXIT 0