package com.morris.xml.thread.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class CallableTest {

	@Test
	public void test1() throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Future<String>> results = new ArrayList<Future<String>>(); // Future
																				// 相当于是用来存放Executor执行的结果的一种容器
		for (int i = 0; i < 10; i++) {
			results.add(exec.submit(new TaskWithResult(i)));
		}
		for (Future<String> fs : results) {
			if (fs.isDone()) {
				System.out.println(fs.get());
			} else {
				System.out.println("Future result is not yet complete");
			}
		}
		exec.shutdown();
	}

	@Test
	public void test2() throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newCachedThreadPool();
		List<Callable<String>> tasks = new ArrayList<Callable<String>>();

		for (int i = 0; i < 10; i++) {
			tasks.add(new TaskWithResult(i));
		}

		List<Future<String>> list = exec.invokeAll(tasks, 40, TimeUnit.SECONDS);

		for (Future<String> fs : list) {
			if (fs.isDone()) {
				System.out.println(fs.get());
			} else {
				System.out.println("Future result is not yet complete");
			}
		}
		exec.shutdown();
	}
}
