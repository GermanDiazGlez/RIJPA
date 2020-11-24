package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TORDERS")
public class Order extends BaseEntity{
	@Column (unique = true)
	private String code;
	private LocalDate orderedDate;
	private LocalDate receptionDate;
	private double amount;
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	//Atributos accidentales
	@ManyToOne private Provider provider;
	
	@ElementCollection
	@CollectionTable(name="TOrderLines")
	private Set<OrderLine> lines = new HashSet<>();
	
	public Order() {}
	
	public Order(String code) {
		ArgumentChecks.isNotNull(code);
		this.code = code;
		this.orderedDate = LocalDate.now();
		this.status = StatusEnum.PENDING;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	public LocalDate getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(LocalDate receptionDate) {
		this.receptionDate = receptionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Provider getProvider() {
		return provider;
	}

	void _setProvider(Provider provider) {
		this.provider = provider;
	}

	public Set<OrderLine> getLines() {
		return new HashSet<> ( lines );
	}
	
	public Set<OrderLine> getOrderLines() {
		return new HashSet<> ( lines );
	}

	public void setLines(Set<OrderLine> lines) {
		this.lines = lines;
	}
	
	public boolean isPending() {
		return status.equals(StatusEnum.PENDING) ? true : false;
	}

	public void receive() {
		this.receptionDate = LocalDate.now();
		StateChecks.isTrue(this.status == StatusEnum.PENDING, "This order is PENDING");
		for (OrderLine orderLine : lines) {
			orderLine.getSparePart().setPrice(calculateNewPrice(orderLine));
			orderLine.getSparePart().setStock(orderLine.getQuantity() + orderLine.getSparePart().getStock());
		}
		this.status = StatusEnum.RECEIVED;
	}

	private double calculateNewPrice(OrderLine ol) {
		int previousStock = ol.getSparePart().getStock();
		int maxStock = ol.getSparePart().getMaxStock();
		double purchasePrice = ol.getPrice();
		double previousPrice = ol.getSparePart().getPrice();
		
		double newPrice = (previousStock * previousPrice
				+ 1.2 * purchasePrice * (maxStock - previousStock)
			)
			/ maxStock;
		return newPrice;
	}
	
	private double calculateAmount() {
		double amount = 0;
		for (OrderLine orderLine : lines) {
			amount = orderLine.getPrice() * orderLine.getQuantity();
		}
		return amount;
	}
	
	public void setAmount() {
		this.amount = calculateAmount();
	}

	public void addSparePartFromSupply(Supply supply) {
		ArgumentChecks.isNotNull(supply);
		
		for (OrderLine orderLine : lines) {
			StateChecks.isFalse(orderLine.getSparePart() == supply.getSparePart());
		}
		
		if(supply.getSparePart().getStock() < supply.getSparePart().getMinStock()) {
			OrderLine orderLine = new OrderLine(supply.getSparePart(), supply.getPrice());
			this.lines.add(orderLine);
			this.amount += orderLine.getAmount();
		}

		this.receptionDate = LocalDate.now();
		
	}

	public boolean isReceived() {
		return status.equals(StatusEnum.RECEIVED) ? true : false;
	}

	public void removeSparePart(SparePart sp) {
		ArgumentChecks.isNotNull(sp);
		for (OrderLine orderLine : lines) {
			if(orderLine.getSparePart().getCode().equals(sp.getCode())) {
				lines.remove(orderLine);
			}
		}
	}
	
}
