package com.shiftscheduler.utils;

import com.google.common.io.Files;
import com.shiftscheduler.persistence.DatabaseHelper;

import java.io.File;
import java.io.IOException;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/SC.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        DatabaseHelper.setDbUrl(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
