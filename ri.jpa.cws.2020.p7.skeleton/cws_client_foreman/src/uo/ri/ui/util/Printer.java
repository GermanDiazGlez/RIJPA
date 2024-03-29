package uo.ri.ui.util;

import alb.util.console.Console;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class Printer {

	public static void printWorkOrderDetail(WorkOrderDto wo) {

		Console.printf("%s for vehicle %s\n\t%-25.25s\n\t%tm/%<td/%<tY\n\t%s\n",
				wo.id
				, wo.vehicleId
				, wo.description
				, wo.date
				, wo.status
			);
	}

	public static void printVehicleDetail(VehicleDto v) {

		Console.printf("%s\t%-8.8s\t%s\t%s\n",
				v.id
				, v.plate
				, v.make
				, v.model
			);
	}

}
