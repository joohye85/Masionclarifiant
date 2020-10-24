package com.example.masionclarifiant;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SkinResultRequest extends StringRequest {
    final static private String URL = "http://3.35.37.0/get_skin_result.php";
    private Map<String, String> map;

    public SkinResultRequest(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);//위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}

