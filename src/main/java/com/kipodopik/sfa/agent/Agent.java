package com.kipodopik.sfa.agent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <b>This agent is designed to replace JRE classes during runtime. </b><br/><br/>
 * It intercepts classloader calls and replaces the classes listed in the classesToReplace List.<br/>
 * Written by Shay Artzi (shay@artzi.org), August 2018<br/><br/>
 * 
 * This software is provided as-is, as a proof-of-concept only.<br />
 * <b><i>Do not use this code in a production environment!</i></b>
 */
public class Agent {
	
	private static List<String> classesToReplace;
	
	static {
		classesToReplace = new ArrayList<>();
		classesToReplace.add("sun/font/FontDesignMetrics$KeyReference");
		classesToReplace.add("sun/font/FontDesignMetrics$MetricsKey");
		classesToReplace.add("sun/font/FontDesignMetrics");
		classesToReplace.add("javax/swing/text/TextLayoutStrategy$AttributedSegment");
		classesToReplace.add("javax/swing/text/TextLayoutStrategy");

	}
	
	public static void premain(String args, Instrumentation instrumentation) {
		
		printMsg("premain executed");
				
		instrumentation.addTransformer( new ClassFileTransformer() {
			
			@Override
			public byte[] transform(
					ClassLoader loader, 
					String className, 
					Class<?> classBeingRedefined,
					ProtectionDomain protectionDomain, 
					byte[] classfileBuffer) throws IllegalClassFormatException {
								
				if ( classesToReplace.isEmpty() ) {					
					return null;
				}
				
				Iterator<String> iterator = classesToReplace.iterator();
				
				//If the class request is found on the list, replace and remove from list
				while ( iterator.hasNext() ) {
					String classToReplace = iterator.next();
					if ( className.equals(classToReplace) ) {						
						try {							
							iterator.remove();
							System.out.println("instrumenting class "+classToReplace);
							return JarHelper.getClassAsBytes(classToReplace);
						} catch (IOException e) {						
							printMsg("Failed replacing class: " + classToReplace);
							e.printStackTrace();
						}
						
						return null;						
					}
					
				}
								
				return null;
								
			}
		} );
	}
	
	private static void printMsg(String msg) {
		System.out.println("[Agent] " + msg);
	}
	
}
