package cl.usach.sd.interfaz;


import java.rmi.Remote;


public interface InterfazRemotaUno extends Remote{
	
	public String sueldoTrabajador(String rut) throws Exception;

	
}
