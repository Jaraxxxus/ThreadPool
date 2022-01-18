
import java.util.concurrent.BlockingQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;



public class MyThreadPool {
    private BlockingQueue taskQueue;
    private List<MyThreadPoolRunnable> runnables = new ArrayList();
    private boolean isStopped = false;

    public MyThreadPool(int threadsNumber, int maxThreads) {
        this.taskQueue = new ArrayBlockingQueue(maxThreads);

        for(int i = 0; i < threadsNumber; ++i) {
            this.runnables.add(new MyThreadPoolRunnable(this.taskQueue));
        }

        Iterator<MyThreadPoolRunnable> it = this.runnables.iterator();

        while(it.hasNext()) {
            MyThreadPoolRunnable runnable = (MyThreadPoolRunnable)it.next();
            (new Thread(runnable)).start();
        }

    }

    public synchronized void execute(Runnable task) throws Exception {
        if (this.isStopped) {
            throw new IllegalStateException("ThreadPool is stopped");
        } else {
            this.taskQueue.offer(task);
        }
    }

    public synchronized void stop() {
        this.isStopped = true;
        Iterator<MyThreadPoolRunnable> stopItt = this.runnables.iterator();

        while(stopItt.hasNext()) {
            MyThreadPoolRunnable runnable = (MyThreadPoolRunnable)stopItt.next();
            runnable.Stop();
        }

    }
}
