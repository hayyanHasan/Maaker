package android.events.dg.actions;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import android.events.dg.logic.Extractor;
import android.events.dg.ui.AndroidEventsExtractionWizard;
import android.events.dg.ui.AndroidEventsExtractorWizardPage;
import android.events.dg.ui.AndroidEventsSelectStrategyPage;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ExtractorAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	/**
	 * The constructor.
	 */
	public ExtractorAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		try {
			AndroidEventsSelectStrategyPage selectStrategyPage = new AndroidEventsSelectStrategyPage(null, null);
			AndroidEventsExtractionWizard wizard = new AndroidEventsExtractionWizard();
			WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
			
			if (dialog.open() == Window.OK) {
				
				AndroidEventsExtractorWizardPage page = (AndroidEventsExtractorWizardPage)wizard.getPage("SelectProjectAndXMLDescriptors");
				IProject javaProject = page.getSelectedProject();
				
				String AndroidManifestDescriptor = page.getAndroidManifestDescriptor();
				
				String AndroidManifestDescriptor1 =  "file:/"+ ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+ "/" + javaProject.getName() + "/" + "AndroidManifest.xml";
				
				
				//for time:
				
				long startTime = System.currentTimeMillis();
				System.out.println("--------------start time----------------:"+startTime);
				Extractor ext = new Extractor(javaProject, AndroidManifestDescriptor1);
			
					ext.execute();
				
		
				
				//end
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("--------------totalTime----------------:"+totalTime);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}