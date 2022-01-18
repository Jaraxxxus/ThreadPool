

import java.util.concurrent.BlockingQueue;



class MyThreadPoolRunnable implements Runnable {
    private Thread thread;
    private BlockingQueue taskQueue;
    private boolean isStopped = false;

    public MyThreadPoolRunnable(BlockingQueue queue) {
        this.taskQueue = queue;
    }

    public void run() {
        this.thread = Thread.currentThread();

        while(!this.isStopped()) {
            try {
                Runnable runnable = (Runnable)this.taskQueue.take();
                runnable.run();
            } catch (Exception e) {
            }
        }

    }

    public synchronized void Stop() {
        this.isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }
}


