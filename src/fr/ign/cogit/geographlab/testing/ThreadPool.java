package fr.ign.cogit.geographlab.testing;

import java.util.LinkedList;

public class ThreadPool {
	private WorkerThread[] threads;
	private LinkedList<Runnable> taskQueue;
	boolean finished = false;
	
	public ThreadPool(int threadNumber) {
		this.taskQueue = new LinkedList<Runnable>();
		this.threads = new WorkerThread[threadNumber];
		for (int i = 0; i < this.threads.length; i++) {
			this.threads[i] = new WorkerThread();
			this.threads[i].start();
			this.threads[i].setName("Thread_compute_paths_"+i);
		}
	}
	
	public void enqueue(Runnable r) {
		synchronized (this.taskQueue) {
			this.taskQueue.addLast(r);
			this.taskQueue.notify();
		}
	}
	
	public void finish() throws InterruptedException {
		this.finished = true;
		
		for (int i = 0; i < this.threads.length; i++) {
			this.threads[i].stop();
		}
	}
	
	public class WorkerThread extends Thread {
		public void run() {
			Runnable r;
			while (ThreadPool.this.finished == false) {
//				System.out.println("WorkerThread on");
				synchronized (taskQueue) {
					while (taskQueue.isEmpty()) {
						try {
							taskQueue.wait();
						} catch (InterruptedException e) {
							// ignore
						}
					}
					r = (Runnable) taskQueue.removeFirst();					
				}
				try {
					r.run();
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}
}