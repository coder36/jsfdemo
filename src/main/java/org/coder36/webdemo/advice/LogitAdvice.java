package org.coder36.webdemo.advice;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Logging advice
 * @author Mark Middleton
 */
@Service
@Aspect
public class LogitAdvice {

	private static final Logger log = LoggerFactory.getLogger(LogitAdvice.class);
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	@Pointcut("@target(logit)")
	public void logitJoinPoint(Logit logit) {}
	
	@Around( "@annotation(l)")
	public Object logMethod( ProceedingJoinPoint jp, Logit l ) throws Throwable {
		return log( jp, l );
	}
	
	@Around( "logitJoinPoint(l) && execution(* *(..))" )
	public Object log( ProceedingJoinPoint jp, Logit l ) throws Throwable {
		try {
			if ( log.isTraceEnabled() ) log.trace(toString(jp, l));
			Object o = jp.proceed();
			if ( log.isTraceEnabled() ) log.trace("  Return: " + getString(o));
			return o;
		}
		catch( Throwable t ) {			
			if ( ! log.isTraceEnabled() ) log.error(toString(jp, l) );
			log.error( ExceptionUtils.getStackTrace(t) );
			throw t;
		}
	}
	
	public String toString( ProceedingJoinPoint p, Logit l ) {
		StringBuffer s = new StringBuffer();
		s.append( "\n--------> " + l.value() + "\n");
		s.append( "  Method: " + p.getSignature().toShortString() + "\n" );
		Object [] args = p.getArgs();
		for( int i=0; i<args.length; i++  ) {
			s.append( "  args[" + i + "]: " + getString(args[i]) + "\n" );
		}
		return s.toString();
	}
	
	public String getString( Object o ) {
		if ( o == null ) return "null";
		if( o instanceof Number || o instanceof String ) {
			return o.toString();
		}
		try {
			return jsonMapper.defaultPrettyPrintingWriter().writeValueAsString( o );
		}
		catch( Exception e ) {
			return "Not serializable";
		}
	}
}
