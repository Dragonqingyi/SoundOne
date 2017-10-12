package com.dragonyang.soundone;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

/**
 * 即时播放声音小案例
 */
public class MainActivity extends AppCompatActivity {
    Button button1,button2;
    SoundPool sp;       //声明SoundPool的引用
    HashMap<Integer,Integer> hm;        //声明HashMap存放声音
    int currStreamId;       //当前正在播放的声音id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        initSoundPool();        //初始化声音池
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1,0);
                Toast.makeText(MainActivity.this,"播放即时音效",Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.stop(currStreamId);
                Toast.makeText(MainActivity.this,"停止播放即时音效",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void playSound(int sound, int loop) {        //播放声音的方法
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);  //获取AudioManager的引用
        float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);      //获取当前音量
        float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       //获取系统最大音量
        float volume = streamVolumeCurrent / streamVolumeMax;                           //计算得到播放音量
        currStreamId = sp.play(hm.get(sound),volume,volume,1,loop,1.0f);
    }

    public void initSoundPool() {       //初始化声音池
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC,0);     //创建SoundPool对象
        hm = new HashMap<>();
        //加载声音文件，并设置为1号声音放入哈希
        hm.put(1,sp.load(this,R.raw.musictest,1));
    }
}
