package com.dsy.recorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zero.smallvideorecord.LocalMediaCompress;
import com.zero.smallvideorecord.Log;
import com.zero.smallvideorecord.model.AutoVBRMode;
import com.zero.smallvideorecord.model.LocalMediaConfig;
import com.zero.smallvideorecord.model.OnlyCompressOverBean;

public class MainActivity extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_recorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,RecorderActivity.class);
                startActivityForResult(it,0x111);
            }
        });

        textView=findViewById(R.id.tv);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0x123) {
            if (data != null) {
                final String videoPath = data.getStringExtra("PATH");
                if (!TextUtils.isEmpty(videoPath)) {

                    textView.append("视频已保存到："+videoPath+"\n");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //视频压缩
                            LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
                            final LocalMediaConfig config = buidler
                                    .setVideoPath(videoPath)
                                    .captureThumbnailsTime(1)
                                    .doH264Compress(new AutoVBRMode())
                                    .setFramerate(15)
                                    .build();
                            OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();

                            System.out.println("压缩视频地址:"+onlyCompressOverBean.getVideoPath());
                            System.out.println("压缩视频预览图:"+onlyCompressOverBean.getPicPath());

                            //TODO 执行上传操作
                        }
                    }).start();

                }
            }
        }
    }
}
