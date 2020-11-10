package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
public class VehicleType extends BaseEntity{
	// natural attributes
	@Column(unique=true) private String name;
	private double pricePerHour;

	// accidental attributes
	@OneToMany (mappedBy="type") private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {}

	public VehicleType(String name) {
		super();
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}


	public VehicleType(String name, double pricePerHour) {
		this(name);
		ArgumentChecks.isNotNull(pricePerHour);
		this.pricePerHour = pricePerHour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour + "]";
	}

	
	
	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
