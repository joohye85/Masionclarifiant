package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DiagnoseEyeCheck extends AppCompatActivity {
    Button goSkinCam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_eye_check);
        goSkinCam = (Button)findViewById(R.id.go_skin_cam);
        final String userID = getIntent().getStringExtra("userID");
        System.out.println("eyecheck 들어옴");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");
        goSkinCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketService socketService = MainActivity.socketService;
                socketService.connect();
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
                SendEyeCamRequest sendEyeCamRequest = new SendEyeCamRequest(userID, res);
                RequestQueue queue = Volley.newRequestQueue(DiagnoseEyeCheck.this);
                queue.add(sendEyeCamRequest);
                Intent intent = new Intent(DiagnoseEyeCheck.this, DiagnoseSkinCam.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
