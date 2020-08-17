package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//Login Request
public class LoginRequest extends StringRequest {
    final static private String URL = "http://wngp0805.dothome.co.kr/login.php";
    private Map<String,String>map;

    public LoginRequest(String userID, String userPW, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("userPW",userPW);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
