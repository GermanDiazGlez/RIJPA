package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.domain.Mechanic;

public class UpdateMechanic implements Command<Void>{

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {

		Mechanic m = em.find(Mechanic.class, id);
		BusinessChecks.isTrue(m!=null,"Mechanic does not exist");
		m.setName(dto.name);
		m.setSurname(dto.surname);
		
		return null;
	}

}
