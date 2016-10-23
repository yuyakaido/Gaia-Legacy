package com.yuyakaido.android.blueprint.util;

import android.content.Context;

import com.yuyakaido.android.blueprint.infra.constant.InfraConst;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by ohshiro on 16/03/10.
 */
public class ExportUtil {

    public static void export(Context context) {
        File dbFile = getDatabaseFile(context);
        File spFile = getSharedPreferencesFile(context);
        File anrFile = getAnrFile();
        File exportFile = getExportFile(context);

        List<File> targets = new ArrayList<>();
        if (dbFile.exists()) {
            targets.add(dbFile);
        }
        if (spFile.exists()) {
            targets.add(spFile);
        }
        if (anrFile.exists()) {
            targets.add(anrFile);
        }

        if (!targets.isEmpty()) {
            if (exportFile.exists()) {
                exportFile.delete();
            }

            byte[] buffer = new byte[8192];
            try {
                ZipOutputStream outputStream = new ZipOutputStream(
                        new BufferedOutputStream(new FileOutputStream(exportFile)));

                for (File file : targets) {
                    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

                    ZipEntry entry = new ZipEntry(file.getName());
                    outputStream.putNextEntry(entry);

                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }

                    inputStream.close();
                    outputStream.closeEntry();
                }

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File getDatabaseFile(Context context) {
        return new File("/data/data/" + context.getPackageName() + "/databases/" + InfraConst.Database.DB_NAME);
    }

    private static File getSharedPreferencesFile(Context context) {
        String packageName = context.getPackageName();
        return new File("/data/data/" + packageName + "/shared_prefs/" + packageName + "_preferences.xml");
    }

    private static File getAnrFile() {
        return new File("/data/anr/traces.txt");
    }

    private static File getExportFile(Context context) {
        return new File(context.getFilesDir().getAbsolutePath() + "/export.zip");
    }

}
