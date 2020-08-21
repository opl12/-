package com.ying.jianxuan.activity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.googd.http.Interface;
import android.googd.http.http;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ying.jianxuan.JNI.JNI;
import com.ying.jianxuan.List.item;
import com.ying.jianxuan.List.list_item;
import com.ying.jianxuan.R;
import com.ying.jianxuan.StatusUtil;
import com.ying.jianxuan.theme.theme;
import com.ying.jianxuan.ui.left.left_adapter;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ying.jianxuan.activity.MainActivity.handler;
import static com.ying.jianxuan.StatusUtil.getStatusBarHeight;
import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;

public class sz_activity extends AppCompatActivity {

    public Context context;

    public String[] strings={"设置IP_API链接","设置主题背景","获取网页UA","自定义UA","恢复默认背景"};


    public String getRealPathFromURI(Context context, Uri contentUri) {

        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if(requestCode==1) {
                // Get the Uri of the selected file
                Toast.makeText(this, "获取到图片文件:" + getRealPathFromUri(this, uri), Toast.LENGTH_LONG).show();
                File file = new File(sd + "bj.txt");
                setfile(file, getRealPathFromUri(this, uri));
                File bj = new File(sd + "bj.txt");
                Bitmap bitmap = BitmapFactory.decodeFile(getfile(bj));
                Resources resources = getResources();
                BitmapDrawable drawable = new BitmapDrawable(resources,
                        bitmap);
                linearLayout.setBackground(drawable);
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }else if (requestCode==2){
                Toast.makeText(this, "获取到自定义UA目录:" + getRealPathFromURI(this, uri), Toast.LENGTH_LONG).show();
                File file = new File(sd + "UA.txt");
                setfile(file, getRealPathFromURI(this, uri));
               // File bj = new File(sd + "UA.txt");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sz);

        StatusUtil statusUtil=new StatusUtil();
        statusUtil.initStatusView(this);

        linearLayout=findViewById(R.id.bk);

        new theme(this).setBackground(linearLayout);
        ImageButton fui=findViewById(R.id.fui);
        fui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED, null);
                finish();

            }
        });

        //高度
        LinearLayout gao=findViewById(R.id.gao);
        gao.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,getStatusBarHeight(this)));

        context=this;
        ListView listView=findViewById(R.id.list);

        list_item list_item=new list_item();
        list_item.setList(R.color.colorAccent,"设置IP_API链接",View.VISIBLE,"爬虫api");
        list_item.setList(R.color.colorAccent,"设置主题背景",View.VISIBLE,"背景");
        list_item.setList(R.color.colorAccent,"恢复默认背景",View.GONE,null);
        list_item.setList(R.color.colorAccent,"获取网页UA",View.VISIBLE,"UA");
        list_item.setList(R.color.colorAccent,"自定义UA",View.GONE,null);
        final List<item> itemList=list_item.getList();

        listView.setAdapter(new left_adapter(this,itemList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title=view.findViewById(R.id.title);
                String m=title.getText().toString();
                if (m.equals("设置IP_API链接")){
                    AlertDialog();
                }else if (m.equals("设置主题背景")){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");//选择图片
                    //intent.setType(“audio/*”); //选择音频
                    //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                    //intent.setType(“video/*;image/*”);//同时选择视频和图片
                    // intent.setType("*/*");//无类型限制
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    try{
                    startActivityForResult(intent, 1);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "亲，木有文件管理器啊-_-!!", Toast.LENGTH_SHORT).show();
                    }
                }else if (m.equals("恢复默认背景")){
                    File file = new File(sd + "bj.txt");
                    if (file.exists()){
                        file.delete();
                    }
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.tmbai));
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                }else if (m.equals("获取网页UA")){
                    final ProgressBar progressBar=setDownload();
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {

                                http http = new http();
                                URL url=new URL("http://1-2.ltd/apiua.php");

                                http.http(url);
                                http.setTime(10000);

                                http.setProgressBar(new Interface() {
                                    @Override
                                    public void MAX(int  max, int a, final String   bfb) {

                                        // Log.d("超时",""+max+a+bfb);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    progressBar.setProgress(Integer.parseInt(bfb));
                                                }catch (Exception e){
                                                    Toast.makeText(sz_activity.this,"异步下载完成",Toast.LENGTH_LONG).show();
                                                }
                                                if (progressBar.getProgress()==100){
                                                    Toast.makeText(sz_activity.this,"下载完成",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }});

                                File file=new File(sd+"UA");
                                if (file.exists()){
                                    file.delete();
                                }
                                setfile(file,http.get());
                            }catch (final Exception e){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"下载失败:"+e,Toast.LENGTH_LONG).show();
                                    }});
                            }}

                    }.start();

                }else if (m.equals("自定义UA")){

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    //intent.setType("image/*");//选择图片
                    //intent.setType(“audio/*”); //选择音频
                    //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                    //intent.setType(“video/*;image/*”);//同时选择视频和图片
                    intent.setType("*/*");//无类型限制
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    try {
                        startActivityForResult(intent, 2);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "亲，木有文件管理器啊-_-!!", Toast.LENGTH_SHORT).show();
                    }
                } }});

    }


    ProgressBar setDownload(){

        View view=getLayoutInflater().inflate(R.layout.downloadurl,null,false);
        ProgressBar progressBar=view.findViewById(R.id.progress);
        Button no=view.findViewById(R.id.no);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this,R.style.AlertDialog);
        alertDialog.setView(view);
        final AlertDialog alertDialog1=alertDialog.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
        return progressBar;

    }

    void AlertDialog(){

        final EditText editText=new EditText(this);
        File file=new File(sd+"api.txt");
        if (file.exists()){
            editText.setText(new JNI().getFile(sd+"api.txt"));
        }

        AlertDialog.Builder a=new AlertDialog.Builder(this);
        a.setTitle("请输入API").setView(editText);;
        a.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals(null)) {
                    Toast.makeText(sz_activity.this,  "输入框为空", Toast.LENGTH_LONG).show();
                }else {

                    File file = new File(sd + "api.txt");
                    setfile(file, editText.getText().toString());
                    Toast.makeText(sz_activity.this, editText.getText().toString() + "保存成功", Toast.LENGTH_LONG).show();

                }}
        });

        a.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        a.show();

    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {

        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }

    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
