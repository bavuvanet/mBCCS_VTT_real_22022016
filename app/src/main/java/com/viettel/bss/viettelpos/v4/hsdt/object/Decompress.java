package com.viettel.bss.viettelpos.v4.hsdt.object;

import com.viettel.bss.viettelpos.v4.utils.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by thuandq on 11/6/2017.
 */

public class Decompress {
    String _zipFile;
    String _location;

    public Decompress(String zipFile, String location, String url) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
        downloadFiles(url);
    }

    public List<String> unzip() {
        List<String> result=new ArrayList<>();
        String path = "";
        try {
            FileInputStream fin = new FileInputStream(_location + "/" + _zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            byte b[] = new byte[1024];
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.out("Decompress", "Unzipping " + ze.getName());
                path = _location + "/" + ze.getName();
                if (ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(path);
                    BufferedInputStream in = new BufferedInputStream(zin);
                    BufferedOutputStream out = new BufferedOutputStream(fout);
                    int n;
                    while ((n = in.read(b, 0, 1024)) >= 0) {
                        out.write(b, 0, n);
                    }
                    zin.closeEntry();
                    out.close();
                    result.add(path);
                }
            }
            zin.close();
        } catch (Exception e) {
            Log.e("Decompress unzip" + e.getMessage());
        }
        return result;
    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }

    public void downloadFiles(String url) {
        try {
            URL u = new URL(url);
            InputStream is = u.openStream();
            DataInputStream dis = new DataInputStream(is);
            byte[] buffer = new byte[1024];
            int length;
            FileOutputStream fos = new FileOutputStream(new File(_location + "/" + _zipFile));
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate" + "malformed url error" + mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate" + " io error " + ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate security error " + se);
        }
    }
}


