package br.com.val.guessnumber;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GuessNumberActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    AlertDialog dialogSuccess, dialogFail;
    String nameUser;
    int number, attempts, begin, range;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.nameUser = intent.getStringExtra(MainActivity.NAME_USER);
        initGame();
        setContentView(R.layout.activity_guess_number);
        textView = (TextView) findViewById(R.id.textViewTypeNumber);
        textView.setText(nameUser + getString(R.string.type_a_number) + " de->" + begin + " até->" + (range + begin - 1));
        makeDialogs();
    }

    private void makeDialogs() {
        dialogSuccess = createConfirmDialog("Fantástico!", "Parabéns " + nameUser + " Voce acertou!",
                "Jogar Novamente", "Sair do Jogo", new Action() {
                    @Override
                    public void run() {
                        editText.setText("");
                        initGame();
                        textView.setText(nameUser + getString(R.string.type_a_number) + " de->" + begin + " até->" + (range + begin - 1));
                    }
                }, new Action() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

        dialogFail = createConfirmDialog("Boa tentativa!", "Que tristeza! " + nameUser + "\n Voce quase acertou!",
                "Tentar Novamente", "Sair do Jogo", new Action() {
                    @Override
                    public void run() {
                        int distBegin, distEnd;
                        distBegin = number - begin;
                        distEnd = (range + begin) - number;
                        if (distBegin > distEnd) {
                            begin += ((range) / 2);
                        }
                        range = (range % 2) == 0 ? (range / 2) : (range / 2) + 1;
                        editText.setText("");
                        textView.setText(nameUser + getString(R.string.type_a_number) + " de->" + begin + " até->" + (range + begin - 1));

                    }
                }, new Action() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
    }

    private void initGame() {
        this.attempts = 0;
        this.begin = 1;
        this.range = 100;
        this.number = generateRandomNumber();
    }

    public void tryLuck(View view) {
        this.attempts++;
        editText = (EditText) findViewById(R.id.editTextNumberEntered);
        if (editText.getText().toString().isEmpty()) {
            return;
        }
        int numberEntered = Integer.parseInt(editText.getText().toString());
        if (number == numberEntered) {
            saveRecords();
            dialogSuccess.show();
        } else {
            dialogFail.show();
        }
        //Toast toast = Toast.makeText(this, "number: " + number, Toast.LENGTH_SHORT);
        //toast.show();
    }

    public int generateRandomNumber() {
        Random generate = new Random();
        int number = generate.nextInt(range) + begin;
        return number;
    }

    public Record createRecord() {
        Record record = new Record();
        record.setNameUser(this.nameUser);
        record.setNumber(this.number);
        record.setAttempts(this.attempts);
        record.setDate(new Date());
        return record;
    }

    public void saveRecords() {
        List<Record> records = getRecords();
        if (records == null) {
            records = new ArrayList<>();
        }
        records.add(createRecord());
        orderRecords(records);
        String recordsJson = gson.toJson(records, new TypeToken<List<Record>>() {
        }.getType());
        SharedPreferences preferences = this.getSharedPreferences(RecordActivity.BR_COM_VAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(RecordActivity.BR_COM_VAL_RECORDS, recordsJson);
        editor.commit();
        this.number = generateRandomNumber();
        this.attempts = 0;
    }

    public void orderRecords(List<Record> records) {
        Collections.sort(records);
        while (records.size() > 5) {
            records.remove(records.size() - 1);
        }
    }

    public List<Record> getRecords() {
        SharedPreferences sharedPreferences = getSharedPreferences(RecordActivity.BR_COM_VAL, MODE_PRIVATE);

        String jsonRecords = sharedPreferences.getString(RecordActivity.BR_COM_VAL_RECORDS, "");
        if (jsonRecords.length() < 1) {
            return null;
        } else {
            try {
                List<Record> records = gson.fromJson(jsonRecords, new TypeToken<List<Record>>() {
                }.getType());
                return records;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    private AlertDialog createConfirmDialog(String title, String message, String ok, String cancel,
                                            final Action actionOk, final Action actionCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                actionOk.run();
            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                actionCancel.run();
            }
        });
        return builder.create();
    }


}
