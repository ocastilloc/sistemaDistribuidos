package cl.usach.web.delegate;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import cl.usach.sd.dto.DTOEmpleado;
import cl.usach.sd.exception.ExisteException;
import cl.usach.sd.interfaz.InterfazRemotaRrhh;
import cl.usach.sd.interfaz.InterfazRemotaUno;

@ManagedBean(name="mantenedorBeanRhh")
@RequestScoped

public class MantenedorBeanRrhh {
	
	public MantenedorBeanRrhh() {
		
	}
	
	public String obtenerSueldo(String rut) throws Exception{
		try{
			Registry registry;
			registry = LocateRegistry.getRegistry("10.13.200.210");
			InterfazRemotaUno testRemote = (InterfazRemotaUno) registry.lookup("Test");
			return testRemote.sueldoTrabajador(rut);
		} catch (RemoteException e) {
			throw new Exception("Error al intentar comunicar con el servidor RMI : "+e.getMessage());
		}catch(NotBoundException ee){
			throw new Exception("Error problema con el nombre del servidor RMI registrado : "+ee.getMessage());
		}catch(Exception eee){
			throw new Exception("Error general del cliente RMI : "+eee.getMessage());
		}		
		
	}
	
	public List<DTOEmpleado> listaEmpleados() throws Exception{
		try{
			Registry registry;
			registry = LocateRegistry.getRegistry("10.13.200.210");
			InterfazRemotaRrhh rrhhRemoto = (InterfazRemotaRrhh) registry.lookup("rrhh");
			List<DTOEmpleado> lista = rrhhRemoto.listaEmpleados();
			return lista;
		} catch (RemoteException e) {
			throw new Exception("Error al intentar comunicar con el servidor RMI : "+e.getMessage());
		}catch(NotBoundException ee){
			throw new Exception("Error problema con el nombre del servidor RMI registrado : "+ee.getMessage());
		}catch(Exception eee){
			throw new Exception("Error general del cliente RMI : "+eee.getMessage());
		}		
	}
	
	public String crearEmpleado(DTOEmpleado dtoEmpleado) throws ExisteException,Exception{
		try{
			Registry registry;
			registry = LocateRegistry.getRegistry("10.13.200.210");
			//Thread.sleep(60000);
			InterfazRemotaRrhh rrhhRemoto = (InterfazRemotaRrhh) registry.lookup("rrhh");
			String lista = rrhhRemoto.crearEmpleado(dtoEmpleado);
			return lista;
			
		} catch (RemoteException e) {
			throw new Exception("Error al intentar comunicar con el servidor RMI : "+e.getMessage());
		}catch(NotBoundException ee){
			throw new Exception("Error problema con el nombre del servidor RMI registrado : "+ee.getMessage());
		}catch(Exception eee){
			throw new Exception("Error general del cliente RMI : "+eee.getMessage());
		}		
	}
	
	public String eliminarEmpleado(String rut) throws ExisteException,Exception{
		try{
			Registry registry;
			registry = LocateRegistry.getRegistry("10.13.200.210");
			InterfazRemotaRrhh rrhhRemoto = (InterfazRemotaRrhh) registry.lookup("rrhh");
			String lista = rrhhRemoto.borrarEmpleado(rut);
			return lista;
		} catch (RemoteException e) {
			throw new Exception("Error al intentar comunicar con el servidor RMI : "+e.getMessage());
		}catch(NotBoundException ee){
			throw new Exception("Error problema con el nombre del servidor RMI registrado : "+ee.getMessage());
		}catch(Exception eee){
			throw new Exception("Error general del cliente RMI : "+eee.getMessage());
		}		
	}

}
