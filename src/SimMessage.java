import java.io.*;
import java.sql.Timestamp;



public class SimMessage {

	private String sourceAddress;
	private String destinationAddress;
	private int applicationNumber;
	private int size;
	FileInputStream in = null;
	FileOutputStream out = null;
	Timestamp timestamp;
	String tempTimeStamp;
	public SimMessage()
	{
		timestamp = new Timestamp(System.currentTimeMillis());
		tempTimeStamp = ""+timestamp.getTime();
		
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public int getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(int applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void insertIntoFile(String message)
	{
		try {
			
			out = new FileOutputStream("Status_" + tempTimeStamp + ".txt", true);
			out.write(message.getBytes());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
}
