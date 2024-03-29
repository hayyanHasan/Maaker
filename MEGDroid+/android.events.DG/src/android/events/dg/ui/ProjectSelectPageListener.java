package android.events.dg.ui;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ProjectSelectPageListener implements Listener {

	private AndroidEventsExtractorWizardPage page;
	private int javaProjectIndex = -1;
	
	public ProjectSelectPageListener(AndroidEventsExtractorWizardPage page) {
		this.page = page;
	}

	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		javaProjectIndex = event.index;
		if (this.page.getCurrentProjectIndex() != this.javaProjectIndex) {
			this.page.loadJavaProject();
		}
		this.page.dialogChanged();
	}
	
	public int getJavaProjectIndex() {
		return this.javaProjectIndex;
	}
	
}
