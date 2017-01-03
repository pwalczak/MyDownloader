package engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;

/**
 * Created by piotr.walczak on 03.01.2017.
 */
public class DownloaderImp implements Downloader {

    private URL urlAddress;
    private ReadableByteChannel downloadChannel;
    private FileOutputStream fileOutputStream;

    private DownloaderImp(URL url)
    {
        this.urlAddress = url;
    }

    public static DownloaderImp getDownloaderImpInstance(String address) throws Exception
    {
        URL url = new URL(address);
        return new DownloaderImp(url);
    }

    @Override
    public void download()  {
        try {
            downloadChannel = Channels.newChannel(urlAddress.openStream());
            fileOutputStream = openOutputStream(urlAddress);
            fileOutputStream.getChannel().transferFrom(downloadChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    public FileOutputStream openOutputStream(URL url) throws FileNotFoundException
    {
        String nameFromURL = getNameFromURL(url);
        return new FileOutputStream(nameFromURL);
    }

    private String getNameFromURL(URL url)
    {
      String [] stringTab = url.toString().split("/");
      return stringTab[stringTab.length-1];
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
    public void close()  {
        close(this.downloadChannel);
        close(this.fileOutputStream);
    }

    public void close(FileOutputStream fs)
    {
     if(fs != null)
     {
         try {
             fs.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    }
    public void close(ReadableByteChannel rbc)
    {
        if(rbc != null)
        {
            try {
                rbc.close();
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
}
