package com.insproject.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.insproject.provider.module.common.SessionUser;
import com.insproject.provider.module.user.entity.User;

@Aspect
@Component
public class ThreadLocalParams {
	public ThreadLocalParams() {
		System.out.println("ThreadLocalParams init ......");
	}
	
	@Around("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
	public Object before(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (arg instanceof HttpServletRequest) {
				Object user = ((HttpServletRequest) arg).getSession().getAttribute("user");
				if(user == null){
					User user2 = new User();
					user2.setId(1);
					SessionUser.threadLocal.set(user2);
				}else{
					SessionUser.threadLocal.set((User)user);
				}
			}
		}
		return pjp.proceed();
	}

}
