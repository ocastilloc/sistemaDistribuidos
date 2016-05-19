package cl.usach.sd.interfaz;

import java.rmi.Remote;
import java.util.List;

import cl.usach.sd.dto.DTOEmpleado;
import cl.usach.sd.exception.ExisteException;

public interface InterfazRemotaRrhh  extends Remote {
	
	public List<DTOEmpleado> listaEmpleados() throws Exception;
	public String crearEmpleado(DTOEmpleado dtoEmpleado) throws ExisteException, Exception;
	public int editarEmpleado(DTOEmpleado dtoEmpleado) throws Exception;
	public String borrarEmpleado(String rut) throws Exception;

}
