package com.api.reporting;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		Thread.currentThread();
		ReportFactory.FailTest("<details><summary>FAILURE :: Execption (click to view details)</summary>"+e.toString()+"<details>");	
	}

}
