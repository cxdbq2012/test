package com.company;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class MyTestBean implements InitializingBean,DisposableBean{

	public void destroy() throws Exception {
		System.out.println("destory......."+this);
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPro......"+this);
	}

}
