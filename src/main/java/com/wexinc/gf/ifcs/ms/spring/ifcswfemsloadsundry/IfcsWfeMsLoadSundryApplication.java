package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.controller"})
@ComponentScan("com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.services")
@ComponentScan("com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.routes")
public class IfcsWfeMsLoadSundryApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfcsWfeMsLoadSundryApplication.class, args);
	}

}
