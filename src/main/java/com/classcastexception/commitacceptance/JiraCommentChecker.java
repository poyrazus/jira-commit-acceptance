package com.classcastexception.commitacceptance;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JiraCommentChecker {
	private static final String JIRA_REST_API_SEARCH_URL = "/rest/api/2/search?jql=";
	private RestClient restClient = new RestClient();
	public String author;
	public String comment;
	public String jiraURL;
	public String projectKeys;
	public String jiraUsername;
	public String jiraPassword;
	public boolean issuesMustBeUnresolved;

	public JiraCommentChecker(String author, String log, String jiraURL, String projectKeys, String jiraUsername,
			String jiraPassword, boolean issuesMustBeUnresolved) {
		this.author = author;
		this.comment = log;
		this.jiraURL = jiraURL;
		this.projectKeys = projectKeys;
		this.jiraUsername = jiraUsername;
		this.jiraPassword = jiraPassword;
		this.issuesMustBeUnresolved = issuesMustBeUnresolved;
	}

	public void acceptCommitByJiraIssueID() {
		if (this.comment == null || this.comment.trim().length() == 0) {
			Tools.exitWithError("Commit message cannot be empty", 30);
		}

		StringBuilder query = new StringBuilder(this.jiraURL).append(JIRA_REST_API_SEARCH_URL);
		query.append("project in (").append(this.projectKeys).append(")");
		query.append(" and ");
		query.append("assignee=").append(this.author);

		query.append(" and key in (");
		String[] projects = this.projectKeys.split(",");
		List<String> issueList = fetchIssueIDsInTheCommentByUsingProjectKeys(projects);
		if (issueList.size() == 0) {
			Tools.exitWithError("Commit message doesn't contain any issue ID", 40);
		}

		query.append(String.join(",", issueList)).append(")");
		if (this.issuesMustBeUnresolved) {
			query.append("and (resolution=Unresolved)");
		}
		query.append("&fields=id,key");

		String restQueryResult = restClient.getDataFromServer(query.toString().replaceAll(" ", "+"), this.jiraUsername, this.jiraPassword);
		int indexOf = restQueryResult.indexOf("\"total\":");
		if (indexOf == -1) {
			Tools.exitWithError("Unexpected result from JIRA server!", 50);
		}
		String count = restQueryResult.substring(indexOf + 8, restQueryResult.indexOf(",", indexOf));
		if (Integer.parseInt(count) <= 0) {
			Tools.exitWithError("No project accepts this commit or the issues doesn't exits/are resolved!", 60);
		}
	}

	private List<String> fetchIssueIDsInTheCommentByUsingProjectKeys(String[] projects) {
		List<String> issueList = new ArrayList<>();
		for (String project : projects) {
			Pattern pattern = Pattern.compile("\\b(?:" + project + ")\\b-\\d+");
			Matcher m = pattern.matcher(this.comment);
			while (m.find()) {
				issueList.add(m.group());
			}
		}
		return issueList;
	}

}
