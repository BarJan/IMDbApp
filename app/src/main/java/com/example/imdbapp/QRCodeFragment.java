package com.example.imdbapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QRCodeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    MainActivity activity;
    View view;
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    Context context;
    boolean scan;

    public QRCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_q_r_code, container, false);
        getContext();
        activity = (MainActivity) getActivity();
        context = inflater.getContext();
        cameraPreview = (SurfaceView) view.findViewById(R.id.cameraPreview);
        txtResult = (TextView) view.findViewById(R.id.txtResult);
        scan = true;

        barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(context, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},1001);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                if ((qrcodes.size() != 0)&&scan) {//Create vibrate
                    scan = false;
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);

                    JSONObject jsonObject;
                    try {
                        //parsing the new data to a MovObj object
                        jsonObject = new JSONObject(qrcodes.valueAt(0).displayValue);
                        System.out.println(qrcodes.valueAt(0).displayValue);
                        int size = jsonObject.get("genre").toString().length();
                        String[] str = jsonObject.get("genre").toString().substring(2, (size - 2)).split("\", \"");
                        List<String> list_genr = new ArrayList<String>();
                        for (String st : str) {
                            list_genr.add(st.replace("\"", ""));
                        }
                        MovObj movObj = new MovObj(jsonObject.get("title").toString(), jsonObject.get("image").toString(),
                                Double.valueOf(jsonObject.get("rating").toString()),
                                Integer.valueOf(jsonObject.get("releaseYear").toString()), list_genr);
                        SQLiteDB db = new SQLiteDB(context);
                        //trying to add the new MovObj to the database
                        if (db.addData(movObj)) { //if the movie is new in the database
                            activity.changeFragment("first_yes");
                        } else { //if the movie already exists
                            activity.changeFragment("first_no");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return view;
    }

}
