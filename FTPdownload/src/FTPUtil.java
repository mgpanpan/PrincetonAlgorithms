import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

/**
 * Created by pmg on 2015/9/23.
 */

public class FTPUtil
{
    public static void downloadDirectory(FTPClient ftpClient, String parentDir, String currentDir, String saveDir)
        throws IOException
    {
        String dirToList = parentDir;
        if (!currentDir.equals(""))
            dirToList += "/" + currentDir;
        FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        if (subFiles != null && subFiles.length > 0)
        {
            for (FTPFile aFile : subFiles)
            {
                String currentFileName = aFile.getName();
                if (currentFileName.equals(".") || currentFileName.equals(".."))
                    continue; // skip parent directory and the directory itself
                String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
                if (currentDir.equals(""))
                    filePath = parentDir + "/" + currentFileName;

                String newDirPath = saveDir + parentDir + File.separator +
                        currentDir + File.separator + currentFileName;
                if (currentDir.equals(""))
                    newDirPath = saveDir + parentDir + File.separator + currentFileName;

                if (aFile.isDirectory())
                {
                    File newDir = new File(newDirPath);
                    boolean created = newDir.mkdirs();
                    if (created)
                        System.out.println("CREATED the directory: " + newDirPath);
                    else
                        System.out.println("COULD NOT create the directory: " + newDirPath);

                    // download the sub directory
                    downloadDirectory(ftpClient, dirToList, currentFileName, saveDir);
                }
                else
                {
                    // download the file
                    boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);
                    if (success)
                        System.out.println("DOWNLOADED the file: " + filePath);
                    else
                        System.out.println("COULD NOT download the file: " + filePath);
                }
            }
        }
    }

    public static boolean downloadSingleFile(FTPClient ftpClient, String remoteFilePath, String savePath)
        throws IOException
    {
        File downloadFIle = new File(savePath);
        File parentDir = downloadFIle.getParentFile();
        if (!parentDir.exists())
            parentDir.mkdir();

        OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(downloadFIle));
        try
        {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.retrieveFile(remoteFilePath, outputStream);
        }
        catch (IOException ex)
        {
            throw ex;
        }
        finally
        {
            if (outputStream != null)
                outputStream.close();
        }
    }
}
