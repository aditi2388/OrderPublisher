package order.jms.model;

public class QueueDetails {
	
	private String broker;
	private String username;
	private String password;
	private String destination;
	private String queuetype;
	
	public String getBroker() {
		return broker;
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getQueuetype() {
		return queuetype;
	}
	public void setQueuetype(String queuetype) {
		this.queuetype = queuetype;
	}
	
	@Override
	public String toString() {
		return "QueueDetails [broker=" + broker + ", username=" + username + ", password=" + password + ", destination="
				+ destination + ", queuetype=" + queuetype + "]";
	}
	
	

}
