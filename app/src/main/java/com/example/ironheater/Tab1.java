package com.example.ironheater;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    SeekBar mSeekBar;
    TextView mTxtValue;
    String value;
    SeekBar mSeekBar2;
    TextView mTxtValue2;
    String value2;
    SeekBar mSeekBar3;
    TextView mTxtValue3;
    String value3;
    public static BluetoothSPP bt;

    public static String rec_data;

  //  public static TextView tempmsg;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt = new BluetoothSPP(getContext()); //Initializing

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Log.i("ga", message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        mSeekBar = (SeekBar) view.findViewById(R.id.temperature);
        mTxtValue = (TextView) view.findViewById(R.id.temper);
        mSeekBar2 = (SeekBar) view.findViewById(R.id.temperature2);
        mTxtValue2 = (TextView) view.findViewById(R.id.temper2);
        mSeekBar3 = (SeekBar) view.findViewById(R.id.temperature3);
        mTxtValue3 = (TextView) view.findViewById(R.id.temper3);

        mSeekBar.setProgress(30);
        value = String.valueOf(mSeekBar.getProgress());
        mTxtValue.setText(value);

        mSeekBar2.setProgress(30);
        value2 = String.valueOf(mSeekBar2.getProgress());
        mTxtValue2.setText(value2);

        mSeekBar3.setProgress(30);
        value3 = String.valueOf(mSeekBar3.getProgress());
        mTxtValue3.setText(value3);

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Log.i("ga", message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = String.valueOf(progress);
                mTxtValue.setText(value);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "온도조절 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "온도조절 끝났습니다.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "온도조절 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "온도조절 끝났습니다.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "온도조절 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "온도조절 끝났습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);  //수신한 데이터
            }
        });


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getContext(), "블루투스 연결 성공", Toast.LENGTH_SHORT);
            }

            @Override
            public void onDeviceDisconnected() {
                Toast.makeText(getContext(), "블루투스 연결 해제", Toast.LENGTH_SHORT);
            }

            @Override
            public void onDeviceConnectionFailed() {
                Toast.makeText(getContext(), "블루투스 연결 실패", Toast.LENGTH_SHORT);
            }
        });
bt.
//rec_data = "SDFAF";
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);  //수신한 데이터
                Log.i("a", "SDFSFF");
                rec_data = message;
                rec_data = "!";
            }
        });



        Button connectbutton = view.findViewById(R.id.connectbtn);
        connectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();    //블루투스 연결되어 있으면 끊고
                } else {
                    Intent intent = new Intent(getContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);  //끊겨있으면 연결해준다.
                }
            }
        });

        return view;
    }

    public void onDestroy(){
        super.onDestroy();
        bt.stopService();   //블루투스 끊음.
    }

    public void onStart(){
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

    }


    public void setup(){
        Button btnSend = getActivity().findViewById(R.id.sendbtn); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
                bt.send("1", true);
                bt.send(value, true);
            }
        });
        Button btnSend2 = getActivity().findViewById(R.id.sendbtn2); //데이터 전송
        btnSend2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
                bt.send("2", true);
                bt.send(value2, true);

            }
        });
        Button btnSend3 = getActivity().findViewById(R.id.sendbtn3); //데이터 전송
        btnSend3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT);
                bt.send("3", true);
                bt.send(value3, true);
            }
        });


        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(getContext(), "SADF", Toast.LENGTH_SHORT);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                Log.i("abab", message);
                Log.i("abab", "ASD");
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
