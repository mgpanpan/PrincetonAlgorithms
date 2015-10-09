import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * Created by pmg on 2015/9/23.
 */
public class PrincetonCS226FTP
{
    public static void main(String[] args)
    {
        String server = "ftp.cs.princeton.edu";
        int port = 21;
        String user = "anonymous";
        String pass = "";

        FTPClient ftpClient = new FTPClient();

        try
        {
            ftpClient.connect(server, port);
            ftpClient.changeToParentDirectory();

            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            System.out.println("Connected");
            String remoteDirPath = "/pub/cs226/";
            String saveDirPath = "E:/Test/PrincetonCS226FTP";
            // if (!ftpClient.changeWorkingDirectory("/pub/cs226/")) System.out.println("CD error!");
            FTPUtil.downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath);

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
