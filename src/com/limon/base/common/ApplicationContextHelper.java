package com.limon.base.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author gqf
 * 
 * 应用程序上下文工具类
 * 2015-2-10 下午02:02:28
 */
public class ApplicationContextHelper implements ApplicationContextAware{
	
	private static ApplicationContext appCtx;  
	
    /** 
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。 
     * @param applicationContext ApplicationContext 对象. 
     * @throws BeansException 
     */ 
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {  
        appCtx = applicationContext;  
    }
    
    /** 
     * 这是一个便利的方法，帮助我们快速得到一个BEAN 
     * @param beanName bean的名字 
     * @return 返回一个bean对象 
     */  
    public static Object getBean( String beanName ) {  
        return appCtx.getBean( beanName );  
    }  
}
