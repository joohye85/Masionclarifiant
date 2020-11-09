package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//피드백을 전송하기 위해 최근 조합한 데이터 1개를 보내주는 Request
public class RecentlyCombinationRequest extends StringRequest {
    final static private String URL = "http://3.35.16.162/recently_data.php";
    private Map<String,String> map;

    public RecentlyCombinationRequest(String userID, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송
        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
