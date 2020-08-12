package com.example.masionclarifiant;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MypageRequest extends StringRequest {
    final static private String URL = "http://wngp0805.dothome.co.kr/feedback.php";
    private Map<String,String> map;

    public MypageRequest(String userID,String feedback, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("feedback",feedback);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
