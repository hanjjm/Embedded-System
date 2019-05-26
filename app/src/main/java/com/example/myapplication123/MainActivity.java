package com.example.myapplication123;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class MainActivity extends AppCompatActivity {
    TextView mTvBluetoothStatus;
    TextView mTvReceiveData;
    TextView mTvSendData;
    Button mBtnBluetoothOn;
    Button mBtnBluetoothOff;
    Button mBtnConnect;
    Button mBtnSendData;
    Button btnSend;
    Button btnSend2;
    Button btnSend3;

    public static BluetoothSPP bt;

    SeekBar mSeekBar;
    TextView mTxtValue;
    String value;
    SeekBar mSeekBar2;
    TextView mTxtValue2;
    String value2;
    SeekBar mSeekBar3;
    TextView mTxtValue3;
    String value3;
    TextView nowtemper1;
    TextView nowtemper2;
    TextView nowtemper3;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = new BluetoothSPP(getApplicationContext()); //Initializing
        mTvBluetoothStatus = (TextView)findViewById(R.id.tvBluetoothStatus);
        mTvReceiveData = (TextView)findViewById(R.id.tvReceiveData);
        mTvSendData =  (EditText) findViewById(R.id.tvSendData);
        mBtnBluetoothOn = (Button)findViewById(R.id.btnBluetoothOn);
        mBtnBluetoothOff = (Button)findViewById(R.id.btnBluetoothOff);
        mBtnConnect = (Button)findViewById(R.id.btnConnect);
        mBtnSendData = (Button)findViewById(R.id.btnSendData);

        btnSend = findViewById(R.id.sendbtn);
        btnSend2 = findViewById(R.id.sendbtn2);
        btnSend3 = findViewById(R.id.sendbtn3);

        nowtemper1 = findViewById(R.id.now_temper1);
        nowtemper2 = findViewById(R.id.now_temper2);
        nowtemper3 = findViewById(R.id.now_temper3);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        mBtnBluetoothOn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOn();
            }
        });
        mBtnBluetoothOff.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOff();
            }
        });
        mBtnConnect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPairedDevices();
            }
        });
/*        mBtnSendData.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mThreadConnectedBluetooth != null) {
                    mThreadConnectedBluetooth.write(mTvSendData.getText().toString()+"A");
                    mTvSendData.setText("");
                }
            }
        });*/

     //   Button btnSend = findViewById(R.id.sendbtn); //데이터 전송
        btnSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
              //  bt.send("1"+"P", true);
              //  bt.send(value+"A", true);
                if(mThreadConnectedBluetooth != null) {
                    mThreadConnectedBluetooth.write("1P");
                    mThreadConnectedBluetooth.write(value + "A");
                }
            }
        });

        btnSend2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
/*                bt.send("2"+"P", true);
                bt.send(value2+"A", true);*/
                if(mThreadConnectedBluetooth != null) {
                    mThreadConnectedBluetooth.write("2P");
                    mThreadConnectedBluetooth.write(value2 + "A");
                }

            }
        });

        btnSend3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
/*                bt.send("3"+"P", true);
                bt.send(value3+"A", true);*/
                if(mThreadConnectedBluetooth != null) {
                    mThreadConnectedBluetooth.write("3P");
                    mThreadConnectedBluetooth.write(value3 + "A");
                }
            }
        });


