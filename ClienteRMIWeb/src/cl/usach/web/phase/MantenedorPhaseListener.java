package cl.usach.web.phase;

import java.io.IOException;
import java.io.StringWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cl.usach.sd.dto.DTOEmpleado;
import cl.usach.sd.dto.DTOLiquidacion;
import cl.usach.sd.exception.ExisteException;
import cl.usach.web.delegate.MantenedorBeanContabilidad;
import cl.usach.web.delegate.MantenedorBeanRrhh;

public class MantenedorPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacesContext contextBean;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Object object;
	private JSONObject elementoJson = null;
	private StringWriter out=null;
	private String jsonText = null;
	
	public void setResponse(FacesContext context) {
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
	}
	
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setRequest(FacesContext context) {
		this.request = (HttpServletRequest)context.getExternalContext().getRequest();
	}
	
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setObject(FacesContext context) {
		this.object = context.getExternalContext().getRequest();
	}
	
	public Object getObject() {
		return object;
	}
	
	public MantenedorPhaseListener() {
	
	}
	@Override
	public void afterPhase(PhaseEvent evento) {
		
		contextBean = evento.getFacesContext();
	     
		String tipo = (String)contextBean.getExternalContext().getRequestParameterMap().get("tipo");
		
		 if(tipo != null){
			
			 HttpServletRequest request = (HttpServletRequest) contextBean.getExternalContext().getRequest();
			 String viewId = request.getRequestURI();
			
			 int intTipo = Integer.parseInt(tipo);
			 switch(intTipo){
				 case 1:
					 if(viewId.indexOf("jsonSueldo") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.sueldoTrabajador(evento);
					 }
					 break;
				 case 2:
					 if(viewId.indexOf("listaEmpleados") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.listaEmpleados(evento);
					 }
					 break;
				 case 3:
					 if(viewId.indexOf("listaLiquidaciones") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.listaLiquidaciones(evento);
					 }
					 break;
				 case 4:
					 if(viewId.indexOf("crearLiquidaciones") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.crearLiquidacion(evento);
					 }
					 break;
				 case 5:
					 if(viewId.indexOf("crearEmpleado") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.crearEmpleado(evento);
					 }
					 break;
				 case 6:
					 if(viewId.indexOf("eliminarEmpleado") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.eliminarEmpleado(evento);
					 }
					 break;
				 case 7:
					 if(viewId.indexOf("eliminarLiquidacion") != -1) {
						 MantenedorPhaseListener phase = new MantenedorPhaseListener();
						 phase.eliminarLiquidacion(evento);
					 }
					 break;
			} 
			
			
		 }
		
	}


	@Override
	public void beforePhase(PhaseEvent arg0) {
	
	}
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	private void sueldoTrabajador(PhaseEvent evento) {
		   String sueldo=null;
		   String error="OK";
	       try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				//parametro desde el la pagina web
				String rut = (String)contextBean.getExternalContext().getRequestParameterMap().get("rut");
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
	            
	            MantenedorBeanRrhh mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanRhh}",MantenedorBeanRrhh.class);
	            
	            if(mantenedorBean !=null){
            	    sueldo = mantenedorBean.obtenerSueldo(rut);
            		elementoJson.put("sueldo",sueldo); 
            		elementoJson.put("Error",error);
	            }
	     
			}catch(Exception e){
				error = e.getMessage();
				elementoJson.put("Error",error);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
	
	}
	
	@SuppressWarnings("unchecked")
	private void listaEmpleados(PhaseEvent evento) {
	
		List<DTOEmpleado> lista = new ArrayList<>();
		String error="OK";
		 try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
		        JSONArray arregloJson = new JSONArray();
	            
	            MantenedorBeanRrhh mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanRhh}",MantenedorBeanRrhh.class);
	            
	            if(mantenedorBean !=null){
	              lista = mantenedorBean.listaEmpleados();
	              
	              for(DTOEmpleado empleado : lista){
	            	  JSONObject elementoJsonFila = new JSONObject();
	            	  elementoJsonFila.put("rut",empleado.getRut().trim());
	            	  elementoJsonFila.put("nombres",empleado.getNombre().trim());
	            	  elementoJsonFila.put("paterno",empleado.getPaterno().trim());
	            	  elementoJsonFila.put("materno",empleado.getMaterno().trim());
	            	  arregloJson.add(elementoJsonFila);
	              }
	              
	  	          elementoJson.put("total",lista.size());
	  	          elementoJson.put("empleado",arregloJson);
	  	          elementoJson.put("Error",error);
	             
	            }
	     
			}catch(Exception e){
				error = e.getMessage();
				elementoJson.put("Error",error);
				 elementoJson.put("empleado",null);
				 elementoJson.put("total",0);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		
	}
	
	@SuppressWarnings("unchecked")
	private void listaLiquidaciones(PhaseEvent evento) {
	
		List<DTOLiquidacion> lista = new ArrayList<>();
		String error="OK";
		 try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
		        JSONArray arregloJson = new JSONArray();
	            
	            MantenedorBeanContabilidad mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanContabilidad}",MantenedorBeanContabilidad.class);
	            
	            if(mantenedorBean !=null){
	              lista = mantenedorBean.listaLiquidacion();
	              
	              for(DTOLiquidacion liquidacion : lista){
	            	  JSONObject elementoJsonFila = new JSONObject();
	            	  elementoJsonFila.put("rut",liquidacion.getRut().trim());
	            	  elementoJsonFila.put("monto",liquidacion.getMonto());
	            	  arregloJson.add(elementoJsonFila);
	              }
	              
	  	          elementoJson.put("total",lista.size());
	  	          elementoJson.put("liquidacion",arregloJson);
	  	          elementoJson.put("Error",error);
	             
	            }
	     
			}catch(Exception e){
				error = e.getMessage();
				elementoJson.put("Error",error);
				 elementoJson.put("liquidacion",null);
				 elementoJson.put("total",0);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		
	}
	
	@SuppressWarnings("unchecked")
	private void crearLiquidacion(PhaseEvent evento) {
	
		String lista ="1";
		String error="OK";
		 try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				
				String rut = (String)contextBean.getExternalContext().getRequestParameterMap().get("rut");
				String monto = (String)contextBean.getExternalContext().getRequestParameterMap().get("monto");
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
		      
	            MantenedorBeanContabilidad mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanContabilidad}",MantenedorBeanContabilidad.class);
	            
	            if(mantenedorBean !=null){
	            	DTOLiquidacion dtoLiquidacion = new DTOLiquidacion();
	            	dtoLiquidacion.setRut(rut);
	            	dtoLiquidacion.setMonto(Long.parseLong(monto));
	                lista = mantenedorBean.crearLiquidacion(dtoLiquidacion);
	  	            elementoJson.put("liquidacion",lista);
	  	            elementoJson.put("Error",error);
	             
	            }
		  
		   
			}catch(Exception e){
				error = e.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		
	}
	
	@SuppressWarnings("unchecked")
	private void crearEmpleado(PhaseEvent evento) {
	
		String lista ="1";
		String error="OK";
		 try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				
				//parametro desde el la pagina web
				String rut = (String)contextBean.getExternalContext().getRequestParameterMap().get("rut");
				String nombres = (String)contextBean.getExternalContext().getRequestParameterMap().get("nombres");
				String paterno = (String)contextBean.getExternalContext().getRequestParameterMap().get("paterno");
				String materno = (String)contextBean.getExternalContext().getRequestParameterMap().get("materno");
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
		      
	            MantenedorBeanRrhh mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanRhh}",MantenedorBeanRrhh.class);
	            
	            if(mantenedorBean !=null){
	              
	              DTOEmpleado dtoEmpleado = new DTOEmpleado();	
	              dtoEmpleado.setRut(rut);
	              dtoEmpleado.setNombre(nombres);
	              dtoEmpleado.setPaterno(paterno);
	              dtoEmpleado.setMaterno(materno);
	              
	              lista = mantenedorBean.crearEmpleado(dtoEmpleado);
	              
	  	          elementoJson.put("estado",lista);
	  	          elementoJson.put("Error",error);
	             
	            }
	            
		  
			} catch (RemoteException e) {
				error = e.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}catch(NotBoundException ee){
				error = ee.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}catch(Exception eee){
				error = eee.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		
	}
	
	@SuppressWarnings("unchecked")
	private void eliminarLiquidacion(PhaseEvent evento) {
		
		String lista ="1";
		String error="OK";
		 try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				
				//parametro desde el la pagina web
				String rut = (String)contextBean.getExternalContext().getRequestParameterMap().get("rut");
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
		      
	            MantenedorBeanContabilidad mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanContabilidad}",MantenedorBeanContabilidad.class);
	            
	            if(mantenedorBean !=null){
	         
	              lista = mantenedorBean.eliminarLiquidacion(rut);
	 
	  	          elementoJson.put("estado",lista);
	  	          elementoJson.put("Error",error);
	             
	            }
	            
			} catch (RemoteException e) {
				error = e.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}catch(NotBoundException ee){
				error = ee.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}catch(Exception eee){
				error = eee.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		
		
	}


	@SuppressWarnings("unchecked")
	private void eliminarEmpleado(PhaseEvent evento) {
		
		String lista ="1";
		String error="OK";
		 try{	
	    		contextBean = evento.getFacesContext();
				this.setObject(contextBean);
				this.setResponse(contextBean);
				
				if (!(this.getObject() instanceof HttpServletRequest)) {
					return;
				}
				
				//parametro desde el la pagina web
				String rut = (String)contextBean.getExternalContext().getRequestParameterMap().get("rut");
				
				out = new StringWriter();		
	            elementoJson = new JSONObject();
		      
	            MantenedorBeanRrhh mantenedorBean = contextBean.getApplication().evaluateExpressionGet(contextBean, "#{mantenedorBeanRhh}",MantenedorBeanRrhh.class);
	            
	            if(mantenedorBean !=null){
	         
	              lista = mantenedorBean.eliminarEmpleado(rut);
	              
	  	          elementoJson.put("estado",lista);
	  	          elementoJson.put("Error",error);
	             
	            }
	            
		  
			} catch (RemoteException e) {
				error = e.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}catch(NotBoundException ee){
				error = ee.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}catch(Exception eee){
				error = eee.getMessage();
				elementoJson.put("Error",error);
				elementoJson.put("liquidacion",0);
			}finally{
				try {
					elementoJson.writeJSONString(out);
					jsonText = out.toString();
					this.getResponse().setHeader("Cache-control", "no-cache");
					this.getResponse().setCharacterEncoding("UTF-8");
					this.getResponse().getWriter().write(jsonText);
					evento.getFacesContext().responseComplete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		
	
		
	}

}
