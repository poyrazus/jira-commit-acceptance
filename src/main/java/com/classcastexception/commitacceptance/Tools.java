package com.classcastexception.commitacceptance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Tools {
	public static String read(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}

	public static void exitWithError(String errorMessage, int errorCode){
		System.err.println(errorMessage);
		System.exit(errorCode);
	}
}
