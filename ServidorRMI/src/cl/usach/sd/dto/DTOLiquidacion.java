package cl.usach.sd.dto;

import java.io.Serializable;

public class DTOLiquidacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rut;
	private long monto;
	
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public long getMonto() {
		return monto;
	}
	public void setMonto(long monto) {
		this.monto = monto;
	}

}