/*        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                //     Toast.makeText(getContext(), "SADF", Toast.LENGTH_SHORT);
                //   Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                Log.i("abab", message);
                Log.i("abab", "ASD");
            }
        });*/


        mBluetoothHandler = new Handler(){

            public void handleMessage(android.os.Message msg){
               // Log.i("tah", "ASDFASDFF");
               // Toast.makeText(getApplicationContext(), "ASD", Toast.LENGTH_SHORT);
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    Toast.makeText(getApplicationContext(), "ASD", Toast.LENGTH_SHORT);
                    try {
                        readMessage = new String((byte[]) msg.obj , "UTF-8");
                      //  Log.i("a", new String((byte[]) msg.obj));
                        Toast.makeText(getApplicationContext(), new String((byte[]) msg.obj), Toast.LENGTH_SHORT);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
               //     readMessage.
                    if(readMessage.charAt(0) == 'a') nowtemper1.setText("현재온도 : " + readMessage.substring(3, 5) + "도");
                    else if(readMessage.charAt(0) == 'b') nowtemper2.setText("현재온도 : " + readMessage.substring(3, 5) + "도");
                    else if(readMessage.charAt(0) == 'c') nowtemper3.setText("현재온도 : " + readMessage.substring(3, 5) + "도");
                   // Toast.makeText(getApplicationContext(), "ASD", Toast.LENGTH_SHORT);
                }
            }
        };


        mSeekBar = findViewById(R.id.temperature);
        mTxtValue = findViewById(R.id.temper);
        mSeekBar2 = findViewById(R.id.temperature2);
        mTxtValue2 = findViewById(R.id.temper2);
        mSeekBar3 = findViewById(R.id.temperature3);
        mTxtValue3 = findViewById(R.id.temper3);
        nowtemper1 = findViewById(R.id.now_temper1);
        nowtemper2 = findViewById(R.id.now_temper2);
        nowtemper3 = findViewById(R.id.now_temper3);


        mSeekBar.setProgress(35);
       // mSeekBar.setMin(30);
        mSeekBar.setMax(70);
        //mSeekBar.setMin(10);
        value = String.valueOf(mSeekBar.getProgress());
        mTxtValue.setText(value);

        mSeekBar2.setProgress(30);
        mSeekBar2.setMax(70);
        value2 = String.valueOf(mSeekBar2.getProgress());
        mTxtValue2.setText(value2);

        mSeekBar3.setProgress(30);
        mSeekBar3.setMax(70);
        value3 = String.valueOf(mSeekBar3.getProgress());
        mTxtValue3.setText(value3);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = String.valueOf(progress);
                mTxtValue.setText(value);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "온도조절 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "온도조절 끝났습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value2 = String.valueOf(progress);
                mTxtValue2.setText(value2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "온도조절 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "온도조절 끝났습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        mSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value3 = String.valueOf(progress);
                mTxtValue3.setText(value3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "온도조절 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "온도조절 끝났습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void bluetoothOn() {
        if(mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        }
        else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
                mTvBluetoothStatus.setText("활성화");
            }
            else {
                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }
    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
            mTvBluetoothStatus.setText("비활성화");
        }
        else {
            Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
                    mTvBluetoothStatus.setText("활성화");
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                    mTvBluetoothStatus.setText("비활성화");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    void connectSelectedDevice(String selectedDeviceName) {
        for(BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }

/*    public void onStart(){
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }

    }*/

/*
    public void setup(){
        Button btnSend = findViewById(R.id.sendbtn); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
                bt.send("1"+"P", true);
                try {
                    Thread.sleep(1000);      // 1초씩 딜레이. for문에 넣어놨으니 결과는 1초마다 실행됨.

                } catch (InterruptedException e) {    //sleep에서 생기는 방해오류(InterruptedException)를 처리.
                    e.printStackTrace();
                }

                bt.send(value+"A", true);
            }
        });
        Button btnSend2 = findViewById(R.id.sendbtn2); //데이터 전송
        btnSend2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           //     Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
                bt.send("2"+"P", true);
                bt.send(value2+"A", true);

            }
        });
        Button btnSend3 = findViewById(R.id.sendbtn3); //데이터 전송
        btnSend3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
                bt.send("3"+"P", true);
                bt.send(value3+"A", true);
            }
        });


        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
           //     Toast.makeText(getContext(), "SADF", Toast.LENGTH_SHORT);
             //   Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                Log.i("abab", message);
                Log.i("abab", "ASD");
            }
        });

    }*/


    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}