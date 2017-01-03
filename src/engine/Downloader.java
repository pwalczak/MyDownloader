package engine;

/**
 * Created by piotr.walczak on 03.01.2017.
 */
public interface Downloader extends Runnable {

    public void download() throws Exception;
    public boolean pause();
    public boolean cancel();
    public void close() throws Exception;
}
