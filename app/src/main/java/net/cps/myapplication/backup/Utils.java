package net.cps.myapplication.backup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.snatik.storage.Storage;

import net.cps.myapplication.BApp;
import net.cps.myapplication.R;
import net.cps.myapplication.model.BackupBean;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public static final String BACKUP_DIR = "backup_money_mitu";
    public static final String SUFFIX = ".db";
    public static final String AUTO_BACKUP_PREFIX = "Money_keep_BackupAuto";
    public static final String USER_BACKUP_PREFIX = "Money_keep_BackupUser";
    public static final String BACKUP_SUFFIX = BApp.getInstance().getString(R.string.text_before_reverting);
    public static final String fileName = AUTO_BACKUP_PREFIX + SUFFIX;

    public static void startBackUp(Activity activity, final String filename){
        new AlertDialog.Builder(activity)
                .setTitle(R.string.text_backup)
                .setMessage(R.string.text_backup_save)
                .setNegativeButton(R.string.text_button_cancel, null)
                .setPositiveButton(R.string.text_affirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backupDB(filename);
                    }
                })
                .create()
                .show();
    }

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
        Log.d("filePath", "backupDB: " + filePath);
        return storage.copy(BApp.getInstance().getDatabasePath(BApp.getDBName()).getPath(), path + File.separator + fileName);
    }

    public static void startRestoreDb(final Activity activity, List<BackupBean> backupBeans){
        BackupFliesDialog dialog = new BackupFliesDialog(activity, backupBeans);
        dialog.setOnItemClickListener(new BackupFliesDialog.OnItemClickListener() {
            @Override
            public void onClick(File file) {
               boolean issuccess= restoreDB(file.getPath());
               if (issuccess){
                   Utils.restart(activity);
               }
            }
        });
        dialog.show();
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

    //显示文件浏览器
    public static void showFileChooser(Activity activity) {
        Storage storage = new Storage(BApp.getInstance());
        boolean isWritable = Storage.isExternalWritable();
        if (!isWritable) {
            return ;
        }
        String path = storage.getExternalStorageDirectory() + File.separator + BACKUP_DIR;
        if (!storage.isDirectoryExists(path)) {
            storage.createDirectory(path);
        }
        String filePath = path + File.separator + fileName;
        File file = new File(filePath);
        //获取父目录
        File parentFlie = new File(file.getParent());
        Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, activity.getPackageName()+ ".fileprovider", parentFlie);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(parentFlie), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    public static List<BackupBean> getBackupFiles() {
        Storage storage = new Storage(BApp.getInstance());
        String dir = storage.getExternalStorageDirectory() + File.separator + BACKUP_DIR;
        List<BackupBean> backupBeans = new ArrayList<>();
        BackupBean bean;
        List<File> files = storage.getFiles(dir, "[\\S]*\\.db");
        if (files == null) {
            return backupBeans;
        }
        File fileTemp;
        PrettyTime prettyTime = new PrettyTime();
        for (int i = 0; i < files.size(); i++) {
            fileTemp = files.get(i);
            bean = new BackupBean();
            bean.file = fileTemp;
            bean.name = fileTemp.getName();
            bean.size = storage.getReadableSize(fileTemp);
            bean.time = prettyTime.format(new Date(fileTemp.lastModified()));
            backupBeans.add(bean);
        }
        return backupBeans;
    }

    public static void restart(Activity activity){
         final Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         activity.startActivity(intent);
    }
}
