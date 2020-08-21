package com.ying.jianxuan.ui.ui.xbao.http;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.tencent.smtt.sdk.DownloadListener;
import com.ying.jianxuan.R;

import java.text.DecimalFormat;

public class MyDownloadStart implements DownloadListener {
    public Activity activity;
    public MyDownloadStart(Activity activity) {
        this.activity=activity;
    }
    @Override
    public void onDownloadStart(final String url, String userAgent, final String contentDisposition, String mimetype, long contentLength) {
        Log.i("tag", "url="+url);
        Log.i("tag", "userAgent="+userAgent);
        Log.i("tag", "contentDisposition="+contentDisposition);
        Log.i("tag", "mimetype="+mimetype);
        Log.i("tag", "contentLength="+contentLength);
        try {
            //   final String ns=contentDisposition.substring(contentDisposition.indexOf("name=")+"name=".length(),contentDisposition.length());
            AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
            View view = activity.getLayoutInflater().inflate(R.layout.download, null);
            Button xiaz = view.findViewById(R.id.xiaz);
            Button qxiaoa = view.findViewById(R.id.qxiao);
            TextView apk=view.findViewById(R.id.apk);
            final String fg[]=contentDisposition.split("=");
            if (fg[1].substring(fg[1].length()-3,fg[1].length()).equals("zip")) {
                apk.setText(fg[1].replace(" ", ""));
            }else {
                String f[]=url.split("/");
                apk.setText(f[f.length-1]);
            }
            TextView MB=view.findViewById(R.id.MB);
            TextView urL=view.findViewById(R.id.urL);
            urL.setText(url);
            MB.setText("文件大小:"+MB(contentLength));
            normalDialog.setView(view);
            //显示是否下载的dialog
            final AlertDialog alertDialog = normalDialog.show();
            xiaz.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//下载时，下载完成后显示通知
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
                                            request.setDestinationInExternalPublicDir("线报", fg[fg.length-1]);
                                            request.setVisibleInDownloadsUi(true);
                                            downloadManager.enqueue(request);
                                            alertDialog.dismiss();
                                            Toast.makeText(activity,"下载位置:线报，目录",Toast.LENGTH_LONG).show();
                                        }

                                    }
            );

            qxiaoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }catch (Exception e){

            Log.d("tag",""+e);
        }


    }
    private static String MB(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
}