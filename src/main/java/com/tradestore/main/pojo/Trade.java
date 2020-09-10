package main.java.com.tradestore.main.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Trade {

	private String tradeId;
	private Integer version;
	private String counterPartyId;
	private String bookId;
	private LocalDate maturityDate;
	private LocalDate createdDate;
	private boolean expired;

	public Trade() {

	}

	public Trade(String tradeId, Integer version, String counterPartyId, String bookId, LocalDate maturityDate,
			LocalDate createdDate) {
		super();
		this.tradeId = tradeId;
		this.version = version;
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.createdDate = createdDate;
		this.expired = false;
		
		LocalDate currentDate = LocalDate.now();
		if(maturityDate.isBefore(currentDate))
			this.expired = true;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		char isExpired = expired ? 'Y' : 'N';
		return "[Trade Id=" + tradeId + ", Version=" + version + ", Counter-Party Id=" + counterPartyId + ", Book-Id="
				+ bookId + ", Maturity Date=" + maturityDate.format(formatter) + ", Created Date=" + createdDate.format(formatter) + ", Expired=" + isExpired
				+ "]";
	}
	
	

}
