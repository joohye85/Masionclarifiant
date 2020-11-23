package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Recently_three_skinageRequest extends StringRequest {
    final static private String URL = "http://3.34.134.55/recently_three_skinage.php";
    private Map<String, String> map;

    public Recently_three_skinageRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);//위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
