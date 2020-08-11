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

    int totalCount = 0;

    public static SelectFrag2 newInstance(){
        SelectFrag2 fragment2 = new SelectFrag2();
        return fragment2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_fragment2,container,false);

        tonerText = (TextView) getActivity().findViewById(R.id.tonerText);
        lotionText = (TextView) getActivity().findViewById(R.id.lotionText);

        aloebox = (CheckBox) view.findViewById(R.id.aloe);
        greenteabox = (CheckBox) view.findViewById(R.id.greentea);
        byeongpulbox = (CheckBox) view.findViewById(R.id.byeongpul);
        honeybox = (CheckBox) view.findViewById(R.id.honey);
        snailbox = (CheckBox) view.findViewById(R.id.snail);
        olivebox = (CheckBox) view.findViewById(R.id.olive);

        aloeText = (TextView) getActivity().findViewById(R.id.aloeText);
        greenteaText = (TextView) getActivity().findViewById(R.id.greenteaText);
        byeoungpulText = (TextView) getActivity().findViewById(R.id.byeongpulText);
        honeyText = (TextView) getActivity().findViewById(R.id.honeyText);
        snailText = (TextView) getActivity().findViewById(R.id.snailText);
        oliveText = (TextView) getActivity().findViewById(R.id.oliveText);

        for(int i=0; i>=5; i++){
            selectedingredients[i] = "";
            System.out.printf("ssssssssssssssssssssss" + selectedingredients[i]);
        }

        deleteAllBtn = (ImageButton) getActivity().findViewById(R.id.deleteAll);
        aloebox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount==3) Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                if(aloebox.isChecked()){
                    aloeText.setVisibility(View.VISIBLE);
                    selectedingredients[0] = "aloe";
                    totalCount++;
                }
                else{
                    aloeText.setVisibility(View.GONE);
                    selectedingredients[0] = "x";
                }
            }
        });

        greenteabox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount==3) Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                if(greenteabox.isChecked()){
                    greenteaText.setVisibility(View.VISIBLE);
                    selectedingredients[1] = "greentea";
                    totalCount++;
                }
                else{
                    greenteaText.setVisibility(View.GONE);
                    selectedingredients[1] = "x";
                }
            }
        });

        byeongpulbox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount==3) Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                if(byeongpulbox.isChecked()){
                    byeoungpulText.setVisibility(View.VISIBLE);
                    selectedingredients[2] = "byeongpul";
                    totalCount++;
                }
                else{
                    byeoungpulText.setVisibility(View.GONE);
                    selectedingredients[2] = "x";
                }
            }
        });

        honeybox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount==3) Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                if(honeybox.isChecked()){
                    honeyText.setVisibility(View.VISIBLE);
                    selectedingredients[3] = "honey";
                    totalCount++;
                }
                else{
                    honeyText.setVisibility(View.GONE);
                    selectedingredients[3] = "x";
                }
            }
        });

        snailbox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(totalCount==3) Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                if(snailbox.isChecked()){
                    snailText.setVisibility(View.VISIBLE);
                    selectedingredients[4] = "snail";
                    totalCount++;
                }
                else{
                    snailText.setVisibility(View.GONE);
                    selectedingredients[4] = "x";
                }
            }
        });

        olivebox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(olivebox.isChecked()){
                    if(totalCount==3) Toast.makeText(getContext(), "성분은 3개 이내로 담아주세요", Toast.LENGTH_SHORT).show();
                    selectedingredients[5] = "olive";
                    oliveText.setVisibility(View.VISIBLE);
                    totalCount++;
                }
                else{
                    selectedingredients[5] = "x";
                    oliveText.setVisibility(View.GONE);
                }
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

                aloebox.setChecked(false);
                greenteabox.setChecked(false);
                byeongpulbox.setChecked(false);
                honeybox.setChecked(false);
                snailbox.setChecked(false);
                olivebox.setChecked(false);

                SelectFrag1.selectedkind = "";
            }
        });
        return view;
    }
}
