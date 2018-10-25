import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class SimFramework extends JFrame {
	private long timeCounter;
	private ArrayList<SimApp> applications = new ArrayList<SimApp>();
	private ArrayList<SimServer> servers = new ArrayList<SimServer>();
	private ArrayList<SimClient> clients = new ArrayList<SimClient>();
	private ArrayList<SimSwitch> switches = new ArrayList<SimSwitch>();
	public JPanel panelSelect, panelStatus, panelAction, panelConnection, panelDisplay;
	public JFrame jf1;
	public JTextField selectClientNumber, selectServerNumber, selectSwitchNumber;
	public JButton submit, connect, saveText, sendMessageButton;
	public int serverCount, switchCount, clientCount;
	public JLabel errorMessage, sendMessageLabel, selectClientApplication, connectionStatus;
	public JLabel messageTimeLabel, messageLengthLabel, messageLabel, messageDisplayLabel01, messagePacketLabel;
	public JLabel selectedClientLabel, selectedServerLabel, hopsLabel, status;
	public ArrayList<String> clientSwitches = new ArrayList<String>();
	public ArrayList<String> switchesSwitches = new ArrayList<String>();
	public ArrayList<String> serverSwitches = new ArrayList<String>();
	public ArrayList<String> serverApplication = new ArrayList<String>();
	public JComboBox selectClient, selectApplication;
	public String clientNames[], applicationNames[];
	public JTextArea sendMessage;
	long startTime, stopTime, elapsedTime;
	public int hops;
	public Hashtable<String, String> serverApplicationMapping = new Hashtable<String, String>();
	public SimMessage simMessage = new SimMessage();
	public ArrayList<SimPacket> simPackets = new ArrayList<SimPacket>();
	public String cMac = "";
	public String sMac = "";

	// JComboBox selectClientNumber,selectServerNumber,selectSwitchNumber;
	public long getTimeCounter() {
		return timeCounter;
	}

	public void setTimeCounter(long timeCounter) {
		this.timeCounter = timeCounter;
	}

	public ArrayList<SimApp> getApplications() {
		return applications;
	}

	public void setApplications(ArrayList<SimApp> applications) {
		this.applications = applications;
	}

	public ArrayList<SimServer> getServers() {
		return servers;
	}

	public void setServers(ArrayList<SimServer> servers) {
		this.servers = servers;
	}

	public ArrayList<SimClient> getClients() {
		return clients;
	}

	public void setClients(ArrayList<SimClient> clients) {
		this.clients = clients;
	}

	public ArrayList<SimSwitch> getSwitches() {
		return switches;
	}

	public void setSwitches(ArrayList<SimSwitch> switches) {
		this.switches = switches;
	}

	SimFramework() {


		jf1 = new JFrame("Select Window");
		panelSelect = new JPanel();
		panelSelect.setLayout(null);

		submit = new JButton("Submit");
		submit.setBounds(200, 150, 100, 30);

		JLabel messageSelection = new JLabel("How many Clients, Servers and Switches do you want to add?");
		JLabel messageSelection01 = new JLabel("Max limit: 10 servers, 10 Clients and 4 Switches.");
		messageSelection.setFont(new Font("Calibri", Font.ITALIC, 20));
		messageSelection01.setBounds(30, 30, 600, 30);
		messageSelection01.setFont(new Font("Calibri", Font.ITALIC, 15));
		messageSelection.setBounds(30, 10, 600, 30);
		panelSelect.add(messageSelection);
		panelSelect.add(messageSelection01);

		JLabel clientsLabel = new JLabel("Clients");
		clientsLabel.setBounds(50, 75, 300, 20);
		clientsLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
		selectClientNumber = new JTextField(5);
		selectClientNumber.setBounds(50, 95, 100, 20);

		JLabel serversLabel = new JLabel("Servers");
		serversLabel.setBounds(200, 75, 300, 20);
		serversLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
		selectServerNumber = new JTextField(5);
		selectServerNumber.setBounds(200, 95, 100, 20);

		JLabel switchesLabel = new JLabel("Switches");
		switchesLabel.setBounds(350, 75, 300, 20);
		switchesLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
		selectSwitchNumber = new JTextField(3);
		selectSwitchNumber.setBounds(350, 95, 100, 20);

		errorMessage = new JLabel("Enter numeric values");
		panelSelect.add(errorMessage);
		errorMessage.setVisible(false);
		errorMessage.setBounds(150, 200, 300, 30);
		errorMessage.setFont(new Font("Calibri", Font.ITALIC, 20));
		errorMessage.setForeground(Color.RED);

		panelSelect.add(messageSelection);
		panelSelect.add(clientsLabel);
		panelSelect.add(serversLabel);
		panelSelect.add(switchesLabel);
		panelSelect.add(submit);
		panelSelect.add(selectClientNumber);
		panelSelect.add(selectServerNumber);
		panelSelect.add(selectSwitchNumber);

		add(panelSelect);
		// add(jf1);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// try {
				errorMessage.setVisible(false);
				if (Integer.parseInt(selectServerNumber.getText()) > 10
						|| Integer.parseInt(selectServerNumber.getText()) <= 0)
					serverCount = 5;
				else
					serverCount = Integer.parseInt(selectServerNumber.getText());

				if (Integer.parseInt(selectSwitchNumber.getText()) > 4
						|| Integer.parseInt(selectSwitchNumber.getText()) <= 0)
					switchCount = 3;
				else
					switchCount = Integer.parseInt(selectSwitchNumber.getText());

				if (Integer.parseInt(selectClientNumber.getText()) > 10
						|| Integer.parseInt(selectClientNumber.getText()) <= 0)
					clientCount = 5;
				else
					clientCount = Integer.parseInt(selectClientNumber.getText());

				clientNames = new String[clientCount];
				// Varun modification;

				applicationNames = new String[serverCount * 2];
				for (int i = 1; i <= clientCount; i++) {
					SimClient client = new SimClient("Client " + Integer.toString(i));
					client.assignIPMac(client.getClientName());
					clients.add(client);
					simMessage.insertIntoFile(client.getClientName() + "\n" + "IP Address:" + client.getIpAddress()
							+ "\n" + "MAC Address:" + client.getMacAddress() + "\n");
					clientNames[i - 1] = "Client " + Integer.toString(i);
				}
				for (int i = 1; i <= 2 * serverCount; i++) {
					applications.add(new SimApp("Application " + i));
					applicationNames[i - 1] = "Application " + i;
				}
				for (int i = 1; i <= serverCount; i++) {
					// Varun
					SimApp[] temp = getApplicationArray(i);
					SimServer server = new SimServer("Server " + Integer.toString(i), temp);
					// Vidhi
					serverApplicationMapping.put(temp[0].getAppName(), "Server " + i);
					serverApplicationMapping.put(temp[1].getAppName(), "Server " + i);
					server.assignIPMac("Server " + Integer.toString(i));
					servers.add(server);
					simMessage.insertIntoFile(server.getServerName() + "\n" + "IP Address:" + server.getIpAddress()
							+ "\n" + "MAC Address:" + server.getMacAddress() + "\n");
				}
				for (int i = 1; i <= switchCount; i++) {
					SimSwitch sw = new SimSwitch("Switch " + Integer.toString(i));
					sw.assignIPMac("Switch " + Integer.toString(i));
					switches.add(sw);
					simMessage.insertIntoFile(sw.getSwitchName() + "\n" + "MAC Address:" + sw.getMacAddress() + "\n");
				}
				mainWindow();
				errorMessage.setVisible(true);
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	SimApp[] getApplicationArray(int i) {
		SimApp[] app = new SimApp[2];
		int index = (i * 2) - 2;
		app[0] = applications.get(index);
		app[1] = applications.get(index + 1);
		return app;
	}

	void createSwitchSwitchConnection() {
		if (switchCount > 1) {
			ArrayList<String> switchNMac = SimDevice.switchNameMAC;
			for (int i = 0; i < switchCount - 1; i++) {
				for (int j = 0; j < switchNMac.size() - 1; j++) {
					if (switchNMac.get(j).contains(switches.get(i).getSwitchName())) {
						String[] split = switchNMac.get(j).split("-");
						String[] split2 = switchNMac.get(j + 1).split("-");
						switchesSwitches.add(split[1] + "-" + split[1]);
					}
				}
			}
		}
	}

	void createServerSwitchConnection() {
		ArrayList<String> serv = SimDevice.serverIpMac;
		ArrayList<String> sw = SimDevice.switchNameMAC;
		if (switchCount == 1) {

			for (int i = 0; i < serverCount - 1; i++) {
				for (int j = 0; j < serv.size(); j++) {
					if (serv.get(j).contains(servers.get(i).getServerName())) {
						String[] split = serv.get(j).split("-");
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(0).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								serverSwitches.add(split[1] + "-" + split2[1]);
							}
						}
					}
				}
			}
		} else {

			String servermac = "";
			String switchmac = "";

			for (int j = 0; j < serverCount; j++) {
				for (int k = 0; k < serv.size(); k++) {
					if (serv.get(k).contains(servers.get(j).getServerName())) {
						String[] split = serv.get(k).split("-");
						servermac = split[1];
					}
				}
				if (j <= serverCount / 2) {
					if (switchCount <= 2) {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(0).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								switchmac = split2[1];
							}
						}
						serverSwitches.add(servermac + "-" + switchmac);
					} else {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(0).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								switchmac = split2[1];
								serverSwitches.add(servermac + "-" + switchmac);
							}
							if (sw.get(k).contains(switches.get(1).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								switchmac = split2[1];
								serverSwitches.add(servermac + "-" + switchmac);
							}
						}
					}
				} else {
					if (switchCount <= 2) {

						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(0).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								switchmac = split2[1];
								serverSwitches.add(servermac + "-" + switchmac);
							}
							if (sw.get(k).contains(switches.get(1).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								switchmac = split2[1];
								serverSwitches.add(servermac + "-" + switchmac);
							}
						}
					} else {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(2).getSwitchName())) {
								String[] split2 = sw.get(k).split("-");
								switchmac = split2[1];
								serverSwitches.add(servermac + "-" + switchmac);
							}
							
						}
					}
				}
			}
		}
	}

	void createClientSwitchConnection() {
		ArrayList<String> client = SimDevice.clientIpMac;
		ArrayList<String> sw = SimDevice.switchNameMAC;
		String clientMac1 = "";
		String switchMac = "";

		if (switchCount == 1) {
			for (int i = 0; i < clientCount - 1; i++) {
				for (int j = 0; j < client.size(); j++) {
					if (client.get(j).contains(clients.get(i).getClientName())) {
						String[] split = client.get(j).split("-");
						clientMac1 = split[1];
					}
				}
				for (int j = 0; j < sw.size(); j++) {
					if (sw.get(j).contains(switches.get(0).getSwitchName())) {
						String[] split = sw.get(j).split("-");
						switchMac = split[1];
					}
				}
				clientSwitches.add(clientMac1 + "-" + switchMac);
			}
		} else {
			for (int j = 0; j < clientCount; j++) {
				for (int i = 0; i < client.size(); i++) {
					if (client.get(i).contains(clients.get(j).getClientName())) {
						String[] split = client.get(i).split("-");
						clientMac1 = split[1];
					}
				}
				if (j <= clientCount / 2) {
					if (switchCount <= 2) {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(0).getSwitchName())) {
								String[] split = sw.get(k).split("-");
								switchMac = split[1];
							}
						}
						clientSwitches.add(clientMac1 + "-" + switchMac);
					} else {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(2).getSwitchName())) {
								String[] split = sw.get(k).split("-");
								switchMac = split[1];
							}
						}
						clientSwitches.add(clientMac1 + "-" + switchMac);
					}
				} else {
					if (switchCount <= 3) {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(1).getSwitchName())) {
								String[] split = sw.get(k).split("-");
								switchMac = split[1];
							}
						}
						clientSwitches.add(clientMac1 + "-" + switchMac);
					} else {
						for (int k = 0; k < sw.size(); k++) {
							if (sw.get(k).contains(switches.get(2).getSwitchName())) {
								String[] split = sw.get(k).split("-");
								switchMac = split[1];
							}
						}
						clientSwitches.add(clientMac1 + "-" + switchMac);
					}
				}
			}
		}
	}

	public void mainWindow() {
		this.setSize(1000, 500);
		panelSelect.setVisible(false);

		selectClient = new JComboBox(clientNames);
		selectClient.setBounds(20, 50, 150, 25);

		selectApplication = new JComboBox(applicationNames);
		selectApplication.setBounds(20, 90, 150, 25);

		panelConnection = new JPanel();
		panelConnection.setLayout(null);
		panelConnection.setBounds(0, 0, 350, 500);
		panelConnection.setBackground(Color.WHITE);

		panelStatus = new JPanel();
		panelStatus.setLayout(null);
		panelStatus.setBounds(350, 0, 650, 200);
		this.add(panelStatus);

		panelDisplay = new JPanel();
		panelDisplay.setLayout(null);
		panelDisplay.setBounds(350, 200, 650, 300);
		panelDisplay.setBackground(Color.decode("#eee5de"));
		this.add(panelDisplay);

		selectClientApplication = new JLabel("Select the client and Application");
		selectClientApplication.setBounds(20, 20, 290, 25);
		selectClientApplication.setFont(new Font("Calibri", Font.ITALIC, 20));

		sendMessageLabel = new JLabel("Message");
		sendMessageLabel.setBounds(20, 170, 150, 25);
		sendMessageLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

		selectedClientLabel = new JLabel();
		selectedServerLabel = new JLabel();
		hopsLabel = new JLabel();
		messagePacketLabel = new JLabel();
		status = new JLabel("Connection Status");

		selectedClientLabel.setBounds(20, 30, 650, 20);
		selectedServerLabel.setBounds(20, 55, 650, 20);
		hopsLabel.setBounds(20, 80, 650, 20);
		status.setBounds(20, 10, 650, 20);
		messagePacketLabel.setBounds(20, 100, 650,20);

		selectedClientLabel.setVisible(false);
		selectedServerLabel.setVisible(false);
		hopsLabel.setVisible(false);
		messagePacketLabel.setVisible(false);

		connect = new JButton("Connect");
		connect.setBounds(20, 130, 150, 25);
		sendMessage = new JTextArea("Type your message here");
		sendMessage.setBounds(20, 200, 250, 100);
		sendMessage.setBackground(Color.LIGHT_GRAY);
		saveText = new JButton("Delete Connection");
		saveText.setBounds(20, 340, 150, 25);
		sendMessageButton = new JButton("Send Message");
		sendMessageButton.setBounds(20, 310, 150, 25);

		messageLabel = new JLabel();
		messageLabel.setBounds(20, 70, 600, 15);
		messageLabel.setVisible(false);

		messageDisplayLabel01 = new JLabel("Message received");
		messageDisplayLabel01.setBounds(20, 10, 600, 15);
		messageDisplayLabel01.setVisible(false);
		messageDisplayLabel01.setFont(new Font("Calibri", Font.ITALIC, 15));

		messageTimeLabel = new JLabel();
		messageTimeLabel.setBounds(20, 30, 600, 15);
		messageTimeLabel.setVisible(false);

		messageLengthLabel = new JLabel();
		messageLengthLabel.setBounds(20, 50, 600, 15);
		messageLengthLabel.setVisible(false);

		panelConnection.add(connect);
		panelConnection.add(selectClientApplication);
		panelConnection.add(saveText);
		panelConnection.add(sendMessageButton);
		panelConnection.add(selectClient);
		panelConnection.add(selectApplication);
		panelConnection.add(sendMessageLabel);
		panelConnection.add(sendMessage);

		panelDisplay.add(messageLabel);
		panelDisplay.add(messageDisplayLabel01);
		panelDisplay.add(messageLengthLabel);
		panelDisplay.add(messageTimeLabel);
		panelDisplay.add(messagePacketLabel);

		panelStatus.add(selectedClientLabel);
		panelStatus.add(selectedServerLabel);
		panelStatus.add(hopsLabel);

		this.add(panelConnection);

		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSwitchSwitchConnection();
				createServerSwitchConnection();
				createClientSwitchConnection();
				String clientName = (String) selectClient.getSelectedItem();
				ArrayList<String> cIpMac = SimDevice.clientIpMac;
				ArrayList<String> sIpMac = SimDevice.serverIpMac;
				String clientMac = "";
				for (int i = 0; i < cIpMac.size(); i++) {
					if (cIpMac.get(i).contains(clientName)) {
						String[] split = cIpMac.get(i).split("-");
						clientMac = split[1];
					}
				}
				String selectedApplicationName = (String) selectApplication.getSelectedItem();
				String selectedServerName = serverApplicationMapping.get(selectedApplicationName);
				String serverMac = "";
				for (int i = 0; i < sIpMac.size(); i++) {
					if (sIpMac.get(i).contains(selectedServerName)) {
						String[] split = sIpMac.get(i).split("-");
						serverMac = split[1];
					}
				}

				String output = findConnectionRoute(clientMac, serverMac);
				cMac = clientMac;
				sMac = serverMac;

				selectedClientLabel.setText(clientName + ": " + clientMac);
				simMessage.insertIntoFile("Connected client: " + clientName + "\n");
				selectedServerLabel.setText(selectedServerName + ": " + serverMac);
				simMessage.insertIntoFile("Connected server: " + selectedServerName + "\n");

				String[] temp = output.split("-");
				if(serverCount > 5 || clientCount > 5 )
				{
					
						hops = 2;
					
				}
				else {hops = temp.length - 2;}
				
				hopsLabel.setText("Number of hops: " + hops);
				simMessage.insertIntoFile("Number of hops: " + hops + "\n");
				selectedClientLabel.setVisible(true);
				selectedServerLabel.setVisible(true);
				hopsLabel.setVisible(true);
			}
		});

		saveText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientSwitches = null;
				switchesSwitches = null;
				serverSwitches = null;
				serverApplication = null;
				System.exit(0);
			}
		});

		sendMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startTime = System.nanoTime();
				messageLabel.setText(sendMessage.getText());
				messageDisplayLabel01.setVisible(true);
				messageLabel.setVisible(true);

				messageLengthLabel
						.setText(Integer.toString(sendMessage.getText().getBytes().length) + " bytes received.");
				messageLengthLabel.setVisible(true);

				stopTime = System.nanoTime();
				elapsedTime = (stopTime - startTime) / 1000;
				messageTimeLabel.setText("Time taken: " + Long.toString(elapsedTime) + " ms.");
				messageTimeLabel.setVisible(true);
				
				
				
				int length = sendMessage.getText().length();
				int quotient = length / 5;
				int remainder = length % 5;
				int packetNum = 0;
				
				if (remainder == 0)	{
					packetNum = quotient;
				} else {
					packetNum = quotient + 1;
				}
				
				for(int i = 1; i <= packetNum; i++) {
					SimPacket packet = new SimPacket(cMac, sMac, (selectApplication.getSelectedIndex() + 1), i);
					simPackets.add(packet);					
				}
				messagePacketLabel.setText("Number of packet received: "+ packetNum);
				messagePacketLabel.setVisible(true);
				
				simMessage.insertIntoFile("Message: "+sendMessage.getText()+"\n");
				simMessage
						.insertIntoFile("Number of Bytes received: " + sendMessage.getText().getBytes().length + "\n");
				simMessage.insertIntoFile("Time taken to send the message: " + elapsedTime + " ms." + "\n");
				simMessage.insertIntoFile("Number of Packets created: " + packetNum + "\n");
				int j = 0;
				for(j = 0; j < quotient; j++) {
					simMessage.insertIntoFile("Packet Sequence Number " + simPackets.get(j).getSequenceNumber() + ":\n");
					simMessage.insertIntoFile("Packet Source Address: " + simPackets.get(j).getSourceAddress() + "\n");
					simMessage.insertIntoFile("Packet Destination Address: " + simPackets.get(j).getDestinationAddress() + "\n");
					simMessage.insertIntoFile("Packet Application Number: " + simPackets.get(j).getApplicationNumber() + "\n");
					simMessage.insertIntoFile("Packet size: " + 5 + "\n");
				}
				
				if (remainder != 0) {
					simMessage.insertIntoFile("Packet Sequence Number " + simPackets.get(j).getSequenceNumber() + ":\n");
					simMessage.insertIntoFile("Packet Source Address: " + simPackets.get(j).getSourceAddress() + "\n");
					simMessage.insertIntoFile("Packet Destination Address: " + simPackets.get(j).getDestinationAddress() + "\n");
					simMessage.insertIntoFile("Packet Application Number: " + simPackets.get(j).getApplicationNumber() + "\n");
					simMessage.insertIntoFile("Packet size: " + remainder + "\n");
				}
				
				
			}
		});
	}

	public static void main(String[] args) {
		SimFramework se = new SimFramework();
		se.setVisible(true);
		se.setSize(600, 350);
		se.setLocation(200, 100);
	}

	public String findConnectionRoute(String clientMac, String serverMac) {

		String output = clientMac + "-" + serverMac;
		String[] clientSwitch = new String[2];

		int flag = 0;
		// client-switch
		// server-switch

		// 1-hop
		for (int i = 0; i < clientSwitches.size(); i++) {
			String[] splitString = clientSwitches.get(i).split("-");
			if (splitString[0].equals(clientMac)) {
				clientSwitch = splitString;
			}
		}

		String sSwitches = "";
		for (int i = 0; i < serverSwitches.size(); i++) {
			String[] splitString = serverSwitches.get(i).split("-");
			if (splitString[0].equals(serverMac)) {
				sSwitches = sSwitches + splitString[1] + ",";
				if (splitString[1].equals(clientSwitch[1])) {
					output = output + "-" + splitString[1];
					return output;
				}
			}
		}

		output = output + "-" + clientSwitch[1];

		// 2-hop
		for (int i = 0; i < switchesSwitches.size(); i++) {
			String[] splitString = switchesSwitches.get(i).split("-");
			if (splitString[0].equals(clientSwitch[1])) {
				if (sSwitches.contains(splitString[1])) {
					output = output + "-" + splitString[1];
					return output;
				}
			} else if (splitString[1].equals(clientSwitch[1])) {
				if (sSwitches.contains(splitString[0])) {
					output = output + "-" + splitString[0];
					return output;
				}
			}
		}

		// 3-hop
		for (int i = 0; i < switchesSwitches.size(); i++) {

			String[] splitString = switchesSwitches.get(i).split("-");

			if (splitString[0].equals(clientSwitch[1])) {

				String result = checkSwitch(splitString[1], sSwitches);
				if (!result.equals("fail")) {
					output = output + "-" + splitString[1] + "-" + result;
					return output;
				}
			} else if (splitString[1].equals(clientSwitch[1])) {
				String result = checkSwitch(splitString[0], sSwitches);
				if (!result.equals("fail")) {
					output = output + "-" + splitString[0] + "-" + result;
					return output;
				}
			}
		}
		return output;
	}

	public String checkSwitch(String switchMac, String serverSwitches) {

		for (int i = 0; i < switchesSwitches.size(); i++) {
			String splitString[] = switchesSwitches.get(i).split("-");
			if (splitString[0].equals(switchMac) || splitString[1].equals(switchMac)) {
				if (serverSwitches.contains(splitString[0])) {
					return splitString[0];
				} else if (serverSwitches.contains(splitString[1])) {
					return splitString[1];
				}
			}
		}
		return "fail";
	}

}