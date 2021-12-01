package android.events.dg;

import org.eclipse.core.runtime.Platform;


public class Utility {
	private static final String metamodelsFolder = "metamodels/";
	private static final String resourcesFolder = "res/";
	private static final String ModelsFolder = "Models/";
	
	
	
	public static final String JAVA_METAMODEL_PATH = "http://www.eclipse.org/MoDisco/Java/0.2.incubation/java";
	public static final String XML_METAMODEL_PATH = "http://www.eclipse.org/MoDisco/Xml/0.1.incubation/XML";
	public static final String GNERATE_METAMODEL_PATH = getLocation() + metamodelsFolder + "EventProduction.ecore";
	public static final String DISCOVERY_METAMODEL_PATH = getLocation() + metamodelsFolder + "EventSource.ecore";
	
	public static final String ANDROID_DESCOVERY ="C:\\Users\\hayyan\\Desktop\\Project\\android.events.DG\\Transformations\\androidextractor.asm";
	public static final String ANDROID_DESCOVERY1 =ANDROID_DESCOVERY.replace("file:/", "");
	
	public static final String ANDROID_GENERATING ="C:\\Users\\hayyan\\Desktop\\Project\\android.events.DG\\Transformations\\androidATLGenerate.asm";
	public static final String ANDROID_GENERATING1 =ANDROID_GENERATING.replace("file:/", "");
	

	public static final String getLocation() {	
		String t = Platform.getBundle(Activator.PLUGIN_ID).getLocation().replaceFirst("reference:", ""); 
		return t ;
	}

}
