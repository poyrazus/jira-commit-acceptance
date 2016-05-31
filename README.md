# JIRA 7 SVN Commit Acceptance Hook
Require SVN commits have commit messages. Also, these commit messages must have valid JIRA issue ID's in it.

I was using Commit Acceptance Plugin for JIRA (till JIRA version 7) as SVN precommit hook. But when upgraded to JIRA 7, learned that Commit Acceptance plugin is no longer supported (because XML-RPC API is dropped in JIRA). Instead, there is a commercial version (using REST API). So, I wanted to create my own hook script.

To use this hook, first build the project using maven. Then, go to SVN repository folder (you must have access to the original repository, not the working copy). Put *pre-commit.bat* and **jira-commit-acceptance.jar** (generated via maven build) under the hooks folder.
In the pre-commit.bat, set the parameters accordingly (All must be filled). The parameters are:
- **java-exe** Path to java executable in your system (java 8 is required)
- **svnlook-path** Path to svnlook.exe file (Required to make svn queries to get the committer name and commit log) 
- **jira-url** JIRA base URL. Queries are made against this URL
- **project-keys** Comma-separated list of JIRA project key(s) accepting commit
- **jira-username** JIRA user to make JIRA queries for the isues
- **jira-password** Password of the JIRA user
- **unresolved** Whether the issues in th commit message should be unresolved or not. true or false

Example inputs are provided in the pre-commit.bat

##Notes
- I used java 8, thus you need java 8 to build and run this project (Infact, just a single line uses java 8 which have *String.join* method in it). 
- To use no-dependency at all, I didn't use any REST client and aldo didn't use a JSON object handler (made String operations instead).  It may be modified to use a REST client and also a JSON object handler.
- It also makes a Basic authentication. Thus, passwords may be visible through the network.
- Because I had problem during git push and it reset all my local files, I recovered from Eclipse local history. Thus, there may be some mistakes in the code (it compiles, I checked :))
