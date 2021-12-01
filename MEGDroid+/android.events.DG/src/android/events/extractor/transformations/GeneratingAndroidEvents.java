package android.events.extractor.transformations;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.core.service.LauncherService;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

import android.events.dg.Utility;

public class GeneratingAndroidEvents {

	
	private ILauncher launcher;
	private IProgressMonitor ip;
	
	private String AndroidEventsPath;
	private String AndroidGeneratingPath;
	
	public GeneratingAndroidEvents(String AndroidEventsPath, String AndroidGeneratingPath) {
		this.AndroidEventsPath = AndroidEventsPath;
		this.AndroidGeneratingPath = AndroidGeneratingPath;
	}

	public void run() throws IOException, ATLCoreException {
		
		this.launcher = new EMFVMLauncher();
		this.ip = new NullProgressMonitor();
		IExtractor extractor = new EMFExtractor();
		ModelFactory modelFactory = new EMFModelFactory();
		IInjector injector = new EMFInjector();
		IReferenceModel resultMetamodel = modelFactory.newReferenceModel();
		injector.inject(resultMetamodel, Utility.GNERATE_METAMODEL_PATH);
		
		Map<String,String> inModels = new HashMap<String,String>();
		inModels.put("IN", "EventsDesc");
		
		Map<String, String> outModels = new HashMap<String, String>();
		outModels.put("OUT", "EventsGen");
		IModel resultModel = modelFactory.newModel(resultMetamodel);
		
		Map<String,String> paths = new HashMap<String,String>();
		paths.put("IN", URI.createFileURI(this.AndroidEventsPath).toString());
		paths.put("OUT", URI.createFileURI(this.AndroidGeneratingPath).toString());
		
		paths.put("EventsDesc", Utility.DISCOVERY_METAMODEL_PATH);
		paths.put("EventsGen", Utility.GNERATE_METAMODEL_PATH);
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("allowInterModelReferences", Boolean.TRUE);

		URL transformation  = this.getClass().getResource(Utility.ANDROID_GENERATING);
		
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
		
		extractor.extract(resultModel, this.AndroidGeneratingPath);

	}
	
}
