package cl.usach.sd.interfaz;

import java.rmi.Remote;
import java.util.List;

import cl.usach.sd.dto.DTOLiquidacion;

public interface InterfazRemotaContabilidad  extends Remote {
	
	public List<DTOLiquidacion> listaLiquidaciones() throws Exception;
	public String crearLiquidacion(DTOLiquidacion dtoEmpleado) throws Exception;
	public int editarLiquidacion(DTOLiquidacion dtoEmpleado) throws Exception;
	public String borrarLiquidacion(String rut) throws Exception;

}
