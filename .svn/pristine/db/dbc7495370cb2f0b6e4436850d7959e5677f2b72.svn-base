package fr.ign.cogit.geographlab.testing;

public class Test {

	public static void main(String[] args) {
		ThreadPool stp = new ThreadPool(2);
		stp.enqueue(new TestTask1());
		stp.enqueue(new TestTask2());
	}

	private static class TestTask1 implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("A");
			}
		}
	}

	private static class TestTask2 implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("B");
			}
		}
	}
}