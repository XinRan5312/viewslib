package com.xinran.qxviewslib.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by qixinh on 16/6/24.
 * 来自：http://www.tuicool.com/articles/fUBRfe
 */
public class ShareMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    init();
    }

    private void init() {
        Button button=new Button(this);
        button.setText("Share");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setType("img/*");表示图片
                // intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("图片地址"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "My first share wr ...");
                startActivity(Intent.createChooser(intent, "Share-Share"));

            }
        });
       setContentView(button);
    }
}
