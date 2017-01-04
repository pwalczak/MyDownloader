package engine;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by piotr.walczak on 03.01.2017.
 */
public class BackgroundMonitorThread implements Runnable{

    private static final BackgroundMonitorThread instance = new BackgroundMonitorThread();
    private ExecutorService executor = Executors.newFixedThreadPool(5);
    private Map<Runnable,Future<?>> mapOfTasks = new HashMap<>();

    private BackgroundMonitorThread() {

    }

    public void submitTask(Runnable jobToDo) {
        Future<?> submit = executor.submit(jobToDo);
        mapOfTasks.put(jobToDo, submit);
    }

    public void cancelTask(Downloader jobToDo) {
        Future<?> submitted = mapOfTasks.get(jobToDo);
        submitted.cancel(true);
    }

    public static BackgroundMonitorThread getInstance() {
        return instance;
    }

    public void close()
    {
        this.executor.shutdownNow();
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cleanMapFromDoneTasks();
    }

    private void cleanMapFromDoneTasks() {
        Iterator<Map.Entry<Runnable, Future<?>>> entries = mapOfTasks.entrySet().iterator();
        while(entries.hasNext())
        {
            Map.Entry<Runnable, Future<?>> entry = entries.next();
            if(entry.getValue().isDone() || entry.getValue().isCancelled())
            {
                entries.remove();
            }
        }
    }
}
