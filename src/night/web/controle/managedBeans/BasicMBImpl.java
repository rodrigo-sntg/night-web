package night.web.controle.managedBeans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
public class BasicMBImpl implements BasicMB, Serializable {

	@Override
	public void addErrorMessage(String componentId, String errorMessage) {
		addMessage(componentId, errorMessage, FacesMessage.SEVERITY_ERROR);
	}

	@Override
	public void addErrorMessage(String errorMessage) {
		addErrorMessage(null, errorMessage);
	}

	@Override
	public void addInfoMessage(String componentId, String infoMessage) {
		addMessage(componentId, infoMessage, FacesMessage.SEVERITY_INFO);
	}

	@Override
	public void addInfoMessage(String infoMessage) {
		addInfoMessage(null, infoMessage);
	}

	private void addMessage(String componentId, String errorMessage, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(errorMessage);
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(componentId, message);
	}
}