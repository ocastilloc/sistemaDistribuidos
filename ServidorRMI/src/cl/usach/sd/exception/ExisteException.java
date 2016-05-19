package cl.usach.sd.exception;

import java.io.Serializable;

public class ExisteException extends Exception  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExisteException(String mensaje) {
		super(mensaje);
	}

}
