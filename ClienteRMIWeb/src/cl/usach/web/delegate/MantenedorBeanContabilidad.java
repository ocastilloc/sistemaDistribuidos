package cl.usach.web.delegate;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import cl.usach.sd.dto.DTOLiquidacion;
import cl.usach.sd.interfaz.InterfazRemotaContabilidad;

@ManagedBean(name="mantenedorBeanContabilidad")
@RequestScoped
public class MantenedorBeanContabilidad {
	
	public MantenedorBeanContabilidad() {
		
	}
	
	public List<DTOLiquidacion> listaLiquidacion() throws Exception{
		try{
			int resultado = (int) (System.currentTimeMillis() % 2);
			String ip="10.13.200.210";
			if(resultado==0){
				ip="10.13.200.209";
			}
			Registry registry;
			registry = LocateRegistry.getRegistry(ip);
			
			InterfazRemotaContabilidad contabilidadRemoto = (InterfazRemotaContabilidad) registry.lookup("contabilidad");
			List<DTOLiquidacion> lista = contabilidadRemoto.listaLiquidaciones();
			return lista;
		} catch (RemoteException e) {
			throw new Exception("Error al intentar comunicar con el servidor RMI : "+e.getMessage());
		}catch(NotBoundException ee){
			throw new Exception("Error problema con el nombre del servidor RMI registrado : "+ee.getMessage());
		}catch(Exception eee){
			throw new Exception("Error general del cliente RMI : "+eee.getMessage());
		}		
	}
	
	public String crearLiquidacion(DTOLiquidacion dtoLiquidacion) throws Exception{
		try{
			Registry registry;
			registry = LocateRegistry.getRegistry("10.13.200.210");
			InterfazRemotaContabilidad contabilidadRemoto = (InterfazRemotaContabilidad) registry.lookup("contabilidad");
			String lista = contabilidadRemoto.crearLiquidacion(dtoLiquidacion);
			return lista;
		} catch (RemoteException e) {
			throw new Exception("Error al intentar comunicar con el servidor RMI : "+e.getMessage());
		}catch(NotBoundException ee){
			throw new Exception("Error problema con el nombre del servidor RMI registrado : "+ee.getMessage());
		}catch(Exception eee){
			throw new Exception("Error general del cliente RMI : "+eee.getMessage());
		}		
	}
	
	public String eliminarLiquidacion(String rut) throws Exception{
		try{
			Registry registry;
			registry = LocateRegistry.getRegistry("10.13.200.210");
			InterfazRemotaContabilidad contabilidadRemoto = (InterfazRemotaContabilidad) registry.lookup("contabilidad");
			String lista = contabilidadRemoto.borrarLiquidacion(rut);
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
