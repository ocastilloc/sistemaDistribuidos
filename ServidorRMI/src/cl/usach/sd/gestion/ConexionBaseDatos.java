package cl.usach.sd.gestion;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cl.usach.sd.dto.DTOEmpleado;
import cl.usach.sd.dto.DTOLiquidacion;
import cl.usach.sd.exception.ExisteException;

public class ConexionBaseDatos {
	
	public ConexionBaseDatos() {
		
	}
	
	public Connection multiBaseDatos(String baseDatos) throws ClassCastException, SQLException,Exception{
		
		Connection conexion = null;
		boolean estado=true;
		try{
			//cargar driver
			Class.forName("org.postgresql.Driver");
			//conectar con la base de datos
			String urlBaseDatos = "jdbc:postgresql://10.13.200.232:5432/"+baseDatos;
			//parametros conexion
			Properties parametros = new Properties();
			parametros.put("user", "laboratorio");
			parametros.put("password", "lab2016");
			conexion = DriverManager.getConnection(urlBaseDatos, parametros);
			System.out.println("CONEXION EXITOSA "+baseDatos);
		}catch(ClassNotFoundException e){
			 estado=false;
			 throw new ClassNotFoundException("Error al cargar el driver : "+e.getMessage());
		}catch(SQLException ee){
			 estado=false;
			 throw new ClassNotFoundException("Error al conectar con la base de datos : "+ee.getMessage());
		}catch(Exception eee){
			 estado=false; 
			 throw new ClassNotFoundException("Error general : "+eee.getMessage());
		}finally{
			if(!estado){
				//cargar driver
				Class.forName("org.postgresql.Driver");
				//conectar con la base de datos
				String urlBaseDatos = "jdbc:postgresql://10.13.200.233:5432/"+baseDatos;
				Properties parametros = new Properties();
				parametros.put("user", "laboratorio");
				parametros.put("password", "lab2016");
				conexion = DriverManager.getConnection(urlBaseDatos, parametros);
				System.out.println("CONEXION EXITOSA "+baseDatos);
			}
		}
		
		return conexion;
	}
	
    public Connection multiBaseDatos(String baseDatos,String ipServidor) throws ClassCastException, SQLException,Exception{
		
		Connection conexion = null;
		boolean estado=true;
		try{
			//cargar driver
			Class.forName("org.postgresql.Driver");
			//conectar con la base de datos
			String urlBaseDatos = "jdbc:postgresql://"+ipServidor+":5432/"+baseDatos;
			//parametros conexion
			Properties parametros = new Properties();
			parametros.put("user", "laboratorio");
			parametros.put("password", "lab2016");
			conexion = DriverManager.getConnection(urlBaseDatos, parametros);
			System.out.println("CONEXION EXITOSA "+baseDatos);
		}catch(ClassNotFoundException e){
			 estado=false;
			 throw new ClassNotFoundException("Error al cargar el driver : "+e.getMessage());
		}catch(SQLException ee){
			 estado=false;
			 throw new ClassNotFoundException("Error al conectar con la base de datos : "+ee.getMessage());
		}catch(Exception eee){
			 estado=false; 
			 throw new ClassNotFoundException("Error general : "+eee.getMessage());
		}finally{
			if(!estado){
				//cargar driver
				Class.forName("org.postgresql.Driver");
				//conectar con la base de datos
				String urlBaseDatos = "jdbc:postgresql://"+ipServidor+":5432/"+baseDatos;
				Properties parametros = new Properties();
				parametros.put("user", "laboratorio");
				parametros.put("password", "lab2016");
				conexion = DriverManager.getConnection(urlBaseDatos, parametros);
				System.out.println("CONEXION EXITOSA "+baseDatos);
			}
		}
		
		return conexion;
	}
		
	public Connection baseDatos() throws ClassNotFoundException, SQLException ,Exception{
		
		Connection conexion=null;
		try{
			//cargar driver
			Class.forName("org.postgresql.Driver");
			//conectar con la base de datos
			String urlBaseDatos = "jdbc:postgresql://10.13.200.232:5432/rrhh";
			Properties parametros = new Properties();
			parametros.put("user", "laboratorio");
			parametros.put("password", "lab2016");
			conexion = DriverManager.getConnection(urlBaseDatos, parametros);
			System.out.println("CONEXION EXITOSA");
		}catch(ClassNotFoundException e){
			 throw new ClassNotFoundException("Error al cargar el driver : "+e.getMessage());
		}catch(SQLException ee){
			 throw new ClassNotFoundException("Error al conectar con la base de datos : "+ee.getMessage());
		}catch(Exception eee){
			 throw new ClassNotFoundException("Error general : "+eee.getMessage());
		}
	    return conexion;
	}
	
