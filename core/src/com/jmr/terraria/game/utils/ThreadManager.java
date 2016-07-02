package com.jmr.terraria.game.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jon on 12/19/15.
 */
public class ThreadManager {

	private static final ThreadManager instance = new ThreadManager();
	private final ExecutorService executorService;

	private ThreadManager() {
		executorService = Executors.newFixedThreadPool(4);
	}

	public void runThread(Runnable runnable) {
		executorService.execute(runnable);
	}

	public void shutdown() {
		executorService.shutdown();
	}

	public static ThreadManager getInstance() {
		return instance;
	}

}
