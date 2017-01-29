/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processinglibrarymanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Jerome
 */
public class ProcessingLibraryManager {

    static final String LISTING_URL = "http://download.processing.org/contribs";
    static final String LOCAL_FILENAME = "contribs.txt";

//    static public byte[] gzipEncode(byte[] what) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        GZIPOutputStream output = new GZIPOutputStream(baos);
//        PApplet.saveStream(output, new ByteArrayInputStream(what));
//        output.close();
//        return baos.toByteArray();
//    }

    static boolean download(URL source, byte[] post, File dest) {
        boolean success = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) source.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(60 * 1000);

            if (post == null) {
                conn.setRequestMethod("GET");
                conn.connect();
            } 
            
//            else {
//                post = gzipEncode(post);
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty("Content-Encoding", "gzip");
//                conn.setRequestProperty("Content-Length", String.valueOf(post.length));
//                conn.setUseCaches(false);
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                conn.getOutputStream().write(post);
//            }

            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(dest);

            byte[] b = new byte[8192];
            int amount;
                int total = 0;
                while ((amount = in.read(b)) != -1) {
                    out.write(b, 0, amount);
                    total += amount;
                }
            out.flush();
            out.close();
            success = true;

        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();
        } catch (IOException ioe) {
              ioe.printStackTrace();
        }
        return success;
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URL url = new URL(LISTING_URL);
            File tempContribFile = new File(LOCAL_FILENAME);
            tempContribFile.setWritable(true);
            download(url, null, tempContribFile);    
            ContributionsReader contributionsReader = new ContributionsReader(tempContribFile.getPath());
            List<Contribution> contributions = contributionsReader.read();
            for (Contribution contribution : contributions) {
                System.out.println("Contribution is : " + contribution);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
