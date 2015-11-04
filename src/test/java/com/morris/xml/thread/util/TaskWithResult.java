package com.morris.xml.thread.util;

import java.util.concurrent.Callable;

public class TaskWithResult implements Callable<String> {

	private int id;

	public TaskWithResult(int id) {
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(1000*id);
		return "result of TaskWithResult " + id;
	}

}
