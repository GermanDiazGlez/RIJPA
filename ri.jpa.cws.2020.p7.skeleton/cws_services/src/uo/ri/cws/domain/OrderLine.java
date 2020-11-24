package uo.ri.cws.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import alb.util.assertion.ArgumentChecks;

@Embeddable
public class OrderLine {
	private double price;
	private int quantity;

	
	// accidental attributes
	@ManyToOne
	private SparePart sparePart;
	
	OrderLine() {}
	
	public OrderLine(double price, int quantity) {
		ArgumentChecks.isNotNull(price);
		ArgumentChecks.isNotNull(quantity);
		this.price = price;
		this.quantity = quantity;
	}


	public OrderLine(SparePart sparePart, double supplyPrice) {
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isNotNull(supplyPrice);
		ArgumentChecks.isTrue(sparePart.getStock()<sparePart.getMinStock());
		this.price = supplyPrice;
		this.sparePart = sparePart;
		this.quantity = sparePart.getQuantityToOrder();
	}
	
	/*
	 * orderLine.receive() makes spare parts to be updated for stock and price
	 */
	public void receive() {
		this.getSparePart().setPrice(calculateNewPrice());
		this.getSparePart().stock += quantity;
		
	}
	
	private double calculateNewPrice() {
		int previousStock = getSparePart().getStock();
		int maxStock = getSparePart().getMaxStock();
		double purchasePrice = getPrice();
		double previousPrice = getSparePart().getPrice();
		
		double newPrice = (previousStock * previousPrice
				+ 1.2 * purchasePrice * (maxStock - previousStock)
			)
			/ maxStock;
		return newPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	void _setSparePart(SparePart sp) {
		this.sparePart = sp;
	}
	
	public SparePart getSparePart() {
		return sparePart;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "OrderLines [price=" + price + ", quantity=" + quantity + "]";
	}

	public double getAmount() {
		double amount = 0;
		amount = quantity * price;
		return amount;
	}

}
