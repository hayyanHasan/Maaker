package android.events.dg.ui;


import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class AndroidEventsExtractorWizardPage extends WizardPage {
	 AndroidEventsSelectStrategyPage selectStrategyPage;
	private Composite container;
	private Group selectAndroidProjectSection; 
	private org.eclipse.swt.widgets.List listJavaProjects; 
	private ProjectSelectPageListener listener;
	public IProject selectedProject;
	private int currentProjectIndex = -1;
	
	private String AndroidManifestDescriptorPath;
	
	public boolean projectselected = true;
	
	protected AndroidEventsExtractorWizardPage(String pageName) {
		super(pageName);
		super.setTitle("Android Events Extractor for Android Applications");
		// TODO Auto-generated constructor stub
	}
	
	public int getCurrentProjectIndex() {
		return this.currentProjectIndex;
	}
	
	public ProjectSelectPageListener getListener() {
		return this.listener;
	}
	
	@Override
	public void createControl(Composite parent) {
		
		container = new Composite(parent, SWT.NULL);
			
		GridData data = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(data);
		
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		selectAndroidProjectSection = new Group(container, SWT.NULL);
		selectAndroidProjectSection.setText("Select an Android project in the workspace");
		selectAndroidProjectSection.setLayout(new GridLayout());
		selectAndroidProjectSection.setLayoutData(data);
		
		
		
		
		listJavaProjects = new org.eclipse.swt.widgets.List
				(selectAndroidProjectSection, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		
		
		
		GridData  gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		listJavaProjects.setLayoutData(gridData);
		
		
		
		if (!getJavaProjectsInWorkspace().isEmpty()) {
		
			initJavaProjectList();
	        // create listener
	        listener = new ProjectSelectPageListener(this);
	        listJavaProjects.addListener(SWT.Selection, listener);
	        
		}	
		dialogChanged();
		setControl(container);
		
	}
	
	private void initJavaProjectList() {
		listJavaProjects.removeAll();
		for(IProject JavaProject : getJavaProjectsInWorkspace())
	    	listJavaProjects.add(JavaProject.getName(), getJavaProjectsInWorkspace().indexOf(JavaProject));
	}
	
	
	
	public List<IProject> getJavaProjectsInWorkspace() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		
		List<IProject> javaProjects = new LinkedList<IProject>();
	
		for(int i = 0; i < projects.length; i++) {
			IProject p = projects[i];
			
			try {
				String[] natures = p.getDescription().getNatureIds();
				
				for(int j = 0; j < natures.length; j++) {
					if (natures[j].equals(JavaCore.NATURE_ID))
						javaProjects.add(p);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
		
		return javaProjects;
	}
	
	public IProject getJavaProjectByIndex(int index) {
		return getJavaProjectsInWorkspace().get(index);
	}
	
	
	public void dialogChanged() {
		//if there are not Java projects
		if (getJavaProjectsInWorkspace().isEmpty()) {
			updateStatus("No Android projects exist in the workspace");
			projectselected = false;
			return;
		}
		
		if (this.selectedProject == null) {
			updateStatus("An Android project must be specified");
			projectselected = false;
			return;
		}
		projectselected = true;
		updateStatus(null);
		
	} 
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public void loadJavaProject() {
		this.selectedProject = getJavaProjectByIndex(listJavaProjects.getFocusIndex());
		this.setPageComplete(true);
		

		
		
	
	}
	
	public IProject getSelectedProject() {
		return this.selectedProject;
	}
	
	public String getAndroidManifestDescriptor() {
		return this.AndroidManifestDescriptorPath;
	}
	

	public boolean canFlipToNextPage()
	{
		
		
		return false;
		
	}
	
}
