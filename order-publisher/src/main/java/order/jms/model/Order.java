package order.jms.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{

	private static final long serialVersionUID = -3232364203446179394L;
	
	private String accont;
	private Date submittedAt;
	private Date receivedAt;
	private String market;
	private String action;
	private Integer size;
	
	public String getAccont() {
		return accont;
	}
	public void setAccont(String accont) {
		this.accont = accont;
	}
	public Date getSubmittedAt() {
		return submittedAt;
	}
	public void setSubmittedAt(Date submittedAt) {
		this.submittedAt = submittedAt;
	}
	public Date getReceivedAt() {
		return receivedAt;
	}
	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Order [accont=" + accont + ", submittedAt=" + submittedAt + ", receivedAt=" + receivedAt + ", market="
				+ market + ", action=" + action + ", size=" + size + "]";
	}
	
	

}
