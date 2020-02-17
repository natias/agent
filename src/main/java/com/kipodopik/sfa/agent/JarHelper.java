package com.kipodopik.sfa.agent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarHelper {
	
	private final static String CLASS_FILE_NAME = "JarHelper.class";	
	private static final String JAR_PATH_PREFIX = "jar:";
	private static final String JAR_FILE_PATH_PREFIX = "jar:file:";
	private static final String CLASS_FILE_EXT = ".class";

	/**
	 * This method takes a class name, finds it inside the current JAR file and
	 * Returns it as a byte array.
	 * 
	 * Returns null if the class was not found.
	 */
	public static byte[] getClassAsBytes(String className) throws IOException {
		
		String thisJarPath = getJarFilePath();
		String classNameWithExt = className + CLASS_FILE_EXT;
		
		try ( JarFile jarFile = new JarFile(thisJarPath) ) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while ( entries.hasMoreElements() ) {
				JarEntry entry = entries.nextElement();
				
				if ( entry.getName().equals(classNameWithExt) ) {					
					return readClassFile( jarFile.getInputStream(entry) );					
				}
				
			}
		}
		
		return null;
		
	}
	
	private static byte[] readClassFile(InputStream is) throws IOException {
		byte[] content = new byte[is.available()];		
		is.read(content);
		is.close();
		return content;		
	}

	/**
	 * Finds and returns current JAR file name 
	 */
	public static String getJarFilePath() {
				
		String classFilePath = JarHelper.class.getResource( CLASS_FILE_NAME ).toString();
		
		if ( !classFilePath.startsWith(JAR_PATH_PREFIX) ){
			return null;
		}
		
		String jarName = classFilePath.substring( 
				classFilePath.indexOf(JAR_FILE_PATH_PREFIX) + JAR_FILE_PATH_PREFIX.length(), 
//				0,
				classFilePath.indexOf("!") );		
		return jarName;
		
	}
	
}
