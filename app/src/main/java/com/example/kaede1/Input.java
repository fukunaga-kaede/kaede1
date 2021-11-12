package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Input extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // 保存ボタンの取得
        Button inputClick = findViewById(R.id.inputClick);
        // 保存ボタンのリスナクラスのインスタンスを作成
        InputClickListener input_listener = new InputClickListener();
        // 保存ボタンにリスナを設定
        inputClick.setOnClickListener(input_listener);

        //部品の取得
        TextView inputDateText =  findViewById(R.id.inputDate);

        //EditTextにリスナーをつける
        inputDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendarインスタンスを取得
                Calendar date = Calendar.getInstance();

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Input.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                inputDateText.setText(String.format("%d / %02d / %02d", year, month+1, dayOfMonth));
                            }
                        },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                    );

                //dialogを表示
                datePickerDialog.show();
            }



        });
    }

    // 戻るボタンを押した場合の処理
    public void onBackButtonClick(View view) {

        finish();
    }


    // 保存ボタンを押した場合の処理
    private class InputClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {

            // ラジオボタンの内容で金額場合分け
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.flgIncomeExpenditure);

            int checkedId = radioGroup.getCheckedRadioButtonId();

            if (checkedId != -1) {
                // 選択されているラジオボタンの取得
                RadioButton radioButton = (RadioButton) findViewById(checkedId);

                // ラジオボタンのテキストを取得
                String text = radioButton.getText().toString();


                TextView inputDateText = findViewById(R.id.inputDate);
                TextView inputItemText = findViewById(R.id.inputItem);
                TextView inputItemAmount = findViewById(R.id.inputAmount);
                TextView inputItemMemo = findViewById(R.id.inputMemo);

                String inputDate = inputDateText.getText().toString();
                String inputItem = inputItemText.getText().toString();
                String inputAmount = inputItemAmount.getText().toString();
                String inputMemo = inputItemMemo.getText().toString();


                // 金額の符号を設定
                if(text.equals("支出")) {
                    int inputAmountInt = Integer.parseInt(inputAmount);
                    inputAmountInt *= -1;
                    inputAmount = Integer.toString(inputAmountInt);
                }

                // SQL

                Intent intent = new Intent(Input.this, Look.class);
                startActivity(intent);

            } else {
                // ラジオボタンが選択されていない場合の処理
                // トーストを表示
                Toast.makeText(Input.this, R.string.toast_radio, Toast.LENGTH_LONG).show();
            }


        }
    }
}