package com.example.user.cs496_p1_t3;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReadContact extends Fragment {

    public ReadContact() {  }
//예전
    ListView listView5 = null;
    ImageButton contacts5 = null;
    TextView txtFriends;

    //새로운
    CallbackManager callbackManager;
    TextView txtFriendsf;
    ProgressDialog mDialog;
    JSONArray jsonFacebook;
    JSONArray jsonContact;
    JSONArray jsonFacebook3;

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    //요기까지 새로운것
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.readcontact, container, false);
//새로운 코드
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getActivity());
        callbackManager =CallbackManager.Factory.create();
        txtFriendsf= (TextView)view.findViewById(R.id.txtFriends2);


        LoginButton loginButton = (LoginButton)view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()

        {

            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(getActivity());
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                String accestoken = loginResult.getAccessToken().getToken();

                GraphRequest request = new GraphRequest( loginResult.getAccessToken(),
                        "/me/taggable_friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                mDialog.dismiss();
                                Log.d("response", response.toString());
                                JSONObject object = response.getJSONObject();
                                getData(object);
                            }
                        });
                Bundle paramaters = new Bundle();
                paramaters.putString("fields","name,id");
                request.setParameters(paramaters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error){

            }

        });




// 원래 코드



        contacts5 = (ImageButton) view.findViewById(R.id.contacts5);
        listView5 = (ListView) view.findViewById(R.id.result);
        txtFriends = (TextView) view.findViewById(R.id.txtFriends);

        contacts5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //to do
                ContentResolver cr = getActivity().getContentResolver(); //다 가져와00
                Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);


                JSONArray school2 = new JSONArray();
                while (cursor.moveToNext()) {


                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    JSONObject school = new JSONObject();

                    try {

                        school.put("name:",name);
                        school.put("number:",number);
                        school2.put(school);
                        //txtFriends.setText(school2.toString()); //보여주기

                    }


                    catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
                jsonContact = school2;
                int length = jsonFacebook.length();
                for(int i =0; i<length; i++){
                    try {
                        jsonFacebook.put(jsonContact.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    txtFriendsf.setText(jsonFacebook.toString());
                    Log.d("tag","kill me");
                }



        } });
        return view;


    }
    //새로운 코드 제이슨 마지막

    public void getData(JSONObject object) {
        JSONArray jsonArrayFriends;

        try {

            jsonArrayFriends = object.getJSONArray("data");
            int length = jsonArrayFriends.length();
            for(int i =0; i<length; i++){
                jsonArrayFriends.getJSONObject(i).put("number","facebook");
                //txtFriendsf.setText(jsonArrayFriends.toString());
            }
            jsonFacebook = jsonArrayFriends;



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}








