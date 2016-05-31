package com.classcastexception.commitacceptance;

/**
 * Main class
 *
 */
public class MainApp {
	public MainApp(String[] args){
		SvnInfoFetcher svnInfoFetcher = new SvnInfoFetcher(args[2], args[0], args[1]);
		String author = svnInfoFetcher.getSvnInfo("author");
		String log= svnInfoFetcher.getSvnInfo("log");

		new JiraCommentChecker(author, log, args[3], args[4], args[5] ,args[6], Boolean.parseBoolean(args[7])).acceptCommitByJiraIssueID();
	}

	public static void main(String[] args) {
		if (args.length != 8) {
			System.err.println(
					"USAGE:\n\tjava -jar jira-commit-acceptance.jar %1 %2 <svnlook-path> <jira-url> <project-keys> <jira-username> <jira-password> <unresolved>\n");
			System.err
					.println("%1 and %2: \tRepository path and revision number, respectively. Comes from commit hook");
			System.err.println("svnlook-path: \tPath to the svnlook.exe file");
			System.err.println("jira-url: \tBase URL of the JIRA (e.g., http://192.168.10.10/jira)");
			System.err.println(
					"project-keys: \tComma-separated list of project keys accepting commits (e.g., PRJ or PRJ1,PRJ2)");
			System.err.println(
					"jira-username: \tJIRA user to query issues. Must be able to query related projects (e.g., eric.vals)");
			System.err.println(
					"jira-password: \tJIRA password of the user. Basic authentication is made, password may be visible through the network.");
			System.err.println("unresolved: \tWhether the issues must be unresolved or not. true or false");
			System.err.println("Wrong usage of the jar file!");
			System.exit(10);
		} else {
			new MainApp(args);
		}
	}


}
