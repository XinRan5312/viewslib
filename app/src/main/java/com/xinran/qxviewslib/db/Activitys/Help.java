package com.xinran.qxviewslib.db.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xinran.qxviewslib.R;

public class Help extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        View okButton = findViewById(R.id.help_ok);
        okButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.help_ok:
            finish();
            break;
        }
    }
}
