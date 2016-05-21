package br.com.val.guessnumber;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    public static String BR_COM_VAL="br.com.val.guessNumber";
    public static String BR_COM_VAL_RECORDS="records";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        showRecord();
    }

    public void showRecord(){
        SharedPreferences sharedPreferences = getSharedPreferences(this.BR_COM_VAL, MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonRecords=sharedPreferences.getString(this.BR_COM_VAL_RECORDS,"");
        try {
            Collection<Record> records = gson.fromJson(jsonRecords, new TypeToken<Collection<Record>>() {
            }.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(this,jsonRecords,Toast.LENGTH_SHORT);
        toast.show();


    }
}
