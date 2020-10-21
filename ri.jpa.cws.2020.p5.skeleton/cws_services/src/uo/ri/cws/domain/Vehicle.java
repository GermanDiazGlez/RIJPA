package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import sun.awt.windows.WPrinterJob;

public class Vehicle {
	private String plateNumber;
	private String make;
	private String model;
	
	//Atributos accidentales
	private Client client;
	private VehicleType vehicleType;
	private Set<WorkOrder> workOrders = new HashSet<>();
	

	public Vehicle(String plateNumber) {
		super();
		ArgumentChecks.isNotEmpty(plateNumber);
		this.plateNumber = plateNumber;
	}


	public Vehicle(String plateNumber, String make, String model) {
		this(plateNumber);
		ArgumentChecks.isNotEmpty(make);
		ArgumentChecks.isNotEmpty(model);
		this.make = make;
		this.model = model;
	}


	public String getPlateNumber() {
		return plateNumber;
	}


	public String getMake() {
		return make;
	}


	public String getModel() {
		return model;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plateNumber == null) ? 0 : plateNumber.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (plateNumber == null) {
			if (other.plateNumber != null)
				return false;
		} else if (!plateNumber.equals(other.plateNumber))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
	}


	void _setClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return client;
	}
	
	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}
	
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}


}
