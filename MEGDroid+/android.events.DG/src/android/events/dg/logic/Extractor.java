package android.events.dg.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.infra.discoverymanager.ui.launch.parametersdisplay.ParametersTableContentProvider;
import org.eclipse.gmt.modisco.java.actions.DefaultDiscoverer;
import org.eclipse.gmt.modisco.java.actions.DiscoverJavaModelFromJavaProject;
import org.eclipse.gmt.modisco.java.generation.files.GenerateJavaExtended;
import org.eclipse.gmt.modisco.xml.discoverer.actions.XMLModelDiscoverer;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.ui.IWorkbench;

import android.events.dg.Utility;
import android.events.dg.ui.AndroidEventsSelectStrategyPage;
import android.events.execute.transformations.AndroidEventsExtractor;
import android.events.execute.transformations.GeneratingEvents;
public class Extractor {
	
	private IProject javaProject;
	private IFile AndroidManestifDescriptor;
	private IFile AndroidManestifDescriptor1;
	
	//private IFile outputEventsGeneratePath1;
	private String javaProjectPath;
	private String javaProjectPath1;
	
	
	private String AndroidManifestDescriptorPath;
	private String AndroidManifestDescriptorPath1;
	
	
	private String AndroidEventsPath1;
	private String AndroidEventsPath11;
	private String ManifestFile;
	private String target;
	private String XMLMODEL;
	private String JavaMODEL;
	private String XMLMODEL1;
	private String JavaMODEL1;
	private String modelgenrating;
	private String modelDescovey;
	
	private String mergedPIMPath;
	private IWorkbench workbench;
	private IStructuredSelection selection;
	
	public Extractor(IProject javaProject, String Manifest) {
		
		this.javaProject = javaProject;
		this.javaProjectPath = this.javaProject.getLocation().toFile().getPath();
		this.javaProjectPath1=this.javaProject.getLocation().toString();
		
		 modelgenrating=  "file:/C:/Users/hayyan/Desktop/Project/org.eclipse.acceleo.module.EventsGenerating/Model/GeneratedEvents.xmi";
		 modelDescovey = "file:/C:/Users/hayyan/Desktop/Project/android.events.DG/Models/EventsDiscovery.xmi";
		
		
		
		
		this.AndroidEventsPath1= "file:/"+ ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + this.javaProject.getName() + "/" + "EventsDiscovery.xmi";
		this.AndroidEventsPath11= "file:/"+ this.javaProjectPath1+"/"+"EventsDiscovery.xmi";
		this.ManifestFile= "file:/"+ ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + this.javaProject.getName() + "/" + "AndroidManifest.xml";
		this.XMLMODEL= "file:/"+ ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + this.javaProject.getName() + "/" + "AndroidManifest_xml.xmi";
		this.XMLMODEL1="file:/"+ this.javaProjectPath1+ "/"+"AndroidManifest_xml.xmi";
		
		
		this.mergedPIMPath = this.javaProjectPath + "\\" + this.javaProject.getName() + ".pim.xmi";
		
		if (Manifest != null) {		
			this.AndroidManifestDescriptorPath = this.getFullPath(Manifest);
			this.AndroidManifestDescriptorPath1 = "file:/"+ ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + this.javaProject.getName() + "/" + "AndroidManifest.xml";
			this.AndroidManestifDescriptor = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(this.AndroidManifestDescriptorPath));	
			this.AndroidManestifDescriptor1 = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(this.AndroidManifestDescriptorPath1));
		}
		
		
	}
	
	public String getFullPath(String path) {
		String output = "";
		if (path.startsWith("platform:/resource/")) {
			String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile().getPath();
			String filePath = path.replaceAll("platform:/resource/", "");
			output = workspacePath + "\\" + filePath.replaceAll("/", "\\\\");
		}
		else if (path.startsWith("file:/")) {
			output = path.replaceAll("file:/", "");
		}
		
		return output;
	}
	

	public void execute() throws IOException, ATLCoreException, CoreException {
		//create model representations of the Java code and XML descriptors
		this.generateModels();
		
		//populate the models 
		this.extractEventsFromAndroid();
		this.GetGeneratedEvents();
		
	    
		//TODO: merge PIMS
		
		this.cleanProject();
		
	}
	
	private void cleanProject() throws CoreException {
		
		this.refreshProjet();		
		this.refreshProjet();
	}
	
	private void refreshProjet() throws CoreException {
		this.javaProject.refreshLocal(IResource.DEPTH_INFINITE, null);
	}
	
	private void generateModels() throws IOException {
		this.generateModelCode(this.javaProject);
	System.out.print("   AndroidManestifDescriptor1  :" + this.AndroidManifestDescriptorPath1);
			this.generateModelXML(this.AndroidManestifDescriptor1);
	}
	
	
	public void GetGeneratedEvents() throws IOException, ATLCoreException {
		System.out.print("Events Generated");
		GeneratingEvents transformation=new GeneratingEvents();

		try {
			transformation.runATL(modelDescovey,modelgenrating);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Events Generated End");
		

	}
		
	public void extractEventsFromAndroid() throws IOException, ATLCoreException {
		
		System.out.println("Extract android model ");
		AndroidEventsExtractor transformation=new AndroidEventsExtractor();
		System.out.println(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + this.javaProject.getName() + "/" + "app.android.xmi");
		
		try {
		transformation.runATL(this.XMLMODEL1, this.JavaMODEL1, modelDescovey);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Extract android model      end");
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void generateModelCode(IProject project) throws IOException {
		IJavaProject javaProject = JavaCore.create(project);
		 System.out.println("   Create a discoverer for a Java project");
		DiscoverJavaModelFromJavaProject javaDiscoverer = new DiscoverJavaModelFromJavaProject();

		//Parameters of the discoverer
		@SuppressWarnings("rawtypes")
		Map javaDiscoveryParameters = new HashMap();
		javaDiscoveryParameters.put(DefaultDiscoverer.PARAMETER_SILENT_MODE, true);
		javaDiscoveryParameters.put(DefaultDiscoverer.PARAMETER_BROWSE_RESULT, false);

		//Execute the discoverer (javaProject is a IJavaProject) and serialize the model to <project_name>.javaxmi
		javaDiscoverer.discoverElement(javaProject, javaDiscoveryParameters);
		
		this.JavaMODEL="file:/"+ ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + this.javaProject.getName() + "/" + this.javaProject.getName() + ".javaxmi";
		this.JavaMODEL1="file:/"+ this.javaProjectPath1+ "/"+ this.javaProject.getName()+ ".javaxmi";
		System.out.println("   JavaMODEL1:  "+this.JavaMODEL1);
		return;
	}
		
	@SuppressWarnings("unchecked")
	public void generateModelXML(IFile source) {
       
       XMLModelDiscoverer discoverer = new XMLModelDiscoverer();
   
     // launch the discovery
        Map xmlDiscoveryParameters = new HashMap();
		xmlDiscoveryParameters.put(XMLModelDiscoverer.PARAMETER_SILENT_MODE, true);
		xmlDiscoveryParameters.put(XMLModelDiscoverer.PARAMETER_SERIALIZE_XMI, true);

     // get the resulting resource containing the XML model
    Resource xmlResource = discoverer.getResourceResult();
     System.out.println("    xmlResource: " + xmlResource);
		System.out.println("   XML model:" + xmlDiscoveryParameters);
		 System.out.println(" end  XML discovery:");
		
		
	}
	
	
	
}
