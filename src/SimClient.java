
public class SimClient extends SimDevice {

	private SimServer[] servers;
	private SimApp[] applications;
	private String clientName;

	SimClient(String clientName) {
		this.clientName = clientName;
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public SimServer[] getServers() {
		return servers;
	}

	public void setServers(SimServer[] servers) {
		this.servers = servers;
	}

	public SimApp[] getApplications() {
		return applications;
	}

	public void setApplications(SimApp[] applications) {
		this.applications = applications;
	}

}
