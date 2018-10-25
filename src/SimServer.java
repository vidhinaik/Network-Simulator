
public class SimServer extends SimDevice {

	private SimClient[] clients;
	private SimApp[] applications;

	private String serverName;

	SimServer(String serverName, SimApp[] applications) {
		this.serverName = serverName;
		this.applications = applications;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public SimClient[] getClients() {
		return clients;
	}

	public void setClients(SimClient[] clients) {
		this.clients = clients;
	}

	public SimApp[] getApplications() {
		return applications;
	}

	public void setApplications(SimApp[] applications) {
		this.applications = applications;
	}

}
