package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//피드백 업데이트 하는 Request
public class FeedbackRequest extends StringRequest {
    final static private String URL = "http://3.34.134.55/feedback.php";
    private Map<String,String> map;

    public FeedbackRequest(String userID,String feedback, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("feedback",feedback);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}
