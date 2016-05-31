package com.classcastexception.commitacceptance;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class RestClient {
	public String getDataFromServer(String path, String username, String password) {
		try {
			URLConnection urlConnection = getURLWithAuthentication(new URL(path), username, password);
			return Tools.read(urlConnection.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private URLConnection getURLWithAuthentication(URL url, String username, String password) throws IOException {
		URLConnection urlConnection = url.openConnection();
		String authString = username + ":" + password;
		String authStringEnc = new String(Base64.getEncoder().encode(authString.getBytes()));
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return urlConnection;
	}
}