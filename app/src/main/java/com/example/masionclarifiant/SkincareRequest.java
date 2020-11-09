package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//피부타입 및 집중케어 항목 변경할 수 있는 Request
public class SkincareRequest extends StringRequest {
    final static private String URL="http://3.35.16.162/changed_skincare.php";
    private Map<String,String> map;

    public SkincareRequest(String userID, String skinType, String interest, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("userID", userID);
        map.put("skinType",skinType);
        map.put("interest", interest);

    }

    @Override
    protected Map<String, String> getParams(){
        return map;
    }
}

