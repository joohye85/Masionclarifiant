package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Mypage2Request extends StringRequest {
    final static private String URL = "http://wngp0805.dothome.co.kr/recently_three_data.php";
    private Map<String, String> map;

    public Mypage2Request(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);//위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
