package com.xinran.qxviewslib.aidlservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xinran.qxviewslib.BaseActivity;
import com.xinran.qxviewslib.ISocketService;
import com.xinran.qxviewslib.R;

import java.lang.ref.WeakReference;

/**
 * Created by qixinh on 16/7/21.
 */
public class TestAlDLActivity extends BaseActivity {
    private static final String TAG = TestAlDLActivity.class.getSimpleName();
    private ISocketService iSocketService;
    private TextView mResultText;
    private EditText mEditText;
    private Intent mServiceIntent;

    private SocketReciver mReciver;

    private IntentFilter mIntentFilter;

    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        mResultText = (TextView) findViewById(R.id.resule_text);
        mEditText = (EditText) findViewById(R.id.content_edit);

        mReciver = new SocketReciver(mResultText);

        mServiceIntent = new Intent(this, SocketService.class);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(SocketService.HEART_BEAT_ACTION);
        mIntentFilter.addAction(SocketService.MESSAGE_ACTION);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSocketService = ISocketService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iSocketService = null;
        }
    };

    class SocketReciver extends BroadcastReceiver {
        private WeakReference<TextView> textView;

        public SocketReciver(TextView tv) {
            textView = new WeakReference<TextView>(tv);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            TextView tv = textView.get();
            if (action.equals(SocketService.HEART_BEAT_ACTION)) {
                if (null != tv) {
                    tv.setText("Get a heart heat");
                }
            } else {
                String message = intent.getStringExtra("message");
                tv.setText(message);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocalBroadcastManager.registerReceiver(mReciver, mIntentFilter);
        bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
        mLocalBroadcastManager.unregisterReceiver(mReciver);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                String content = mEditText.getText().toString();
                try {
                    boolean isSend = iSocketService.connectNet(content);//Send Content by socket
                    Toast.makeText(this, isSend ? "success" : "fail",
                            Toast.LENGTH_SHORT).show();
                    mEditText.setText("");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
}
