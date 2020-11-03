package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.sun.jdi.connect.Connector.Argument;

import alb.util.assertion.ArgumentChecks;
import alb.util.date.Dates;
import alb.util.math.Round;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

@Entity
public class Invoice {
	public enum InvoiceStatus { NOT_YET_PAID, PAID }

	// natural attributes
	@Column (unique = true)
	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	// accidental attributes
	@OneToMany (mappedBy="invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany (mappedBy="invoice")
	private Set<Charge> charges = new HashSet<>();
	
	Invoice() {}

	public Invoice(Long number) {
		// call full constructor with sensible defaults
		this(number, LocalDate.now(), List.of());
	}

	public Invoice(Long number, LocalDate date) {
		// call full constructor with sensible defaults
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(date);
		this.number = number;
		this.date = LocalDate.now();
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		// check arguments (always), through IllegalArgumentException
		// store the number
		// store a copy of the date
		// add every work order calling addWorkOrder( w )
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrders);
		this.number = number;
		this.date = LocalDate.now();
		for (WorkOrder w : workOrders)
			this.addWorkOrder(w);
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		LocalDate JULY_1_2012 = LocalDate.of(2012, 7, 1);
		vat = date.isAfter(JULY_1_2012) ? 0.21 : 0.18;
			
		amount = 0L;
		for (WorkOrder w : workOrders) {
			amount += w.getAmount();
		}
		amount *= 1L + vat / 100L;
		amount = Round.twoCents(amount);
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if (!this.getStatus().equals(InvoiceStatus.NOT_YET_PAID)) {
			throw new IllegalStateException("La factura esta sin abonar.");
		}
		if (!workOrder.getStatus().equals(WorkOrderStatus.FINISHED)) {
			throw new IllegalStateException("La averia esta abierta.");
		}
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		this.computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (!this.getStatus().equals(InvoiceStatus.NOT_YET_PAID)) {
			throw new IllegalStateException("La factura no puede estar sin abonar.");
		}
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		this.computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {

	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Invoice other = (Invoice) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", status="
				+ status + "]";
	}
	

}
