package net.cps.myapplication;

import com.snatik.storage.Storage;

import java.io.File;

public class Utils {
    public static final String BACKUP_DIR = "backup_moneykeeper";
    public static final String SUFFIX = ".db";
    public static final String AUTO_BACKUP_PREFIX = "MoneyKeeperBackupAuto";
    public static final String USER_BACKUP_PREFIX = "MoneyKeeperBackupUser";
    public static final String BACKUP_SUFFIX = BApp.getInstance().getString(R.string.text_before_reverting);
    String fileName = AUTO_BACKUP_PREFIX + SUFFIX;

    private static boolean backupDB(String fileName) {
        Storage storage = new Storage(BApp.getInstance());
        boolean isWritable = Storage.isExternalWritable();
        if (!isWritable) {
            return false;
        }
        String path = storage.getExternalStorageDirectory() + File.separator + BACKUP_DIR;
        if (!storage.isDirectoryExists(path)) {
            storage.createDirectory(path);
        }
        String filePath = path + File.separator + fileName;
        if (!storage.isFileExist(filePath)) {
            // 创建空文件，在模拟器上测试，如果没有这个文件，复制的时候会报 FileNotFound
            storage.createFile(filePath, "");
        }
        return storage.copy(BApp.getInstance().getDatabasePath(BApp.getDBName()).getPath(), path + File.separator + fileName);
    }

    public static boolean restoreDB(String restoreFile) {
        Storage storage = new Storage(BApp.getInstance());
        if (storage.isFileExist(restoreFile)) {
            // 恢复之前，备份一下最新数据
            String fileName = "MoneyKeeperBackup" + DateUtils.getCurrentDateString() + BACKUP_SUFFIX + SUFFIX;
            boolean isBackupSuccess = backupDB(fileName);
            boolean isRestoreSuccess = storage.copy(restoreFile, BApp.getInstance().getDatabasePath(BApp.getDBName()).getPath());
            return isBackupSuccess && isRestoreSuccess;
        }
        return false;
    }
}
