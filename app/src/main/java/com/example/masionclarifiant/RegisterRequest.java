package com.example.masionclarifiant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//회원가입 Request
public class RegisterRequest extends StringRequest {
    final static private String URL="http://3.35.37.0/register.php";
    private Map<String,String> map;

    public RegisterRequest(String userID, String userPW, String gender, String age, String skinType, String interest, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        map=new HashMap<>();
        map.put("userID",userID);
        map.put("userPW",userPW);
        map.put("gender",gender);
        map.put("age",age);
        map.put("skinType", skinType);
        map.put("interest",interest);
    }

    @Override
    protected Map<String, String> getParams(){
        return map;
    }
}
