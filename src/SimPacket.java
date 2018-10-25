
public class SimPacket {

	private String sourceAddress;
	private String destinationAddress;
	private int applicationNumber;
	private int sequenceNumber;

	public SimPacket(String sourceAddress, String destinationAddress, int applicationNumber, int sequenceNumber) {
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.applicationNumber = applicationNumber;
		this.sequenceNumber = sequenceNumber;
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

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

}