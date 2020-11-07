package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//선택한 재료 저장하는 Request
public class SelectRequest extends StringRequest {
    final static private String URL="http://3.35.37.0/selected_skin.php";
    private Map<String,String> map;

    public SelectRequest(String userID, String skinType, String i_name, String perfume, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("userID", userID);
        map.put("i_name",i_name);
        map.put("perfume",perfume);
        map.put("skinType",skinType);

    }

    @Override
    protected Map<String, String> getParams(){
        return map;
    }
}
