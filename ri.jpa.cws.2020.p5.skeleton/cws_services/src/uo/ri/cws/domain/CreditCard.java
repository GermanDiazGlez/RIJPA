package uo.ri.cws.domain;

import java.time.LocalDate;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {
	private String number;
	private String type;
	private LocalDate validThru;

}
