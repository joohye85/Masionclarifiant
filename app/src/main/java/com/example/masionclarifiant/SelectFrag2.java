package com.example.masionclarifiant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectFrag2 extends Fragment {
    public static String selectedingredients[] = new String[]{"x","x","x","x","x","x"};

    public static TextView tonerText;
    public static TextView lotionText;

    public static TextView aloeText;
    public static TextView greenteaText;
    public static TextView byeoungpulText;
    public static TextView honeyText;
    public static TextView snailText;
    public static TextView oliveText;

    public static CheckBox aloebox;
    public static CheckBox greenteabox;
    public static CheckBox byeongpulbox;
    public static CheckBox honeybox;
    public static CheckBox snailbox;
    public static CheckBox olivebox;

    public ImageButton deleteAllBtn;

    public static int totalCount = 0;

    public static SelectFrag2 newInstance(){
        SelectFrag2 fragment2 = new SelectFrag2();
        return fragment2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_fragment2,container,false);

        //SelectFrag1에 있는 토너/로션 텍스트
        tonerText = (TextView) getActivity().findViewById(R.id.tonerText);
        lotionText = (TextView) getActivity().findViewById(R.id.lotionText);

        //체크박스
        aloebox = (CheckBox) view.findViewById(R.id.aloe);
        greenteabox = (CheckBox) view.findViewById(R.id.greentea);
        byeongpulbox = (CheckBox) view.findViewById(R.id.byeongpul);
        honeybox = (CheckBox) view.findViewById(R.id.honey);
        snailbox = (CheckBox) view.findViewById(R.id.snail);
        olivebox = (CheckBox) view.findViewById(R.id.olive);

        //체크박스 선택하면 하단에 나타나는 텍스트뷰
        aloeText = (TextView) getActivity().findViewById(R.id.aloeText);
        greenteaText = (TextView) getActivity().findViewById(R.id.greenteaText);
        byeoungpulText = (TextView) getActivity().findViewById(R.id.byeongpulText);
        honeyText = (TextView) getActivity().findViewById(R.id.honeyText);
        snailText = (TextView) getActivity().findViewById(R.id.snailText);
        oliveText = (TextView) getActivity().findViewById(R.id.oliveText);

        deleteAllBtn = (ImageButton) getActivity().findViewById(R.id.deleteAll);

        aloebox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount>=3) {Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                aloebox.setChecked(false);}
                if(aloebox.isChecked()){
                    aloeText.setVisibility(View.VISIBLE);
                    selectedingredients[0] = "aloe";
                    totalCount++;
                }
                else{
                    aloebox.setChecked(false);
                    aloeText.setVisibility(View.GONE);
                    selectedingredients[0] = "x";
                    totalCount--;
                }
            }
        });

        greenteabox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount>=3) {Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                greenteabox.setChecked(false);}
                if(greenteabox.isChecked()){
                    greenteaText.setVisibility(View.VISIBLE);
                    selectedingredients[1] = "greentea";
                    totalCount++;
                }
                else{
                    greenteabox.setChecked(false);
                    greenteaText.setVisibility(View.GONE);
                    selectedingredients[1] = "x";
                    totalCount--;
                }
            }
        });

        byeongpulbox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount>=3) {Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                byeongpulbox.setChecked(false);}
                if(byeongpulbox.isChecked()){
                    byeoungpulText.setVisibility(View.VISIBLE);
                    selectedingredients[2] = "byeongpul";
                    totalCount++;
                }
                else{
                    byeongpulbox.setChecked(false);
                    byeoungpulText.setVisibility(View.GONE);
                    selectedingredients[2] = "x";
                    totalCount--;
                }
            }
        });

        honeybox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount>=3) {Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                honeybox.setChecked(false);}
                if(honeybox.isChecked()){
                    honeyText.setVisibility(View.VISIBLE);
                    selectedingredients[3] = "honey";
                    totalCount++;
                }
                else{
                    honeybox.setChecked(false);
                    honeyText.setVisibility(View.GONE);
                    selectedingredients[3] = "x";
                    totalCount--;
                }
            }
        });

        snailbox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount>=3) {Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                snailbox.setChecked(false);}
                if(snailbox.isChecked()){
                    snailText.setVisibility(View.VISIBLE);
                    selectedingredients[4] = "snail";
                    totalCount++;
                }
                else{
                    snailbox.setChecked(false);
                    snailText.setVisibility(View.GONE);
                    selectedingredients[4] = "x";
                    totalCount--;
                }
            }
        });

        olivebox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(olivebox.isChecked()){
                    if(totalCount>=3){ Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                    olivebox.setChecked(false);}
                    selectedingredients[5] = "olive";
                    oliveText.setVisibility(View.VISIBLE);
                    totalCount++;
                }
                else{
                    selectedingredients[5] = "x";
                    olivebox.setChecked(false);
                    oliveText.setVisibility(View.GONE);
                    totalCount--;
                }
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //selectedingredients = new String[]{"x", "x", "x", "x", "x", "x"};



                lotionText.setVisibility(View.GONE);
                tonerText.setVisibility(View.GONE);

                aloeText.setVisibility(View.GONE);
                greenteaText.setVisibility(View.GONE);
                byeoungpulText.setVisibility(View.GONE);
                honeyText.setVisibility(View.GONE);
                snailText.setVisibility(View.GONE);
                oliveText.setVisibility(View.GONE);

                aloebox.setChecked(false);
                greenteabox.setChecked(false);
                byeongpulbox.setChecked(false);
                honeybox.setChecked(false);
                snailbox.setChecked(false);
                olivebox.setChecked(false);

                SelectFrag1.selectedkind = "";
                totalCount = 0;
            }
        });
        return view;
    }
}
