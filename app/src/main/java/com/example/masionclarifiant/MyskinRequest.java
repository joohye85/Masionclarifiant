package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//피부타입 및 집중케어 항목 확인할 수 있는 Request
public class MyskinRequest extends StringRequest {
    final static private String URL="http://wngp0805.dothome.co.kr/get_skincare.php";
    private Map<String,String> map;

    public MyskinRequest(String userID, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams(){
        return map;
    }
}

