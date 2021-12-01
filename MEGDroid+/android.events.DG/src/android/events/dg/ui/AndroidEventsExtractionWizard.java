package android.events.dg.ui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class AndroidEventsExtractionWizard extends Wizard implements INewWizard {
	
	AndroidEventsExtractorWizardPage selectProjectAndXMLDescriptors;
	 AndroidEventsSelectStrategyPage selectStrategy;
	 private IProject MainProject;
	 protected IStructuredSelection selection;
		
		// the workbench instance
		protected IWorkbench workbench;
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	public AndroidEventsExtractionWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("EventsGenerator"); //$NON-NLS-1$
	}
	

	@Override
	public void addPages() {
		
		
		selectStrategy = new AndroidEventsSelectStrategyPage (workbench, selection);
		addPage(selectStrategy);
	
	selectProjectAndXMLDescriptors = new AndroidEventsExtractorWizardPage("SelectProjectAndXMLDescriptors");
	addPage(selectProjectAndXMLDescriptors);
	
	

	}

	public boolean canFinish()
	{
	if(getContainer().getCurrentPage() == selectStrategy) 
	return false;
	else if (getContainer().getCurrentPage() == selectProjectAndXMLDescriptors ){
	
		
		return selectProjectAndXMLDescriptors.projectselected;
		
	}
		
	else
	return true;
	}
	@Override
	public boolean performFinish() {
		
		if (selectStrategy.RandomSelected){
			 try {
				 PrintWriter out = new PrintWriter("C:\\Users\\hayyan\\Desktop\\Project\\samples\\instruction.txt");
				 out.println("Random");
				 out.flush();
				 out.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 return true;
		} 
		return true;
	}
	
	
	public boolean performCancel() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
