package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Input extends AppCompatActivity {

    int newYear;
    int newMonth;
    int newDay;

    // private View mFocusView;

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

        // 現在日時の取得
        Calendar date = Calendar.getInstance();
        newYear = date.get(Calendar.YEAR);
        newMonth = date.get(Calendar.MONTH);
        newDay = date.get(Calendar.DATE);
        inputDateText.setText(String.format("%d / %02d / %02d", newYear, newMonth+1, newDay));

        //EditTextにリスナーをつける
        inputDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Input.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                inputDateText.setText(String.format("%d / %02d / %02d", year, month+1, dayOfMonth));
                                newYear = year;
                                newMonth = month;
                                newDay = dayOfMonth;
                            }
                        },
                        newYear,newMonth,newDay
                );

                //dialogを表示
                datePickerDialog.show();
            }

        });



        /*// 日付入力欄がタップされるとキーボードが閉じる
        View mFocusView = findViewById(R.id.inputDate);
        mFocusView.requestFocus();

        //項目のフォーカスが外れるとキーボードが閉じる
        EditText inputItem = (EditText)findViewById(R.id.inputItem);
        inputItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    // フォーカスが外れた場合キーボードを非表示にする
                    InputMethodManager inputMethodMgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }
        });

        //金額のフォーカスが外れるとキーボードが閉じる
        EditText inputAmount = (EditText)findViewById(R.id.inputAmount);
        inputAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    // フォーカスが外れた場合キーボードを非表示にする
                    InputMethodManager inputMethodMgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        //メモのフォーカスが外れるとキーボードが閉じる
        EditText inputMemo = (EditText)findViewById(R.id.inputMemo);
        inputMemo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    // フォーカスが外れた場合キーボードを非表示にする
                    InputMethodManager inputMethodMgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });*/
    }

    // 戻るボタンを押した場合の処理
    public void onBackButtonClick(View view) {

        finish();
    }


    // 保存ボタンを押した場合の処理
    private class InputClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {

            // 金額の入力内容をString型で取得
            TextView inputAmountText = findViewById(R.id.inputAmount);
            String inputAmountString = inputAmountText.getText().toString();

            // ラジオボタンの内容で金額場合分け
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.flgIncomeExpenditure);

            int checkedId = radioGroup.getCheckedRadioButtonId();

            if (checkedId == -1) {
                // ラジオボタンが選択されていない場合の処理
                // トーストを表示
                Toast.makeText(Input.this, R.string.toast_radio, Toast.LENGTH_LONG).show();
            } else if (inputAmountString.equals("")){
                // 金額が入力されていない場合の処理
                // トーストを表示
                Toast.makeText(Input.this, R.string.toast_amount, Toast.LENGTH_LONG).show();
            } else {

                // 入力された内容を取得
                TextView inputDateText = findViewById(R.id.inputDate);
                TextView inputItemText = findViewById(R.id.inputItem);
                TextView inputMemoText = findViewById(R.id.inputMemo);


                String inputDateString = inputDateText.getText().toString();
                String inputItem = inputItemText.getText().toString();
                String inputMemo = inputMemoText.getText().toString();

                try {
                    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy / MM / dd");
                    Date inputDate = sdFormat.parse(inputDateString);

                    // 選択されているラジオボタンの取得
                    RadioButton radioButton = (RadioButton) findViewById(checkedId);

                    // ラジオボタンのテキストを取得
                    String text = radioButton.getText().toString();

                    // 金額をint型に変換
                    int inputAmount = Integer.parseInt(inputAmountString);


                    // 金額の符号を設定
                    if(text.equals("支出")) {
                        inputAmount *= -1;
                    }

                    // SQL

                    Intent intent = new Intent(Input.this, Look.class);
                    startActivity(intent);

                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }


        }
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        mFocusView.requestFocus();
        return super.onTouchEvent(event);
    }*/
}