	public String obtenerSalario(String rut) throws Exception{
		
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		PreparedStatement st = conexion.baseDatos().prepareStatement("SELECT e.\"EMP_SALARIO\" FROM rrhh.\"RH_EMPLEADO\" e WHERE e.\"EMP_RUT\" = ?");
		st.setString(1, rut);
		ResultSet rs = st.executeQuery();
		String salario = new String();
		while (rs.next()){
			salario = String.valueOf(rs.getLong(1));
		}
		rs.close();
		st.close(); 
		return salario;
		
	}
	
	public List<DTOEmpleado> listaEmpleados() throws Exception{
		
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		PreparedStatement st = conexion.multiBaseDatos("rrhh").prepareStatement("SELECT * FROM rrhh.\"RH_EMPLEADO\" ");
		ResultSet rs = st.executeQuery();
		
		String rut=null;
		String nombres=null;
		String paterno=null;
		String materno=null;
		
		List<DTOEmpleado> listaEmpleado = new ArrayList<>();
		while (rs.next()){
			DTOEmpleado dtoEmpleado = new DTOEmpleado();
			rut = String.valueOf(rs.getString(1));
			nombres = String.valueOf(rs.getString(2));
			paterno = String.valueOf(rs.getString(3));
			materno = String.valueOf(rs.getString(4));
			dtoEmpleado.setRut(rut);
			dtoEmpleado.setNombre(nombres);
			dtoEmpleado.setPaterno(paterno);
			dtoEmpleado.setMaterno(materno);
			listaEmpleado.add(dtoEmpleado);
		}
		rs.close();
		st.close(); 
		conexion.multiBaseDatos("rrhh").close();
		return listaEmpleado;
	}
	
    public List<DTOLiquidacion> listaLiquidaciones() throws Exception{
		
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		PreparedStatement st = conexion.multiBaseDatos("contabilidad").prepareStatement("SELECT e.\"EMP_RUT\", e.\"CON_MONTO\" FROM conta.\"CT_LIQUIDACION\" e");
		ResultSet rs = st.executeQuery();
		
		String rut=null;
		long monto=0L;
	
		List<DTOLiquidacion> listaLiquidacion = new ArrayList<>();
		while (rs.next()){
			DTOLiquidacion dtoLiquidacion = new DTOLiquidacion();
			rut = String.valueOf(rs.getString(1));
			monto = rs.getLong(2);
			dtoLiquidacion.setRut(rut);
			dtoLiquidacion.setMonto(monto);
			
			listaLiquidacion.add(dtoLiquidacion);
		}
		rs.close();
		st.close(); 
		conexion.multiBaseDatos("contabilidad").close();
		
		return listaLiquidacion;
	}
    
    public String crearEmpleado(DTOEmpleado empleado) throws ExisteException, Exception{
    	
    	Connection conexion =null;
    	PreparedStatement pt=null;
    	String estado="1";
    	try{
    		//Validar que el rut no exista
    		conexion = this.multiBaseDatos("rrhh","10.13.200.232");
        	
    		//validar, si existe empleado lanzar excepcion para no insertarlo
    		String sqlRut="SELECT COUNT(*) FROM rrhh.\"RH_EMPLEADO\" WHERE \"EMP_RUT\" = ? ";
    		PreparedStatement ste = conexion.prepareStatement(sqlRut);
    		ste.setString(1,empleado.getRut());
    		ResultSet rs = ste.executeQuery();
    		int existeRut = 0;
    		while (rs.next()){
    			existeRut = rs.getInt(1);
    		}
    		if(existeRut>0){
    			//throw new ExisteException("El empleado ya fue creado por otro usuario");
    		    estado="El empleado ya fue creado por otro usuario";
    		    return estado;
    		}
    		ste.close();
    		
    		
    		conexion.setAutoCommit(false);
        	String SQL = "INSERT INTO rrhh.\"RH_EMPLEADO\" ( \"EMP_RUT\", \"EMP_NOMBRES\",\"EMP_PATERNO\",\"EMP_MATERNO\") VALUES(?,?,?,?) ";
        	pt =  conexion.prepareStatement(SQL);
        	pt.setString(1,empleado.getRut());
            pt.setString(2,empleado.getNombre());
            pt.setString(3,empleado.getPaterno());
            pt.setString(4,empleado.getMaterno());
            
            //Thread.sleep(60000);
            
            pt.executeUpdate(); 
            conexion.commit();
        
    	}catch(Exception e){
            estado="0";
    		conexion.rollback();
    		throw new Exception(e.getCause());
    	}
    	
    	return estado;
    }
    
