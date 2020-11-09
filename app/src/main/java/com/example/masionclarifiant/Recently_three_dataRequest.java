package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//테이블에 최근 조합 데이터 3개 불러오는 Request
public class Recently_three_dataRequest extends StringRequest {
    final static private String URL = "http://3.35.16.162/r_t_d.php";
    private Map<String, String> map;

    public Recently_three_dataRequest(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);//위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}

