package br.com.val.guessnumber;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GuessNumberActivity extends AppCompatActivity {

    String nameUser;
    int number,attempts,begin,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.nameUser = intent.getStringExtra(MainActivity.NAME_USER);
        this.number=generateRandomNumber();
        this.attempts=0;
        this.begin=1;
        this.end=100;
        setContentView(R.layout.activity_guess_number);
    }

    public void tryLuck(View view){
        this.attempts++;
        EditText editText=(EditText) findViewById(R.id.editTextNumberEntered);
        int numberEntered=Integer.parseInt(editText.getText().toString());

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        if(number==numberEntered){
            alertDialog.setTitle("Parabéns!!");
            alertDialog.setMessage("Voce adivinhou o numero correto : "+number);
            alertDialog.setButton("Jogar novamente", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    saveRecords();
                }
            });

        }else{
            editText.setText("");
            alertDialog.setTitle("Ops!");
            alertDialog.setMessage("Voce quase advinhou "+nameUser+"!!!");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

        }

        alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
        Toast toast = Toast.makeText(this,"number: "+number,Toast.LENGTH_SHORT);
        toast.show();
    }

    public int generateRandomNumber(){
        Random generate=new Random();
        int number=generate.nextInt(100)+1;
        return number;
    }

    public Record createRecord(){
        Record record=new Record();
        record.setNameUser(this.nameUser);
        record.setNumber(this.number);
        record.setAttempts(this.attempts);
        record.setDate(new Date());
        return record;
    }

    public void saveRecords(){
        List<Record>records=getRecords();
        if(records==null){
            records=new ArrayList<>();
        }
        records.add(createRecord());
        orderRecords(records);
        Gson gson=new Gson();
        String recordsJson=gson.toJson(records,new TypeToken<List<Record>>() { }.getType());
        SharedPreferences preferences = this.getSharedPreferences(RecordActivity.BR_COM_VAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(RecordActivity.BR_COM_VAL_RECORDS, recordsJson);
        editor.commit();
        this.number=generateRandomNumber();
        this.attempts=0;
    }

    public void orderRecords(List<Record> records){
        Collections.sort(records);
    }

    public List<Record> getRecords(){
        SharedPreferences sharedPreferences = getSharedPreferences(RecordActivity.BR_COM_VAL, MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonRecords=sharedPreferences.getString(RecordActivity.BR_COM_VAL_RECORDS,"");
        if(jsonRecords.length()>1){
            return null;
        }else{

        try {
            List<Record> records = gson.fromJson(jsonRecords, new TypeToken<List<Record>>() { }.getType());
            return records;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        }

    }



}
