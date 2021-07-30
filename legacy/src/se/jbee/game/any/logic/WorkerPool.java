package se.jbee.game.any.logic;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class WorkerPool {

	final ConcurrentLinkedQueue<Worker> runningWorkers = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Worker> idleWorkers = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Runnable> jobs = new ConcurrentLinkedQueue<>();

	public WorkerPool(int maxWorkers) {
		for (int i = 0; i < maxWorkers; i++) {
			new Worker().start();
		}
	}

	/**
	 * Runs the given job in FIFO order. A worker picks up the job when it is
	 * its turn. A job might never run if {@link #cancel()} is called before the
	 * job is started. All started jobs always finish.
	 */
	public void run(Runnable job) {
		if (jobs.contains(job))
			return;
		jobs.add(job);
		if (!idleWorkers.isEmpty()) {
			try {
				Worker worker = idleWorkers.poll();
				synchronized (worker) {
					worker.notify();
				}
			} catch (NoSuchElementException e) { }
		}
	}

	/**
	 * Does not start any more jobs. All started jobs are finished. 
	 * 
	 * The method returns when all started jobs are done.
	 */
	public void cancel() {
		jobs.clear();
		while (!runningWorkers.isEmpty()) {
			try {
				Worker worker = runningWorkers.element();
				worker.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				// just continue, we are done
			}
		}
	}

	private class Worker extends Thread {

		public Worker() {
			setDaemon(true);
		}

		@Override
		public void run() {
			runningWorkers.add(this);
			while (true) {
				Runnable job = jobs.poll();
				if (job != null) {
					try {
						setName("Worker:"+job.toString());
						job.run();
					} catch (Exception e) {
						//TODO some error handling
						e.printStackTrace();
					}
				} else { 
					setName("Idle Worker");
					runningWorkers.remove(this);
					synchronized (this) {
						idleWorkers.add(this);
						try {
							wait();
						} catch (InterruptedException e) {
							// well, we just see if there is something to do...
						}
					}
					runningWorkers.add(this);
				}
			}
		}
	}
}
