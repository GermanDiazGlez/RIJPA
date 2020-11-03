package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Voucher extends PaymentMean {
	@Column (unique=true)
	private String code;
	private double available = 0.0;
	private String description;

	@ManyToOne private Client client;

	@OneToMany(mappedBy="paymentMean") Set<Charge> charges = new HashSet<>();
	
	Voucher() {}
	
	public Voucher(String code) {
		super();
		this.code = code;
	}

	public Voucher(String code, String description, double available) {
		this(code);
		this.description = description;
		this.available = available;
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {

	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(double available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public Client getClient() {
		return client;
	}

	@Override
	void _setClient(Client client) {
		this.client = client;
	}

	@Override
	public Set<Charge> getCharges() {
		return new HashSet<> ( charges );
	}

	@Override
	Set<Charge> _getCharges() {
		return charges;
	}

}
