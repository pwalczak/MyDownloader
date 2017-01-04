package engine;

import java.io.*;
import java.net.URL;

/**
 * Created by piotr.walczak on 03.01.2017.
 */
public class DownloaderImp implements Downloader {

    private URL urlAddress;
    private BufferedInputStream fileInputStream;
    private FileOutputStream fileOutputStream;

    private DownloaderImp(URL url) {
        this.urlAddress = url;
    }

    public static DownloaderImp getDownloaderImpInstance(String address) throws Exception {
        URL url = new URL(address);
        return new DownloaderImp(url);
    }

    private void openStreams() {
        try {
            fileInputStream = new BufferedInputStream(urlAddress.openStream());
            fileOutputStream = openOutputStream(urlAddress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download() {
        openStreams();
        byte[] buffer = new byte[1024];
        int count = 0;
        try {
            while ((count = fileInputStream.read(buffer, 0, 1024)) != -1) {
                if (Thread.currentThread().isInterrupted())
                    return;
                fileOutputStream.write(buffer, 0, count);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private FileOutputStream openOutputStream(URL url) throws FileNotFoundException {
        String nameFromURL = getNameFromURL(url);
        return new FileOutputStream(nameFromURL);
    }

    private String getNameFromURL(URL url) {
        String[] stringTab = url.toString().split("/");
        return stringTab[stringTab.length - 1];
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public void close() {
        close(this.fileInputStream);
        close(this.fileOutputStream);
    }

    @Override
    public void getId() {

    }

    private void close(FileOutputStream fs) {
        if (fs != null) {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(BufferedInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloaderImp that = (DownloaderImp) o;

        return urlAddress.equals(that.urlAddress);
    }

    @Override
    public int hashCode() {
        return urlAddress.hashCode();
    }
}
