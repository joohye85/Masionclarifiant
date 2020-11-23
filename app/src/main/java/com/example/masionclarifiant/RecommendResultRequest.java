package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RecommendResultRequest extends StringRequest {
    final static private String URL = "http://3.34.134.55/auto_skin.php";
    private Map<String, String> map;

    public RecommendResultRequest(String userID, String i_Name, String Water, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);//위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("userID", userID);
        map.put("i_name", i_Name);
        map.put("water", Water);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
