package com.example.masionclarifiant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectFrag2 extends Fragment {
    public static SelectFrag2 newInstance(){
        SelectFrag2 fragment2 = new SelectFrag2();
        return fragment2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_fragment2,container,false);
        Bundle bundle = this.getArguments();
        String data = bundle.getString("skinType");
        //Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
        return view;
    }
}
