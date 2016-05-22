package br.com.val.guessnumber;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ListAdapter adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                getRecords());
        listView=(ListView) findViewById(R.id.listViewRecord);
        listView.setAdapter(adapter);
    }

    public String[] getRecords(){
        String recordsArray[];
        SharedPreferences sharedPreferences = getSharedPreferences(this.BR_COM_VAL, MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonRecords=sharedPreferences.getString(this.BR_COM_VAL_RECORDS,"");
        try {
            List<Record> records = gson.fromJson(jsonRecords, new TypeToken<List<Record>>() {
            }.getType());
            recordsArray=new String[records.size()];
            Record record;
            for (int i=0; i<records.size();i++) {
                record=records.get(i);
                recordsArray[i]=record.getNameUser()+" : "+record.getAttempts()+" tentativas"+
                        " Numero: " +record.getNumber();
            }
            return recordsArray;
        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this,"Exceção lançada!",Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

    }
}
