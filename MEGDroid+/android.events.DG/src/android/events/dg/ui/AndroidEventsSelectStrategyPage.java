package android.events.dg.ui;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

public class AndroidEventsSelectStrategyPage extends WizardPage implements Listener{
	
	IWorkbench workbench;
	IStructuredSelection selection;
	
	// widgets on this page 

	Button Model_Driven;
	Button Random;


	public Boolean RandomSelected = true;
	
	// status variable for the possible errors on this page
	
	IStatus status;
	/**
	 * Constructor for AndroidEventsSelectStrategyPage.
	 */
	
	public AndroidEventsSelectStrategyPage (IWorkbench workbench, IStructuredSelection selection) {
		super("StrategyPage");
		setTitle("Events Generation");
		setDescription("Welcome to Malware Events Generator!!!!!");
		this.workbench = workbench;
		this.selection = selection;
		status = new Status(IStatus.OK, "Please Select Strategy", 0, "", null);
			
	}
	
	
	
	
	
	
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
	
		    // create the composite to hold the widgets
			GridData gd;
			Composite composite =  new Composite(parent, SWT.NULL);

		    // create the desired layout for this wizard page
			GridLayout gl = new GridLayout();
			int ncol = 4;
			gl.numColumns = ncol;
			composite.setLayout(gl);

	

			// Choice of Strategy		
			Model_Driven = new Button(composite, SWT.RADIO);
			Model_Driven.setText("Model Driven Generation");
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = ncol;
			Model_Driven.setLayoutData(gd);
			Model_Driven.setSelection(true);
			
			Random = new Button(composite, SWT.RADIO);
			Random.setText("Random Generation");
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = ncol;
			Random.setLayoutData(gd);
			

			
			
			
		    // set the composite as the control for this page
			setControl(composite);		
			addListeners();
		
	}
	
	private void addListeners()
	{
		Model_Driven.addListener(SWT.Selection, this);
		Random.addListener(SWT.Selection, this);
		
	}


	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
	}
	
	public IWizardPage getNextPage()
	{    		
			
		if (Model_Driven.getSelection()) {
			
			AndroidEventsExtractorWizardPage page = ((AndroidEventsExtractionWizard)getWizard()).selectProjectAndXMLDescriptors;
			return page;
		} else if (Random.getSelection()){
			AndroidEventsExtractorWizardPage page = ((AndroidEventsExtractionWizard)getWizard()).selectProjectAndXMLDescriptors;
		
			RandomSelected = true;
			return page;
		}

		return null;
	}
	
	public boolean canFlipToNextPage()
	{
		if (getErrorMessage() != null) return false;
		if (Model_Driven.getSelection()|| Random.getSelection())
			
			return true;
		return false;
	}


}