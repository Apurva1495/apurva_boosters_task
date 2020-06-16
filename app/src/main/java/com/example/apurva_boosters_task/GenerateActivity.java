package com.example.apurva_boosters_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText field;
    TextView btnGenerate;
    ImageView qrProfile,back;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        setTitle("Generate Qr Code");

        field = findViewById(R.id.field);
        btnGenerate = findViewById(R.id.btnGenerate);
        qrProfile = findViewById(R.id.qrProfile);
        back = findViewById(R.id.back);

        btnGenerate.setOnClickListener(this);
        back.setOnClickListener(this);


        if(getIntent().getExtras() != null){
            btnGenerate.setVisibility(View.GONE);


            String code = String.valueOf(getIntent().getExtras().get("code"));

            field.setText(code);
            GenerateCode(code);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGenerate:
                content = field.getText().toString();

                GenerateCode(content);
                break;
            case R.id.back:
                Intent intent = new Intent(GenerateActivity.this,MainActivity.class);
                startActivity(intent);
                break;


        }
    }

    private void GenerateCode(String content) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
            qrProfile.setImageBitmap(bitmap);
            qrProfile.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}
