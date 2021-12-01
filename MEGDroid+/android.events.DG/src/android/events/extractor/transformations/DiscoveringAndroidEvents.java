package android.events.extractor.transformations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.core.service.LauncherService;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmt.modisco.java.ThisExpression;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFReferenceModel;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

//import org.eclipse.m2m.atl.engine.AtlLauncher;

import android.events.dg.Utility;

public class DiscoveringAndroidEvents {
	
	private ILauncher launcher;
	private IProgressMonitor ip;
	protected IModel outModel;
	private Properties properties;
	
	
	private String javaModelPath;
	private String AndroidManifestModelPath;
	private String AndroidEventsPath;
	
	public DiscoveringAndroidEvents(String javaPath, String AndroidManifestDescriptorPath, String AndroidEventsPath) {
		this.javaModelPath = javaPath;
		this.AndroidManifestModelPath = AndroidManifestDescriptorPath;
		this.AndroidEventsPath = AndroidEventsPath;
	}

	public void run() throws IOException, ATLCoreException {
		
		this.launcher = new EMFVMLauncher();
		this.ip = new NullProgressMonitor();
		IExtractor extractor = new EMFExtractor();
		ModelFactory modelFactory = new EMFModelFactory();
		ModelFactory factory = new EMFModelFactory();
		IInjector injector = new EMFInjector();
	
	//	IModel ResultModel_Total = modelFactory.newReferenceModel(Utility.ANDROID_SECURITY_METAMODEL_PATH);(Utility.ANDROID_SECURITY_METAMODEL_PATH);
		
		Map<String,String> inModels = new HashMap<String,String>();
		inModels.put("IN", "XML");
		inModels.put("IN1", "java");
		
		Map<String, String> outModels = new HashMap<String, String>();
		outModels.put("OUT", "EventsDesc");
		
		
		Map<String,String> paths = new HashMap<String,String>();
		paths.put("IN", URI.createFileURI(this.AndroidManifestModelPath).toString());
		paths.put("IN1", URI.createFileURI(this.javaModelPath).toString());
		paths.put("OUT", URI.createFileURI(this.AndroidEventsPath).toString());
		//added
		EcorePackage.eINSTANCE.eResource();
		
		paths.put("java", Utility.JAVA_METAMODEL_PATH);
		paths.put("XML", Utility.XML_METAMODEL_PATH);
		paths.put("EventsDesc", Utility.DISCOVERY_METAMODEL_PATH);
		
		Map<String, Object> options = new HashMap<String, Object>();

	
		URL transformation= this.getClass().getResource(Utility.ANDROID_DESCOVERY);
		LauncherService.launch(ILauncher.RUN_MODE, 
							ip, 
							launcher, 
							inModels, 
							Collections.<String, String> emptyMap(),
							outModels, 
							paths, 
							options, 
							Collections.<String, InputStream> emptyMap(), 
							transformation.openStream());	

	}
	
	
}
