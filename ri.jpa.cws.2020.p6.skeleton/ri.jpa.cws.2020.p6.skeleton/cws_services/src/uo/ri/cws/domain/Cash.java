package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;

public class Cash extends PaymentMean {
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client);
		Associations.Pay.link(client, this);
	}

}
