package com.example.filework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.math.BigDecimal;
import java.util.Stack;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button ok,read;
    private EditText name,id,age;
    private TextView show;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(MainActivity.this);
        ok=(Button)findViewById(R.id.ok);
        read=(Button)findViewById(R.id.read);
        name=(EditText)findViewById(R.id.input_name);
        id=(EditText)findViewById(R.id.input_id);
        age=(EditText)findViewById(R.id.input_age);
        show=(TextView)findViewById(R.id.show);
        ok.setOnClickListener(this);
        read.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
            StringBuilder data = new StringBuilder();
            data.append(name.getText().toString());
            data.append("|" + id.getText().toString());
            data.append("|" + age.getText().toString());
            System.out.println("data内容为"+data.toString());
                FileOutputStream out = null;
                BufferedWriter writer = null;
            //FileUtil fileUtil = new FileUtil();
            //fileUtil.creadSDDir("filework");
            try {
                //File file = fileUtil.creadSDfile("file");
               // if (file.exists()) {
                    System.out.println("文件存在");
                    out = openFileOutput( "file", MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    writer.write(data.toString());
                    System.out.println("写入成功");
               // }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer!=null)
                {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case R.id.read:
                FileInputStream in=null;
                BufferedReader reader=null;
                StringBuilder content=new StringBuilder();
                try {
                    //File file=new File( Environment.getExternalStorageDirectory()+"filework/file");
                    //if(file.exists()) {
                        System.out.println("文件存在");
                        in = openFileInput( "file");
                        reader = new BufferedReader(new InputStreamReader(in));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            content.append(line);
                        }
                   // }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(reader!=null)
                    {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("content内容为"+content);
                String all=content.toString();
                String [] a=all.split("[|]");
                show.setText("姓名为"+a[0]+"\n"+"学号为"+a[1]+"\n"+"年龄"+a[2]);
                break;
                default:break;
        }
    }
}
