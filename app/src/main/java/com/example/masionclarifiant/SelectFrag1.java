package com.example.masionclarifiant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectFrag1 extends Fragment {

    public static SelectFrag1 newInstance(){
        SelectFrag1 fragment1 = new SelectFrag1();
        return fragment1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String skinType = "hi";
        Bundle bundle = new Bundle();
        SelectFrag2 fragment2 = new SelectFrag2();
        bundle.putString("skinType", skinType);
        fragment2.setArguments(bundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.select_fragment1,container,false);
        //RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        // RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        //String skinType = rb.getText().toString();
        return view;
    }
}
