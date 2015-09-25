import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * Created by pmg on 2015/9/24.
 */
public class KdTreeTestFileDownload
{
    public static void main(String[] args)
    {
        String server = "coursera.cs.princeton.edu";
        int port = 21;
        String user = "anonymous";
        String pass = "";

        FTPClient ftpClient = new FTPClient();

        try
        {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            System.out.println("Connected");
            String removeDirPath = "/algs4/testing/kdtree";
            String saveDirPath = "E:/Test/kdTreeTest";
            // if (!ftpClient.changeWorkingDirectory("/pub/cs226/")) System.out.println("CD error!");
            FTPUtil.downloadDirectory(ftpClient, removeDirPath, "", saveDirPath);

            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
}
