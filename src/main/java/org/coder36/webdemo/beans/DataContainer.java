package org.coder36.webdemo.beans;

import java.io.Serializable;
import java.util.Date;

public class DataContainer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Date timestamp = new Date();
	private String propName;
	private String propValue;		
	
	public DataContainer( String propName, String propValue ) {
		this.propName = propName;
		this.propValue = propValue;
	}
	
	public String getPropName() {
		return propName;
	}

	public String getPropValue() {
		return propValue;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