 public String crearLiquidacion(DTOLiquidacion liquidacion) throws Exception{
    	
    	Connection conexion =null;
    	Connection conexionRrhh =null;
    	PreparedStatement pt=null;
    	String estado="1";
    	try{
    		
    		
    		//Validar que la persona exista en recursos humanos
    		conexionRrhh = this.multiBaseDatos("rrhh","10.13.200.232");
    		String sqlRut="SELECT COUNT(*) FROM rrhh.\"RH_EMPLEADO\" WHERE \"EMP_RUT\" = ? ";
    		PreparedStatement ste = conexionRrhh.prepareStatement(sqlRut);
    		ste.setString(1,liquidacion.getRut());
    		ResultSet rs = ste.executeQuery();
    		int existeRut = 0;
    		while (rs.next()){
    			existeRut = rs.getInt(1);
    		}
    		ste.close();
    		if(existeRut==0){
    			//throw new ExisteException("El empleado ya fue creado por otro usuario");
    		    estado="No es posible ingresar la liquidación, ya que el empleado no existe en Recursos Humanos.";
    		    conexionRrhh.close();
    		    return estado;
    		}
    		
    		
    		conexion = this.multiBaseDatos("contabilidad","10.13.200.232");
    		
    		//validar que el rut no este en la base de contabilidad
    		String sqlRutconta="SELECT COUNT(*) FROM conta.\"CT_LIQUIDACION\" WHERE \"EMP_RUT\" = ? ";
    		PreparedStatement stc = conexion.prepareStatement(sqlRutconta);
    		stc.setString(1,liquidacion.getRut());
    		ResultSet rsc = stc.executeQuery();
    		int existeRutConta = 0;
    		while (rsc.next()){
    			existeRutConta = rsc.getInt(1);
    		}
    		stc.close();
    		if(existeRutConta>0){
    			//throw new ExisteException("El empleado ya fue creado por otro usuario");
    		    estado="No es posible ingresar la liquidación, ya que el empleado ya posee una liquidación.";
    		    conexion.close();
    		    return estado;
    		}
    		
    	
        	conexion.setAutoCommit(false);
        	String SQL = "INSERT INTO conta.\"CT_LIQUIDACION\" ( \"EMP_RUT\", \"CON_MONTO\") VALUES(?,?) ";
        	pt =  conexion.prepareStatement(SQL);
        	pt.setString(1,liquidacion.getRut());
            pt.setLong(2,liquidacion.getMonto());
            pt.executeUpdate(); 
            conexion.commit();
        
    	}catch(Exception e){
            estado="0";
    		conexion.rollback();
    		throw new Exception(e.getCause());
    	}
    	
    	return estado;
    }
 
    public String eliminarLiquidacion(DTOLiquidacion liquidacion) throws Exception{
    	
    	Connection conexion =null;
    	PreparedStatement pt=null;
    	String estado="1";
    	
    	try{    		
    		conexion = this.multiBaseDatos("contabilidad","10.13.200.232");
        	conexion.setAutoCommit(false);
        	String SQL = "DELETE FROM conta.\"CT_LIQUIDACION\" WHERE \"EMP_RUT\" = ? ";
        	pt =  conexion.prepareStatement(SQL);
        	pt.setString(1,liquidacion.getRut());
            pt.executeUpdate(); 
            conexion.commit();
        
    	}catch(Exception e){
            estado="0";
    		conexion.rollback();
    		throw new Exception(e.getCause());
    	}
    	
    	return estado;
	 
    }
    
    public String eliminarEmpleado(DTOEmpleado empleado) throws Exception{
    	
    	Connection conexion =null;
    	PreparedStatement pt=null;
    	String estado="1";
    	
    	try{    		
    		conexion = this.multiBaseDatos("rrhh","10.13.200.232");
        	conexion.setAutoCommit(false);
        	String SQL = "DELETE FROM rrhh.\"RH_EMPLEADO\" WHERE \"EMP_RUT\" = ? ";
        	pt =  conexion.prepareStatement(SQL);
        	pt.setString(1,empleado.getRut());
            pt.executeUpdate(); 
            conexion.commit();
            
            //Ademas eliminar la liquidación del trabajador
            DTOLiquidacion liquidacion = new DTOLiquidacion();
            liquidacion.setRut(empleado.getRut());
            this.eliminarLiquidacion(liquidacion);
        
    	}catch(Exception e){
            estado="0";
    		conexion.rollback();
    		throw new Exception(e.getCause());
    	}
    	
    	return estado;
	 
    }

}