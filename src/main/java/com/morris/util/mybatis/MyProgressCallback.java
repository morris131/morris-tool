package com.morris.util.mybatis;

import org.mybatis.generator.api.ProgressCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyProgressCallback implements ProgressCallback{
	
	private Logger log = LoggerFactory.getLogger(MyProgressCallback.class);

	@Override
	public void introspectionStarted(int totalTasks) {
		// TODO Auto-generated method stub
		log.info("totalTasks: "+totalTasks);
		
	}

	@Override
	public void generationStarted(int totalTasks) {
		// TODO Auto-generated method stub
		log.info("totalTasks: "+totalTasks);
		
	}

	@Override
	public void saveStarted(int totalTasks) {
		// TODO Auto-generated method stub
		log.info("totalTasks: "+totalTasks);
	}

	@Override
	public void startTask(String taskName) {
		// TODO Auto-generated method stub
		log.info("taskName: "+taskName);
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		log.info("totalTasks: done");
	}

	@Override
	public void checkCancel() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

}
