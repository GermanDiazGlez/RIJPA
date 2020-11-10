package uo.ri.cws.application.service.mechanic.crud.command;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.eclipse.persistence.jpa.config.Entity;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class AddMechanic implements Command<MechanicDto>{

	private MechanicDto dto;

	public AddMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {

		Optional<Mechanic> om = em.createNamedQuery("Mechanic.findByDni", Mechanic.class)
							.setParameter(1, dto.dni)
							.getResultList()
							.getResultStream()
							.findFirst();
	
		BusinessCheck.isTrue(om.isEmpty(),"Mechanic already exists");
		
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		
		dto.id = m.getId();
		
		repo.add(m);
		
		return dto;
	}

}
