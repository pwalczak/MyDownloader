package engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by piotr.walczak on 03.01.2017.
 */
public class BackgroundMonitoring {

    private static final BackgroundMonitoring instance = new BackgroundMonitoring();
    private ExecutorService executor = Executors.newFixedThreadPool(5);
    private BackgroundMonitoring()
    {

    }

    public void submitTask(Runnable jobToDo)
    {
       executor.submit(jobToDo);
    }

    public static BackgroundMonitoring getInstance()
    {
        return instance;
    }



}
