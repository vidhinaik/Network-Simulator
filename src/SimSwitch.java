
public class SimSwitch extends SimDevice {

	private int portCount = 60;
	private SimDevice[] sendingDevices;
	private SimDevice[] receivingDevices;
	private String switchName;

	SimSwitch(String switchName) {
		this.switchName = switchName;
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public int getPortCount() {
		return portCount;
	}

	public void setPortCount(int portCount) {
		this.portCount = portCount;
	}

	public SimDevice[] getSendingDevices() {
		return sendingDevices;
	}

	public void setSendingDevices(SimDevice[] sendingDevices) {
		this.sendingDevices = sendingDevices;
	}

	public SimDevice[] getReceivingDevices() {
		return receivingDevices;
	}

	public void setReceivingDevices(SimDevice[] receivingDevices) {
		this.receivingDevices = receivingDevices;
	}
}
