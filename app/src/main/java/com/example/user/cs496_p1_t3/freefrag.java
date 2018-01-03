package com.example.user.cs496_p1_t3;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by user on 2017-12-27.
 */

public class freefrag extends Fragment {

    public freefrag(){

    }
    Button button3;
    EditText NUMBER1;
    EditText NUMBER2;
    TextView RESULT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedlnstanceState){

        View view = inflater.inflate(R.layout.freefrag, container, false);

        EditText NUMBER1 =(EditText)view.findViewById(R.id.NUMBER1);
        EditText NUMBER2 =(EditText)view.findViewById(R.id.NUMBER2);
        TextView RESULT = (TextView) view.findViewById(R.id.RESULT);

        return view;
    }
    public  void addclick(View v){

        int n1 = Integer.parseInt(NUMBER1.getText().toString());
        int n2 = Integer.parseInt(NUMBER2.getText().toString());
        RESULT.setText(Integer.toString(n1+n2));
        if(n1==496 & n2==496){
            Intent intent = new Intent(getActivity(), Diary.class);
            startActivity(intent);

        }


    }

    public  void subclick(View v){
        int n1 = Integer.parseInt(NUMBER1.getText().toString());
        int n2 = Integer.parseInt(NUMBER2.getText().toString());
        RESULT.setText(Integer.toString(n1-n2));


    }

    public  void mulclick(View v){
        int n1 = Integer.parseInt(NUMBER1.getText().toString());
        int n2 = Integer.parseInt(NUMBER2.getText().toString());
        RESULT.setText(Integer.toString(n1*n2));


    }

    public  void divclick(View v){
        int n1 = Integer.parseInt(NUMBER1.getText().toString());
        int n2 = Integer.parseInt(NUMBER2.getText().toString());
        RESULT.setText(Integer.toString(n1/n2));


    }


}
