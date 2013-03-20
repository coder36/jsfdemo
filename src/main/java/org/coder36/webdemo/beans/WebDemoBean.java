package org.coder36.webdemo.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import org.codehaus.jackson.map.ObjectMapper;
import org.coder36.webdemo.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web Demo Controller
 * @author Mark Middleton
 */
@ManagedBean
@RequestScoped
public class WebDemoBean {
	
	private String systemProperty;
	private String environmentVariable;
	private String jndi;
	private String jndiDS;
	private String sql;
	private String sqlOutput;
	
	@ManagedProperty( "#{myStateBean}" )
	private MyState myState;

	public MyState getMyState() {
		return myState;
	}

	public void setMyState(MyState myState) {
		this.myState = myState;
	}

	@ManagedProperty( "#{databaseServiceImpl}" )
	private DatabaseService databaseService;

	private Logger log = LoggerFactory.getLogger( WebDemoBean.class );

	public List<String> systemPropertyAutoComplete( String query ) {
		List<String> l = new ArrayList<String>();
		Properties p = System.getProperties();
		for( String s: p.stringPropertyNames() ) {
			if( s.toUpperCase().startsWith( query.toUpperCase() ) ) {
				l.add( s );
			}
		}
				
		return l;
	}	
	
	// doesnt work!
	public List<String> jndiAutoComplete( String query ) {
		List<String> l = new ArrayList<String>();
		try {			
			InitialContext ctx = new InitialContext();
			NamingEnumeration<NameClassPair> list = ctx.list( query );
			while (list.hasMore()) {
				String s = list.next().getName();
				if( s.toUpperCase().startsWith( query.toUpperCase() ) ) {
					l.add( s );
				}				
			}			
		}
		catch( Exception e ) {
		}
		return l;
	}
	
	public List<String> envinronmentVariableAutoComplete( String query ) {
		log.debug( "envinronmentVariableAutoComplete: query=" + query );
		List<String> l = new ArrayList<String>();
		Map<String,String> p = System.getenv();
		for( String s: p.keySet() ) {
			if( s.toUpperCase().startsWith( query.toUpperCase() ) ) {
				l.add( s );
			}
		}
		return l;
	}
	
	public void metadata() {
		try {
			sqlOutput = databaseService.getMetadata( jndiDS );
		}
		catch( Exception e ) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error message", e.getMessage()));
		}		
	}
	
	public void runSql() {
		try {
			List<Object []> l = databaseService.getData(jndiDS, sql);
			ObjectMapper m = new ObjectMapper();
			sqlOutput = m.writeValueAsString(l); 
		}
		catch( Exception e ) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error message", e.getMessage()));
		}
	}
	
	public void reset() {
		myState.getData().clear();
	}
	
	public void findEnvironmentVariable() {
		DataContainer c = new DataContainer( environmentVariable, environmentVariable.isEmpty() ? null : System.getenv( environmentVariable )  );
		myState.getData().add(c);
	}	
	
	public void findSystemProperty() {
		DataContainer c = new DataContainer( systemProperty, systemProperty.isEmpty() ? null : System.getProperty( systemProperty ) );
		myState.getData().add(c);
	}
	
	public void findJndi() {
		try {
			InitialContext ctx = new InitialContext();
			Object o = ctx.lookup( jndi );
			DataContainer c = new DataContainer( jndi, o == null ? null : o.toString() );
			myState.getData().add(c);
		}
		catch( Exception e ) {
			DataContainer c = new DataContainer( jndi, null );
			myState.getData().add(c);
		}
	}

	public List<DataContainer> getData() {
		return myState.getData();
	}

	public String getSystemProperty() {
		return systemProperty;
	}

	public void setSystemProperty(String systemProperty) {
		this.systemProperty = systemProperty;
	}
	
	public String getEnvironmentVariable() {
		return environmentVariable;
	}

	public void setEnvironmentVariable(String envProperty) {
		this.environmentVariable = envProperty;
	}	
	
	public String getJndi() {
		return jndi;
	}

	public void setJndi(String jndi) {
		this.jndi = jndi;
	}	
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}	
	
	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}	
	
	public String getJndiDS() {
		return jndiDS;
	}

	public void setJndiDS(String jndiDS) {
		this.jndiDS = jndiDS;
	}
	
	public String getSqlOutput() {
		return sqlOutput;
	}
	
	public void setSqlOutput(String sqlOutput) {
		this.sqlOutput = sqlOutput;
	}		

	

}
