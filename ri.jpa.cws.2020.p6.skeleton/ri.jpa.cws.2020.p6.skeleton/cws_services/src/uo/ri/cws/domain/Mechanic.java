package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import alb.util.assertion.ArgumentChecks;

@Entity
public class Mechanic {
	// natural attributes
	@Column (unique = true)
	private String dni;
	private String surname;
	private String name;

	// accidental attributes
	@OneToMany (mappedBy = "mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();
	
	@OneToMany (mappedBy = "mechanic") 
	private Set<Intervention> interventions = new HashSet<>();
	
	Mechanic() {}
	
	public Mechanic(String dni) {
		super();
		this.dni = dni;
	}

	public Mechanic(String dni, String surname, String name) {
		this(dni);
		ArgumentChecks.isNotEmpty(surname);
		ArgumentChecks.isNotEmpty(name);
		this.surname = surname;
		this.name = name;
	}



	public Set<WorkOrder> getAssigned() {
		return new HashSet<>( assigned );
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Mechanic other = (Mechanic) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}



	public String getDni() {
		return dni;
	}



	public String getSurname() {
		return surname;
	}



	public String getName() {
		return name;
	}



	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name=" + name + "]";
	}

	
}
