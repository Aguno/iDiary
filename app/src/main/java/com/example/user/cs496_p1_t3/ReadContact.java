package com.example.user.cs496_p1_t3;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class ReadContact extends Fragment {

    public ReadContact(){

    }
    //change!!
    TextView listView = null;
    ImageButton contacts = null;
    ImageButton get = null;
    ListView contactsview = null;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    ArrayAdapter<String> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.readcontact, container, false);



        contacts = (ImageButton)view.findViewById(R.id.contacts);
        //change!!
        listView = (TextView) view.findViewById(R.id.result);
        get = (ImageButton)view.findViewById(R.id.get);
        contactsview = (ListView) view.findViewById(R.id.listview);


        //inserting button here

        contacts.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
    //change!!
/*
                //to do
                ContentResolver cr = getActivity().getContentResolver();
                Cursor cursor = cr.query(
                        ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

                int ididx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int nameidx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                StringBuilder result = new StringBuilder();
                while(cursor.moveToNext())
                {
                    result.append(cursor.getString(nameidx) + " :");

                    String id = cursor.getString(ididx);
                    Cursor cursor2 = cr.query(ContactsContract.CommonDataKinds.
                                    Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",
                            new String[]{id},null);

                    int typeidx = cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TYPE);

                    int numidx = cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER);

                    while (cursor2.moveToNext()){
                        String num = cursor2.getString(numidx);
                        switch(cursor2.getInt(typeidx)){
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                result.append(" Mobile:"+num);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                result.append(" Home:"+num);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                result.append(" Work:"+num);
                                break;
                        }
                    }
                    cursor2.close();
                    result.append("\n");

                }
                cursor.close();

                String str= result.toString();
                ArrayList<String> arr_list = new ArrayList<String>();

                String[] str1=str.split("\n");
                for(int i=0;i<str1.length;i++){

                    arr_list.add(str1[i]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,  arr_list);
                // Assign adapter to ListView
                adapter.sort(new Comparator<String>(){

                    @Override
                    public int compare(String arg1,String arg0){
                        return arg1.compareTo(arg0);
                    }
                });
                //change!!
                //listView.setAdapter(adapter);
*/

                //change!! start here
                new JSONTask().execute("http://13.124.100.34:2000/post");

            }

        });//ends here

        get.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                new JSONTask2().execute("http://13.124.100.34:2000/contacts");
            }
        });


        return view;
    }

    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                JSONObject tempcontact = new JSONObject();
                tempcontact.put("name", "아나 집가고 싶다");
                tempcontact.put("number", "123-4567-1234");

                JSONObject tempcontact2 = new JSONObject();
                JSONArray temparray = new JSONArray();


                temparray.put(tempcontact);
                tempcontact2.put("name", "헤헤히히");
                tempcontact2.put("number", "333-33333-3333");
                temparray.put(tempcontact2);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();

            //        int length = temparray.length();

       //             for(int i=0;i<length;i++) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                        writer.write(temparray.toString());
                        writer.flush();
                    writer.close();//버퍼를 받아줌


                        //서버로 부터 데이터를 받음
                        InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();

                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }


                    return buffer.toString();

                    //서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            listView.setText(result);
        }
    }

    public class JSONTask2 extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id", "androidTest");
                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{

                JSONArray ja = new JSONArray(result);
                HashMap<String,String> item;

                for(int i =0;i<ja.length();i++) {
                    JSONObject order = ja.getJSONObject(i);
                    item = new HashMap<String,String>();
                    item.put("name", order.getString("name"));
                    item.put("number", order.getString("number"));
                    list.add(item);
                    Log.d("tag","here alot");
                }
                Log.d("tag","check here");
                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),list,android.R.layout.simple_list_item_2,
                        new String[] {"name","number"},
                        new int[]{android.R.id.text1,android.R.id.text2});
                contactsview.setAdapter(simpleAdapter);

            }
            catch(JSONException e) {
                return;
                }
        }


    }

}



