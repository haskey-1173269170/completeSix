package com.jk.aopmongdb;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LogAspect
 * @Deacription TODO
 * @Author liushuxin
 * @Date 2019/11/22 17:11
 * @Version 1.0
 **/
@Aspect
@Component
public class LogAspect{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Pointcut("execution(* com.jk.*.controller.*.*(..))")
   public void logPointCut(){

   }

    @AfterReturning(value="logPointCut()",returning = "result")
    public void showAfterLog(JoinPoint joinPoint,Object result) throws Throwable{
       LogBack logBack = new LogBack();

        logBack.setMethod(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        String parmes="";
        for (int i = 0; i < args.length; i++) {
            parmes+=parmes.equals("")?args[i]:","+args[i];
        }
        logBack.setParmes(parmes);
        if(result!=null){
            logBack.setRvalue(result.toString());
        }

        mongoTemplate.save(logBack);
    }
}
