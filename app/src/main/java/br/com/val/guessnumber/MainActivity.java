package br.com.val.guessnumber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String NAME_USER="nameUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View v) {
        String msg=getString(R.string.inf_name_requerid);
        EditText text=(EditText) findViewById(R.id.editTextName);
        String name=text.getText().toString();
        if(name.equals("")){
            Toast toast = Toast.makeText(this,msg ,Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Intent intent = new Intent(this, GuessNumber.class);
            intent.putExtra(NAME_USER, name);
            startActivity(intent);
        }

    }
}
