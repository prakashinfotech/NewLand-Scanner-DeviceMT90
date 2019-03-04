package test.com.newlandpoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public TextView txtbarcodeType, txtbarcode, txtScanUsing;
    public boolean isScanFromButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtbarcodeType = (TextView) findViewById(R.id.barcodeType);
        txtbarcode = (TextView) findViewById(R.id.txtbarcode);
        txtScanUsing = (TextView) findViewById(R.id.txtScanUsing);
        Button btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("nlscan.action.SCANNER_TRIG");
                intent.putExtra("SCAN_TIMEOUT", 4);
                intent.putExtra("SCAN_TYPE ", 1);
                sendBroadcast(intent);
                isScanFromButton = true;
            }
        });


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {

                    String barcode = intent.getStringExtra("SCAN_BARCODE1");
                    int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1);

                    if (barcode != null) {

                        txtbarcode.setText(barcode);
                        txtbarcodeType.setText(""+barcodeType);

                    } else {

                        Toast.makeText(getApplicationContext(), "Barcode Not getting.", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Scan Failed", Toast.LENGTH_LONG).show();
                }
            }
        }, new IntentFilter("nlscan.action.SCANNER_RESULT"));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_F6:
                Intent intent = new Intent("nlscan.action.SCANNER_TRIG");
                intent.putExtra("SCAN_TIMEOUT", 4);
                intent.putExtra("SCAN_TYPE ", 1);
                sendBroadcast(intent);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



