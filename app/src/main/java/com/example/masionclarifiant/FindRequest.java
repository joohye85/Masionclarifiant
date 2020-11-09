package com.example.masionclarifiant;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//비밀번호 찾기 Request
public class FindRequest extends StringRequest {
    final static private String URL = "http://3.35.16.162/find_pw.php";
    private Map<String,String> map;

    public FindRequest(String userID, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송
        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
