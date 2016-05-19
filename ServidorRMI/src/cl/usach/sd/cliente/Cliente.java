package cl.usach.sd.cliente;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import cl.usach.sd.interfaz.InterfazRemotaUno;

public class Cliente {
	public static void main(String[] args) throws Exception {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("192.168.1.2");
			InterfazRemotaUno testRemote = (InterfazRemotaUno) registry.lookup("Test");
		    System.out.println(testRemote.sueldoTrabajador("13974888-3"));
		} catch (RemoteException e) {
			System.out.println("Error al intentar comunicar con el servidor RMI ");
			System.out.println("Detalle "+e.getMessage());
		}catch(NotBoundException ee){
			System.out.println("Error problema con el nombre del servidor RMI registrado ");
			System.out.println("Detalle "+ee.getMessage());
		}catch(Exception eee){
			System.out.println("Error general del cliente RMI");
			System.out.println("Detalle "+eee.getMessage());
		}		
	}
}