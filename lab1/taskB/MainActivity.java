package com.example.lab1;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ContentResolver mContentResolver = null;
    //ADD
    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(0x7F0B001C);
        Button startBtn = (Button)this.findViewById(0x7F080057);
        Button stopBtn = (Button)this.findViewById(0x7F080058);
        Button statBtn = (Button)this.findViewById(0x7F080059);
        Button dynaBtn = (Button)this.findViewById(0x7F08005A);
        Button testBtn = (Button)this.findViewById(0x7F08005B);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        statBtn.setOnClickListener(this);
        dynaBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);
        MyClass1 br = new MyClass1();
        IntentFilter intentFilter = new IntentFilter("com.android.skill");
        intentFilter.addAction("android.intent.action.MY_BROADCAST");
        this.registerReceiver(br, intentFilter);
        TextView tv = (TextView)this.findViewById(0x7F080151);
        this.mContentResolver = this.getContentResolver();
        tv.setText("Add initial data ");
        int i;
        for(i = 0; i < 10; ++i) {
            ContentValues values = new ContentValues();
            values.put("name", "haha" + i);
            this.mContentResolver.insert(Constant.CONTENT_URI, values);
        }
    }
    //ADD

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                //ADD
                case 0x7F080057:
                //ADD
                    Intent startIntent = new Intent(this, MyClass2.class);
                    startService(startIntent);
                    break;
                //ADD
                case 0x7F080058:
                //ADD
                    Intent stopIntent = new Intent(this, MyClass2.class);
                    stopService(stopIntent);
                    break;
                //ADD
                case 0x7F080059:
                //ADD
                    Intent custIntent = new Intent();
                    custIntent.setAction("com.example.CUSTOM_INTENT");
                    custIntent.setPackage("com.example.myapplication");
                    sendBroadcast(custIntent);
                //ADD
                case 0x7F08005A: {
                    this.sendBroadcast(new Intent("android.intent.action.MY_BROADCAST"));
                }
                case 0x7F08005B: {
                    @SuppressLint("ResourceType") TextView tv = (TextView)this.findViewById(0x7F080151);  // id:textView
                    tv.setText("Query Data ");
                    @SuppressLint("Recycle") Cursor v1 = this.mContentResolver.query(Constant.CONTENT_URI, new String[]{"_id", "name"}, null, null, null);
                    if(v1.moveToFirst()) {
                        tv.setText("The first data： " + v1.getString(v1.getColumnIndex("name")));
                    }

                }
                //ADD
            }
        }
    }

}