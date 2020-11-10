package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.Invoice.InvoiceStatus;

@Entity
public class Charge {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;
	
	Charge() {}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this.amount = amount;
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		paymentMean.pay( amount );
		// link invoice, this and paymentMean
		Associations.Charges.link(paymentMean, this, invoice);
		
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// assert the invoice is not in PAID status
		StateChecks.isTrue(InvoiceStatus.NOT_YET_PAID.equals(invoice.getStatus()) , "Invoice cant be paid");
		// decrement the payment mean accumulated ( paymentMean.pay( -amount) )
		paymentMean.pay(-amount);
		// unlink invoice, this and paymentMean
		Associations.Charges.unlink(this);
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}
	
	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}
	
	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}


	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice + ", paymentMean=" + paymentMean + "]";
	}

	
}
