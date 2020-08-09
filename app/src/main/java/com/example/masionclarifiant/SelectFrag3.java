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

public class SelectFrag3 extends Fragment {
    public static String selectedscent;

    public static TextView tonerText;
    public static TextView lotionText;

    public static TextView aloeText;
    public static TextView greenteaText;
    public static TextView byeoungpulText;
    public static TextView honeyText;
    public static TextView snailText;
    public static TextView oliveText;

    public static TextView teatreeText;
    public static TextView rosemaryText;
    public static TextView nothingText;

    public static RadioButton teatreeBtn;
    public static RadioButton rosemaryBtn;
    public static RadioButton nothingBtn;

    public ImageButton deleteAllBtn;

    public static SelectFrag3 newInstance(){
        SelectFrag3 fragment3 = new SelectFrag3();
        return fragment3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_fragment3, container, false);

        tonerText = (TextView) getActivity().findViewById(R.id.tonerText);
        lotionText = (TextView) getActivity().findViewById(R.id.lotionText);

        aloeText = (TextView) getActivity().findViewById(R.id.aloeText);
        greenteaText = (TextView) getActivity().findViewById(R.id.greenteaText);
        byeoungpulText = (TextView) getActivity().findViewById(R.id.byeongpulText);
        honeyText = (TextView) getActivity().findViewById(R.id.honeyText);
        snailText = (TextView) getActivity().findViewById(R.id.snailText);
        oliveText = (TextView) getActivity().findViewById(R.id.oliveText);

        teatreeText = (TextView) getActivity().findViewById(R.id.teatreeText);
        rosemaryText = (TextView) getActivity().findViewById(R.id.rosemaryText);
        nothingText = (TextView) getActivity().findViewById(R.id.nothingText);

        teatreeBtn = (RadioButton) view.findViewById(R.id.teatree);
        rosemaryBtn = (RadioButton) view.findViewById(R.id.rosemary);
        nothingBtn = (RadioButton) view.findViewById(R.id.nothing);

        deleteAllBtn = (ImageButton) getActivity().findViewById(R.id.deleteAll);

        teatreeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                teatreeText.setVisibility(View.VISIBLE);
                rosemaryText.setVisibility(View.GONE);
                nothingText.setVisibility(View.GONE);
                selectedscent = "teatree";
            }
        });

        rosemaryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rosemaryText.setVisibility(View.VISIBLE);
                teatreeText.setVisibility(View.GONE);
                nothingText.setVisibility(View.GONE);
                selectedscent = "rosemary";
            }
        });

        nothingBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nothingText.setVisibility(View.VISIBLE);
                teatreeText.setVisibility(View.GONE);
                rosemaryText.setVisibility(View.GONE);
                selectedscent = "nothing";
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                lotionText.setVisibility(View.GONE);
                tonerText.setVisibility(View.GONE);

                aloeText.setVisibility(View.GONE);
                greenteaText.setVisibility(View.GONE);
                byeoungpulText.setVisibility(View.GONE);
                honeyText.setVisibility(View.GONE);
                snailText.setVisibility(View.GONE);
                oliveText.setVisibility(View.GONE);

                teatreeText.setVisibility(View.GONE);
                rosemaryText.setVisibility(View.GONE);

                selectedscent = "";
                SelectFrag1.selectedkind = "";
            }
        });

        return view;
    }
}
