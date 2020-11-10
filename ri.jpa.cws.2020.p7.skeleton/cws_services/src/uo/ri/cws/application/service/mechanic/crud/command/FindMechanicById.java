package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.domain.Mechanic;

public class FindMechanicById implements Command<Optional<Mechanic>>{

	private String id;

	public FindMechanicById(String id) {
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {

		Mechanic m = em.find(Mechanic.class, id);
		
		return Optional.ofNullable(DtoAssembler.toDto(m));
	}

}
