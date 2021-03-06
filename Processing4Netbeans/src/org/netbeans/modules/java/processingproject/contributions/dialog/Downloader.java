/**
 *   This file is part of Processing4Netbeans Netbeans plugin, 
 *   initially created by fujeyla.
 *
 *   Processing4Netbeans is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Processing4Netbeans is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Processing4Netbeans.  If not, see <https://www.gnu.org/licenses/>
 */

package org.netbeans.modules.java.processingproject.contributions.dialog;

import java.io.*;
import java.net.*;
import java.util.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Downloader extends Observable implements Runnable {

    private static final int MAX_BUFFER_SIZE = 1024;

    public static final String STATUSES[] = {"Downloading",
        "Paused", "Complete", "Cancelled", "Error"};

    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETE = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;

    private final URL url; // download URL
    private final String folderPath;
    private String downloadedFileName;

    private IntegerProperty sizeProperty = new SimpleIntegerProperty();
    private IntegerProperty downloadedProperty = new SimpleIntegerProperty();
    private FloatProperty progressProperty = new SimpleFloatProperty();
    private BooleanProperty runningProperty = new SimpleBooleanProperty();

    private int status; // current status of download


    public Downloader(URL url, String folderPath) {
        this.url = url;
        this.folderPath = folderPath;
        sizeProperty.set(-1);
        downloadedProperty.set(0);
        status = DOWNLOADING;

        // Begin the download.
        download();
    }

    public String getUrl() {
        return url.toString();
    }

    public int getSize() {
        return sizeProperty.get();
    }

    public float getProgress() {
        return ((float) downloadedProperty.get() / sizeProperty.get());
    }

    public int getStatus() {
        return status;
    }

    public void pause() {
        status = PAUSED;
        stateChanged();
    }

    public void resume() {
        status = DOWNLOADING;
        stateChanged();
        download();
    }

    public void cancel() {
        status = CANCELLED;
        stateChanged();
    }

    private void error() {
        status = ERROR;
        stateChanged();
    }

    private void download() {
        Thread thread = new Thread(this);
        thread.start();
    }

    private String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }

// Download file.
    public void run() {
        RandomAccessFile file = null;
        InputStream stream = null;

        try {
            // Open connection to URL.
            HttpURLConnection connection
                    = (HttpURLConnection) url.openConnection();

            // Specify what portion of file to download.
            connection.setRequestProperty("Range",
                    "bytes=" + downloadedProperty.get() + "-");

            // Connect to server.
            connection.connect();

            // Make sure response code is in the 200 range.
            if (connection.getResponseCode() / 100 != 2) {
                error();
            }

            // Check for valid content length.
            int contentLength = connection.getContentLength();
            if (contentLength < 1) {
                error();
            }

            /* Set the size for this download if it
     hasn't been already set. */
            if (sizeProperty.get() == -1) {
                sizeProperty.set(contentLength);
                stateChanged();
                runningProperty.set(true);
            }

            // Open file and seek to the end of it.
            this.downloadedFileName = folderPath + getFileName(url);
            file = new RandomAccessFile(downloadedFileName, "rw");
            file.seek(downloadedProperty.get());

            stream = connection.getInputStream();
            while (status == DOWNLOADING) {
                /* Size buffer according to how much of the
       file is left to download. */
                byte buffer[];
                if (sizeProperty.get() - downloadedProperty.get() > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[sizeProperty.get() - downloadedProperty.get()];
                }

                // Read from server into buffer.
                int read = stream.read(buffer);
                if (read == -1) {
                    break;
                }

                // Write buffer to file.
                file.write(buffer, 0, read);
                downloadedProperty.set(downloadedProperty.get() + read);
                progressProperty.set(getProgress());
                stateChanged();
            }

        } catch (Exception e) {
            error();
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {
                }
            }

            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                }
            }
            /* Change status to complete if this point was
     reached because downloading has finished. */
            if (status == DOWNLOADING) {
                status = COMPLETE;
                runningProperty.set(false);
                stateChanged();
            }
        }
    }

    private void stateChanged() {
        setChanged();
        notifyObservers();
    }

    public IntegerProperty getDownloadedProperty() {
        return downloadedProperty;
    }

    public IntegerProperty getSizeProperty() {
        return sizeProperty;
    }

    public FloatProperty getProgressProperty() {
        return progressProperty;
    }

    public BooleanProperty getRunningProperty() {
        return runningProperty;
    }

    public String getDownloadedFilePath() {
        return downloadedFileName;
    }

}
