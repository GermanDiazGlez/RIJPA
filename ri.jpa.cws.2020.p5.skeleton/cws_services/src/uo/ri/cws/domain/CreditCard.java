package uo.ri.cws.domain;

import java.time.LocalDate;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {
	private String number;
	private String type;
	private LocalDate validThru;
	
	
	public CreditCard(String number) {
		super();
		ArgumentChecks.isNotEmpty(number);
		this.number = number;
	}


	public String getNumber() {
		return number;
	}


	public String getType() {
		return type;
	}


	public LocalDate getValidThru() {
		return validThru;
	}
	
	

}
