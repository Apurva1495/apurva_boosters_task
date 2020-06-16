package com.example.apurva_boosters_task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView scanBtn, genBtn;
    TextView txt_qrCode;
    String abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
            }

        }

        scanBtn = findViewById(R.id.scanBtn);
        genBtn = findViewById(R.id.genBtn);
        txt_qrCode = findViewById(R.id.txt_qrCode);

        scanBtn.setOnClickListener(this);
        genBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scanBtn:
                ScanCode();
                break;

            case R.id.genBtn:
                Intent intent = new Intent(MainActivity.this, GenerateActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }
    }

    private void ScanCode() {
        Toast.makeText(this, "code", Toast.LENGTH_SHORT).show();
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult == null) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            String r = intentResult.getContents();
            ShowBox(r);
        }
    }

    private void ShowBox(String r) {
        txt_qrCode.setText(r);
        AlertDialog.Builder b1 = new AlertDialog.Builder(this);
        b1.setTitle("Scanned Result");
        b1.setMessage(r);
        EditText input = new EditText(this);
        input.setText(r);
        b1.setView(input);


        b1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                abc = input.getText().toString();
                if(abc.isEmpty()){
                    abc=r;
                }

                Intent intent = new Intent(MainActivity.this, GenerateActivity.class);
                intent.putExtra("code", abc);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        b1.create().show();


    }
}
