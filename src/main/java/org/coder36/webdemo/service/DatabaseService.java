package org.coder36.webdemo.service;

import java.util.List;

public interface DatabaseService {
	
	/**
	 * Get data
	 * @param sql
	 */
	public List<Object[]> getData( String jndi, String sql );	
	
	/**
	 * Get driver version
	 * @param jndi
	 * @return
	 */
	public String getMetadata( String jndi );

}
