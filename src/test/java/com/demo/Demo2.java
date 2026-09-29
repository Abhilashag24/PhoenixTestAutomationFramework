package com.demo;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo2 {

	
	private static Logger  logger = LogManager.getLogger(Demo2.class);
	
	public static void main(String[] args) {
		 
		System.out.println("Inside the main method");
		logger.info("Inside the main method");
		int a = 10; 
		System.out.println("Value of a is : " +a);
		logger.info("Value of a is : {}" ,a);
		
		int b=0;
		
		if(b==0) {
			logger.warn("Value of b is : {}" ,b);
		}else {
			logger.info("Value of b is : {}" ,b);

		}
		
	
try {
		int result = a/b;
		System.out.println("Result of addition is "+result);
		logger.info("Result of addition is {}",result);
		System.out.println("Result is :" +result);
		
}catch (Exception e) {
	logger.error("Operation cannot happen",e);
}
		 
		logger.info("Program ended!!");
	}

}
