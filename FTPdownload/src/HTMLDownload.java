import com.sun.javafx.binding.StringFormatter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Created by pmg on 2015/10/20.
 */
public class HTMLDownload
{
    public static void main(String[] args)
    {
        String head = "http://comment.bilibili.com/";
        String savePath = "./FTPdownload/src/ZhenHuanZhuanDanmu/";
        String danmuFileNameHead = "Zhen.Huan.Zhuan.E";
        String danmuFileNameTail = ".HDTV.720p.x264.AAC-PHD.xml";

        int startIndex = 2127104;
        int totalNumber = 76;
        for (int i = 0; i < totalNumber; i++) {
            int current = startIndex + i;
            String urlName;
            if (i == 7) {
                urlName = head + 898925 + ".xml";   // special case
            }
            else if (i > 7) urlName = head + (current-1) + ".xml";
            else urlName = head + current + ".xml";
            StdOut.println("Read From " + urlName);
            try {
                URL currentURL = new URL(urlName);
                HttpURLConnection connection = (HttpURLConnection) currentURL.openConnection();
                connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
                connection.connect();
                // System.out.println(connection.getContentEncoding());
                InputStream in = connection.getInputStream();
                InflaterInputStream iin = new InflaterInputStream(in, new Inflater(true));
                BufferedReader reader = new BufferedReader(new InputStreamReader(iin));
                String line;
                String currentIndex;
                if (i < 9) currentIndex = "0" + (i + 1);
                else currentIndex = "" + (i+1);
                Out out = new Out(savePath + danmuFileNameHead + currentIndex + danmuFileNameTail);
                while ((line = reader.readLine()) != null)
                    out.println(line);
                out.close();
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
