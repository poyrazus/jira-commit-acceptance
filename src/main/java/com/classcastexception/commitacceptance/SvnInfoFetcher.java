package com.classcastexception.commitacceptance;

import java.io.IOException;

public class SvnInfoFetcher {
	String svnlookPath;
	String repositoryPath;
	String transaction;

	public SvnInfoFetcher(String svnlookPath, String repositoryPath, String transaction) {
		this.svnlookPath = svnlookPath;
		this.repositoryPath = repositoryPath;
		this.transaction = transaction;
	}

	public String getSvnInfo(String operation) {
		String result ="";
		try {
			Process process = new ProcessBuilder(svnlookPath, operation, repositoryPath, "-t", transaction).start();
			process.waitFor();
			result = Tools.read(process.getInputStream());
			System.err.println(Tools.read(process.getErrorStream()));
		} catch (IOException | InterruptedException e) {
			System.err.println("Unable to get " + operation + " message with svnlook");
			System.exit(30);
		}
		return result;
	}
}
