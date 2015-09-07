package com.apps.common.auth;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Aspect
public class LogBefore { 
	protected static Logger logger =null ; 
	
	public LogBefore() {
	    logger=LoggerFactory.getLogger(getClass().getName());
	}	

	@Before(value="execution(* com.apps.mobile.service.IMobileService.*(..))")
	public void beforeCall(JoinPoint joinPoint) {  
		Object[] args= joinPoint.getArgs();
		String str = "";
		if(args!=null)
		{
			for(int i= 0; i<args.length; i++)
			{
				str += "," + args[i];
			}
			if(str.length()>1)
			{
				str = str.substring(1);
			}
		}
		logger.debug("LogBefore-before:"+joinPoint.getSignature().getName()+"("+str+")");
	}		
	@After(value="execution(* com.apps.mobile.service.IMobileService.*(..))")
	public void afterCall(JoinPoint joinPoint){
		Object[] args= joinPoint.getArgs();
		String str = "";
		if(args!=null)
		{
			for(int i= 0; i<args.length; i++)
			{
				str += "," + args[i];
			}
			if(str.length()>1)
			{
				str = str.substring(1);
			}
		}
		logger.debug("LogBefore-after:"+joinPoint.getSignature().getName()+"("+str+")");
	}
}