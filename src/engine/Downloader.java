package engine;

/**
 * Created by piotr.walczak on 03.01.2017.
 */
public interface Downloader extends Runnable {

    void download() throws Exception;

    boolean pause();

    boolean cancel();

    void close() throws Exception;

    void getId();
}
