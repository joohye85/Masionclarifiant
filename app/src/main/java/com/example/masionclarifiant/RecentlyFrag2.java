package com.example.masionclarifiant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RecentlyFrag2 extends Fragment {
    TextView text;
    TextView count_sell;
    public static RecentlyFrag2 newInstance(){
        RecentlyFrag2 fragment2 = new RecentlyFrag2();
        return fragment2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.recently_purchased_fragment2,container,false);
        text = (TextView) view.findViewById(R.id.textView13);
        count_sell = (TextView) view.findViewById(R.id.count);
        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    String i_name = jsonObject.getString("i_name");
                    String skinType = jsonObject.getString("skinType");
                    String count = jsonObject.getString("count");
                    if(success){
                        text.setText("제형 :" + skinType +"\n재료 :" + i_name);
                        count_sell.setText(count+"명이 이 제품을 선택했습니다!");
                    }

                }catch (Exception e){e.printStackTrace();}
            }
        };
        RecentlyFrag2Request recentlyFrag2Request = new RecentlyFrag2Request(res);
        RequestQueue queue = Volley.newRequestQueue(RecentlyFrag2.super.getContext());
        queue.add(recentlyFrag2Request);

        return view;
    }
}
