package android.events.execute.transformations;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
import android.events.dg.Utility;


public class GeneratingEvents {
	
	public static void runATL(String inputURI, String outputURI) {
        try {
        	System.out.println("ATLinitial:\n");
            ILauncher transformationLauncher = new EMFVMLauncher();
            ModelFactory modelFactory = new EMFModelFactory();
            IInjector injector = new EMFInjector();
            IExtractor extractor = new EMFExtractor();
            
             
            System.out.println("recievemodels:\n");
            IReferenceModel androidMetaModel = modelFactory.newReferenceModel();
            injector.inject(androidMetaModel, Utility.DISCOVERY_METAMODEL_PATH);
            
            IReferenceModel GeneratingMetamodel = modelFactory.newReferenceModel();
    		injector.inject(GeneratingMetamodel, Utility.GNERATE_METAMODEL_PATH);
            

            IModel inModel = modelFactory.newModel(androidMetaModel);
            injector.inject(inModel, inputURI);

            IModel outModel = modelFactory.newModel(GeneratingMetamodel);
            transformationLauncher.initialize(new HashMap());
            transformationLauncher.addInModel(inModel, "IN", "EventsDesc");
            transformationLauncher.addOutModel(outModel, "OUT", "EventsGen");
            transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap(),
                    new FileInputStream(Utility.ANDROID_GENERATING));
            System.out.println("executeATL:\n");
            extractor.extract(outModel,  outputURI);
            System.out.println("savemodel:\n");
           
            EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
            emfModelFactory.unload((EMFModel) inModel);
            emfModelFactory.unload((EMFModel) outModel);
            emfModelFactory.unload((EMFReferenceModel) androidMetaModel);
            emfModelFactory.unload((EMFReferenceModel) GeneratingMetamodel);
            
            
        } catch (ATLCoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        
			
		}
    }	
}
