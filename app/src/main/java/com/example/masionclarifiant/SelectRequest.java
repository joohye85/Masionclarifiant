package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SelectRequest extends StringRequest {
    final static private String URL="http://wngp0805.dothome.co.kr/selected_skin.php";
    private Map<String,String> map;

    public SelectRequest(String i_name, String perfume, String skinType, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("i_name",i_name);
        map.put("perfume",perfume);
        map.put("skinType",skinType);
    }

    @Override
    protected Map<String, String> getParams(){
        return map;
    }
}
