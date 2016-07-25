package com.xinran.qxviewslib.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.xinran.qxviewslib.Fruit;
import com.xinran.qxviewslib.ISocketService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by qixinh on 16/7/21.
 */
public class SocketService extends Service {
    private static final String TAG = SocketService.class.getSimpleName();
    private static final long HEART_BEAT_RATE = 3 * 1000;

    public static final String HOST = "com.baidu";// "192.168.1.21";//
    public static final int PORT = 9800;

    public static final String MESSAGE_ACTION = "com.xinran.message_ACTION";
    public static final String HEART_BEAT_ACTION = "com.xinran.heart_beat_ACTION";

    private ReadThread mReadThread;

    private LocalBroadcastManager mLocalBroadcastManager;

    private WeakReference<Socket> mSocket;

    // For heart Beat
    private Handler mHandler = new Handler();
    //负责真正链接服务端
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                boolean isSuccess = connectNet("");//就发送一个\r\n过去 如果发送失败，就重新初始化一个socket
                if (!isSuccess) {
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    new InitSockeThread().start();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private long sendTime = 0L;
    private ISocketService.Stub iSocketService = new ISocketService.Stub() {
        @Override
        public boolean connectNet(String params) throws RemoteException {
            return connectNet(params);
        }

        @Override
        public Fruit getFruit() throws RemoteException {
            return new Fruit("apple", 8);
        }
    };

    private boolean connectNet(String params) {
        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iSocketService;
    }

    //这个Thread只是为了初始化Socket的
    class InitSockeThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Socket so = new Socket(HOST, PORT);
                mSocket = new WeakReference<Socket>(so);
                mReadThread = new ReadThread(so);
                mReadThread.start();
                mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (!sk.isClosed()) {
                    sk.close();
                }
                sk = null;
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to read content from Socket
    class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<Socket>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer,
                                    length)).trim();
                            Log.e(TAG, message);
                            //收到服务器过来的消息，就通过Broadcast发送出去
                            if (message.equals("ok")) {//处理心跳回复
                                Intent intent = new Intent(HEART_BEAT_ACTION);
                                mLocalBroadcastManager.sendBroadcast(intent);
                            } else {
                                //其他消息回复
                                Intent intent = new Intent(MESSAGE_ACTION);
                                intent.putExtra("message", message);
                                mLocalBroadcastManager.sendBroadcast(intent);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
