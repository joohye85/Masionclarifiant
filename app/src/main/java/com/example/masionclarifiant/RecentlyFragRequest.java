package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//가장 많이 팔린 제품 1순위
public class RecentlyFragRequest extends StringRequest {
    final static private String URL="http://3.34.134.55/best_item.php";
    private Map<String,String> map;

    public RecentlyFragRequest(Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams(){
        return map;
    }
}