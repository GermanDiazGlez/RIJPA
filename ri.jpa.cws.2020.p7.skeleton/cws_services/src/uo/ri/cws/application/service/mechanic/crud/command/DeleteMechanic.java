package uo.ri.cws.application.service.mechanic.crud.command;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import uo.ri.cws.application.service.BusinessException;

public class DeleteMechanic implements Command<>{

	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {

		MechanicRepository repo = Factory.repository.forMechanic();
		
		
		
		Optional<Mechanic> om = repo.findById(mechanicId);
		BusinessChecks.isTrue(om.isPresent(),"Mechanic does not exist");
		Mechanic m = om.get();
		BusinessChecks.isTrue(m.getInterventions().isEmpty(),"Mechanic has interventions");
		
		repo.remove(m);
		
		return null;
	}

}
