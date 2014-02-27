package com.sccl.framework.service.log;


import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.NDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.sccl.framework.common.utils.Uid;
import com.sccl.framework.entity.MsLog;


import flex.messaging.FlexContext;


@Aspect
@Component
public class LogAspect {
	
	private ILogService logService;

	public ILogService getLogService() {
		return logService;
	}

	@Resource
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	//标注该方法体为后置通知，当目标方法执行成功后执行该方法体  
	@AfterReturning(pointcut="within(com..service..*) && @annotation(rl)") 
	public void after(JoinPoint jp, Log rl) {
    	//获取目标类名
    	String className = jp.getTarget().getClass().toString();
    	className = className.substring(className.indexOf("com"));
    	//获取目标方法签名
    	String signature = jp.getSignature().toString();
    	//获取目标方法名
    	String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));  
    	//用户 
    	String userCode = FlexContext.getFlexSession().getAttribute("userCode").toString();
    	
    	Object userBehaveObj = FlexContext.getFlexSession().getAttribute("userBehave");
    	Object parametersObj = FlexContext.getFlexSession().getAttribute("parameters");

    	String userBehave = userBehaveObj == null ? "" : userBehaveObj.toString();
    	String parameters = parametersObj == null ? "" : parametersObj.toString();
    	
    	//System.out.println("执行了！"+className+" " + methodName + "正常");
    	
		
		MsLog msLog = new MsLog();
		msLog.setId(Uid.getUidUtil().createUID());
		msLog.setUserCode(userCode);
		msLog.setClassName(className);
		msLog.setMethodName(methodName);
		msLog.setUserBehave(userBehave);
		msLog.setIpAddress(NDC.pop());
		msLog.setParameters(parameters);
		msLog.setIsSucceed("执行成功");
		msLog.setTheContent(rl.desc());
		msLog.setCreatetime(new Date());
		
		logService.addLog(msLog);
    	
		//logger = LogFactory.getLog(jp.getTarget().getClass());//Logger.getLogger(jp.getTarget().getClass());
		//logger.info("执行" + methodName + "正常," + NDC.get());
    }
	
	@AfterThrowing(pointcut="within(com..service..*) && @annotation(rl)") 
	public void afterError(final JoinPoint jp,final Log rl) {
//		Thread t=new Thread(new Runnable() {
//			
//			public void run() {
			//获取目标类名
		    	String className = jp.getTarget().getClass().toString();
		    	className = className.substring(className.indexOf("com"));
		    	//获取目标方法签名
		    	String signature = jp.getSignature().toString();
		    	//获取目标方法名
		    	String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));  

		    	//用户 
		    	String userCode = FlexContext.getFlexSession().getAttribute("userCode").toString();
		    	
		    	Object userBehaveObj = FlexContext.getFlexSession().getAttribute("userBehave");
		    	Object parametersObj = FlexContext.getFlexSession().getAttribute("parameters");
		    	
		    	String userBehave = userBehaveObj == null ? "" : userBehaveObj.toString();
		    	String parameters = parametersObj == null ? "" : parametersObj.toString();
		    	
		    	//System.out.println("执行了！"+className+" " + methodName + "出错");
		    	
				
		    	MsLog msLog = new MsLog();
				msLog.setId(Uid.getUidUtil().createUID());
				msLog.setUserCode(userCode);
				msLog.setClassName(className);
				msLog.setMethodName(methodName);
				msLog.setUserBehave(userBehave);
				msLog.setIpAddress(NDC.pop());
				msLog.setParameters(parameters);
				msLog.setIsSucceed("执行失败");
				msLog.setTheContent(rl.desc());
				msLog.setCreatetime(new Date());
				
				logService.addLog(msLog);
		    	//logger = LogFactory.getLog(jp.getTarget().getClass());
				//logger.error("执行" + methodName + "不正常," + NDC.get());
//			}
//		});
//		t.start();
		
   }
		
}
