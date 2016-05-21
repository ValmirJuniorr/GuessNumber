package br.com.val.guessnumber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class GuessNumber extends AppCompatActivity {

    String nameUser;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        nameUser = intent.getStringExtra(MainActivity.NAME_USER);
        number=generateRandomNumber();
        setContentView(R.layout.activity_guess_number);
    }

    public void tryLuck(View view){
        EditText editText=(EditText) findViewById(R.id.editTextNumberEntered);
        int numberEntered=Integer.parseInt(editText.getText().toString());

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        if(number==numberEntered){
            alertDialog.setTitle("Parabéns!!");
            alertDialog.setMessage("Voce adivinhou o numero correto : "+number);
            alertDialog.setButton("Jogar novamente", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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


}
