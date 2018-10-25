import java.util.ArrayList;

public abstract class SimDevice {

	private String ipAddress;
	private String macAddress;
	private static int ipCounter = 0;
	private static int macCounter = 0;
	public static ArrayList<String> clientIPs = new ArrayList<String>();
	public static ArrayList<String> clientMACs = new ArrayList<String>();
	public static ArrayList<String> serverIPs = new ArrayList<String>();
	public static ArrayList<String> serverMACs = new ArrayList<String>();
	public static ArrayList<String> switchMACs = new ArrayList<String>();
	public static ArrayList<String> switchNameMAC = new ArrayList<String>();
	public static ArrayList<String> clientIpMac = new ArrayList<String>();
	public static ArrayList<String> serverIpMac = new ArrayList<String>();

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void assignIPMac(String name) {

		String ip = "192.0.0.";
		ipCounter++;
		this.ipAddress = ip + ipCounter;
		//System.out.println(name + ":(IP Address) " + ipAddress);

		String mac = "128.0.0.";
		macCounter++;
		this.macAddress = mac + macCounter;
		//System.out.println(name + ":(Mac Address) " + macAddress);

		if (this instanceof SimClient) {
			clientIPs.add(this.ipAddress);
			clientMACs.add(this.macAddress);
			clientIpMac.add(name + "-" + this.macAddress + "-" + this.ipAddress);
		} else if (this instanceof SimServer) {
			serverIPs.add(this.ipAddress);
			serverMACs.add(this.macAddress);
			serverIpMac.add(name + "-" + this.macAddress + "-" + this.ipAddress);
		} else {
			switchMACs.add(this.macAddress);
			switchNameMAC.add(name + "-" + this.macAddress);
		}
	}
}
