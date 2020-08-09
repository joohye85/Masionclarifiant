package com.example.masionclarifiant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectFrag1 extends Fragment {
    public static String selectedkind;

    public static TextView tonerText;
    public static TextView lotionText;
    public static RadioButton tonerBtn;
    public static RadioButton lotionBtn;
    public ImageButton deleteAllBtn;

    public static SelectFrag1 newInstance(){
        SelectFrag1 fragment1 = new SelectFrag1();
        return fragment1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_fragment1,container,false);

        selectedkind = "";

        tonerBtn  = (RadioButton) view.findViewById(R.id.toner);
        lotionBtn  = (RadioButton) view.findViewById(R.id.lotion);
        tonerText = (TextView) getActivity().findViewById(R.id.tonerText);
        lotionText = (TextView) getActivity().findViewById(R.id.lotionText);
        deleteAllBtn = (ImageButton) getActivity().findViewById(R.id.deleteAll);

        tonerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                tonerText.setVisibility(View.VISIBLE);
                lotionText.setVisibility(View.GONE);
                selectedkind = "toner";
            }
        });

        lotionBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                lotionText.setVisibility(View.VISIBLE);
                tonerText.setVisibility(View.GONE);
                selectedkind = "lotion";
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                lotionText.setVisibility(View.GONE);
                tonerText.setVisibility(View.GONE);
                selectedkind = "";
            }
        });
        return view;
    }
}
