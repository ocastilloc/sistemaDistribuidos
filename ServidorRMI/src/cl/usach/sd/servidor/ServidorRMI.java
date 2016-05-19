package cl.usach.sd.servidor;


import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import cl.usach.sd.dto.DTOEmpleado;
import cl.usach.sd.dto.DTOLiquidacion;
import cl.usach.sd.exception.ExisteException;
import cl.usach.sd.gestion.ConexionBaseDatos;
import cl.usach.sd.interfaz.InterfazRemotaContabilidad;
import cl.usach.sd.interfaz.InterfazRemotaRrhh;
import cl.usach.sd.interfaz.InterfazRemotaUno;

public class ServidorRMI {

	public static void main(String[] args) throws Exception {
		
		try{
			
			System.setProperty("java.rmi.server.hostname", "10.13.200.209"); 
			
			Remote stub = UnicastRemoteObject.exportObject(new InterfazRemotaUno() {	
				@Override
				public String sueldoTrabajador(String rut) throws Exception {
					 //Probar conexion Base de Datos
			        ConexionBaseDatos conexion = new ConexionBaseDatos();
			        String sueldo = conexion.obtenerSalario(rut);
					return sueldo;
				}
			}, 0);
			
			Remote contabilidad =  UnicastRemoteObject.exportObject(new InterfazRemotaContabilidad() {
				
				@Override
				public List<DTOLiquidacion> listaLiquidaciones() throws Exception {
					 
					ConexionBaseDatos conexion = new ConexionBaseDatos();
					List<DTOLiquidacion > lista = conexion.listaLiquidaciones();
					return lista;
					
				}
				
				@Override
				public int editarLiquidacion(DTOLiquidacion dtoEmpleado) throws Exception {
					return 0;
				}
				
				@Override
				public String crearLiquidacion(DTOLiquidacion dtoLiquidacion) throws Exception {
					ConexionBaseDatos conexion = new ConexionBaseDatos();
					String lista = conexion.crearLiquidacion(dtoLiquidacion);
					return lista;
				}
				
				@Override
				public String borrarLiquidacion(String rut) throws Exception {
					ConexionBaseDatos conexion = new ConexionBaseDatos();
					DTOLiquidacion liquidacion = new DTOLiquidacion();
					liquidacion.setRut(rut);
					String lista = conexion.eliminarLiquidacion(liquidacion);
					return lista;
				}
			},0);
			
			Remote rrhh =  UnicastRemoteObject.exportObject(new InterfazRemotaRrhh() {
				
				@Override
				public List<DTOEmpleado> listaEmpleados() throws Exception {
					 
					ConexionBaseDatos conexion = new ConexionBaseDatos();
					List<DTOEmpleado> lista = conexion.listaEmpleados();
					return lista;
				}
				
				@Override
				public int editarEmpleado(DTOEmpleado dtoEmpleado) throws Exception {
					return 0;
				}
				
				@Override
				public String crearEmpleado(DTOEmpleado dtoEmpleado) throws ExisteException, Exception {
					ConexionBaseDatos conexion = new ConexionBaseDatos();
					String lista = conexion.crearEmpleado(dtoEmpleado);
					return lista;
				}
				
				@Override
				public String borrarEmpleado(String rut) throws Exception {
					ConexionBaseDatos conexion = new ConexionBaseDatos();
					DTOEmpleado empleado = new DTOEmpleado();
					empleado.setRut(rut);
					String lista = conexion.eliminarEmpleado(empleado);
					return lista;
				}
			},0);
			
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	        registry.bind("contabilidad", contabilidad);
	        registry.bind("rrhh",rrhh);
	        registry.bind("Test", stub);
	        
	        System.out.println("---------------------");
	        System.out.println("SERVIDOR RMI INICIADO");
	        System.out.println("---------------------");
	       
	        
		}catch(Exception e){
			throw new Exception("Error al iniciar el servicio RMI "+e.getMessage());
		}
	
                
	}

}
