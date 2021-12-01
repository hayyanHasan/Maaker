/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package android.events.execute.transformations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.m2m.atl.common.ATLExecutionException;
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

import android.events.dg.Activator;
import android.events.dg.Utility;


public class AndroidEventsExtractor {

	public static void runATL(String inputURI1,String inputURI2, String outputURI) {
        try {
        	System.out.println("ATLinitial:\n");
            ILauncher transformationLauncher = new EMFVMLauncher();
            ModelFactory modelFactory = new EMFModelFactory();
            IInjector injector = new EMFInjector();
            IExtractor extractor = new EMFExtractor();
             
            System.out.println("recievemodels:\n");
           
            EPackage.Registry.INSTANCE.put("java", org.eclipse.gmt.modisco.java.emf.JavaPackage.eINSTANCE);
    		EPackage.Registry.INSTANCE.put("XML", org.eclipse.gmt.modisco.xml.emf.MoDiscoXMLPackage.eINSTANCE);
    		
    		EPackage.Registry.INSTANCE.put("EventsDesc", "./metamodels/EventSource.ecore");
    		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put
    		                                 ("ecore", new EcoreResourceFactoryImpl());
    		
    		IReferenceModel javaMetaModel = modelFactory.newReferenceModel();
             injector.inject(javaMetaModel, "http://www.eclipse.org/MoDisco/Java/0.2.incubation/java");
         
             IReferenceModel xmlMetaModel = modelFactory.newReferenceModel();
             injector.inject(xmlMetaModel, "http://www.eclipse.org/MoDisco/Xml/0.1.incubation/XML");
           
             IReferenceModel androidMetaModel = modelFactory.newReferenceModel();
             injector.inject(androidMetaModel, Utility.DISCOVERY_METAMODEL_PATH);
             
            IModel inModel1 = modelFactory.newModel(xmlMetaModel);
            injector.inject(inModel1, inputURI1);
            
            IModel inModel2 = modelFactory.newModel(javaMetaModel);
            injector.inject(inModel2, inputURI2);


            IModel outModel = modelFactory.newModel(androidMetaModel);
            transformationLauncher.initialize(new HashMap());
            transformationLauncher.addInModel(inModel1, "IN", "XML");
            transformationLauncher.addInModel(inModel2, "IN1", "java");
            transformationLauncher.addOutModel(outModel, "OUT", "EventsDesc");
            transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap(),
                new FileInputStream(Utility.ANDROID_DESCOVERY));
            System.out.println("executeATL:\n");
            extractor.extract(outModel, outputURI);
            System.out.println("savemodel:\n");
            
            EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
            emfModelFactory.unload((EMFModel) inModel1);
            emfModelFactory.unload((EMFModel) inModel2);
            emfModelFactory.unload((EMFModel) outModel);
            emfModelFactory.unload((EMFReferenceModel) xmlMetaModel);
            emfModelFactory.unload((EMFReferenceModel) javaMetaModel);
            emfModelFactory.unload((EMFReferenceModel) androidMetaModel);
        } catch (ATLCoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
