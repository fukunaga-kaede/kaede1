package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class Fix extends AppCompatActivity {

    int newYear;
    int newMonth;
    int newDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);

        Intent intent = getIntent();

        String fixDate = intent.getStringExtra("fixDate");
        String fixItem = intent.getStringExtra("fixItem");
        int fixAmount = intent.getIntExtra("fixAmount",0);
        String fixMemo = intent.getStringExtra("fixMemo");


        TextView fixDateText = findViewById(R.id.fixDate);
        TextView fixItemText = findViewById(R.id.fixItem);
        TextView fixAmountText = findViewById(R.id.fixAmount);
        TextView fixMemoText = findViewById(R.id.fixMemo);
        // ラジオグループのオブジェクトを取得
        RadioGroup rg = findViewById(R.id.flgIncomeExpenditure);

        if(fixAmount >= 0) {
            // 収入（金額が正の数の場合の処理）
            rg.check(R.id.flgIncome);

        } else {
            // 支出（金額が負の数の場合の処理）
            rg.check(R.id.flgExpenditure);
        }

        // テキストをxmlファイルにセット
        fixDateText.setText(fixDate);
        fixItemText.setText(fixItem);
        fixAmountText.setText(Integer.toString(fixAmount));
        fixMemoText.setText(fixMemo);

        // DBの日時の分割（初期値用）
        String[] strDate = fixDate.split("/");
        newYear = Integer.parseInt(strDate[0]);
        int Month = Integer.parseInt(strDate[1]);
        newMonth = Month - 1;
        newDay = Integer.parseInt(strDate[2]);

        //EditTextにリスナーをつける
        fixDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Fix.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                fixDateText.setText(String.format("%d / %02d / %02d", year, month+1, dayOfMonth));

                                // 初期選択日付を選択日付に更新
                                newYear = year;
                                newMonth = month;
                                newDay = dayOfMonth;
                            }
                        },
                        // 初期値セット
                        newYear,newMonth,newDay
                );

                //dialogを表示
                datePickerDialog.show();
            }

        });


        // 修正ボタンの取得
        Button fixClick = findViewById(R.id.fixClick);
        // 修正ボタンのリスナクラスのインスタンスを作成
        FixClickListenerListener fix_listener = new FixClickListenerListener();
        // 修正ボタンにリスナを設定
        fixClick.setOnClickListener(fix_listener);

        // 削除ボタンの取得
        Button deleteClick = findViewById(R.id.deleteClick);
        // 削除ボタンのリスナクラスのインスタンスを作成
        DeleteClickListener delete_listener = new DeleteClickListener();
        // 削除ボタンにリスナを設定
        deleteClick.setOnClickListener(delete_listener);

        // 背景がタップされるとキーボードを閉じる
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Whatever
                InputMethodManager inputMethodMgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }

    // 戻るボタンを押した場合の処理
    public void onBackButtonClick(View view) {

        finish();
    }

    // 修正ボタンを押した場合の処理
    private class FixClickListenerListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {

            // 金額の入力内容をString型で取得
            TextView fixAmountText = findViewById(R.id.fixAmount);
            String fixAmountString = fixAmountText.getText().toString();

            if (fixAmountString.equals("")){
                // 金額が入力されていない場合の処理
                // トーストを表示
                Toast.makeText(Fix.this, R.string.toast_amount, Toast.LENGTH_LONG).show();
            } else {
                // 選択されているラジオボタンの取得
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.flgIncomeExpenditure);

                int checkedId = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton) findViewById(checkedId);

                // ラジオボタンのテキストを取得
                String text = radioButton.getText().toString();

                // 入力内容を取得
                TextView fixDateText = findViewById(R.id.fixDate);
                TextView fixItemText = findViewById(R.id.fixItem);
                TextView fixMemoText = findViewById(R.id.fixMemo);

                String fixDateString = fixDateText.getText().toString();
                String fixItem = fixItemText.getText().toString();
                int fixAmount = Integer.parseInt(fixAmountString);
                String fixMemo = fixMemoText.getText().toString();

                try {
                    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy / MM / dd");
                    Date inputDate = sdFormat.parse(fixDateString);

                    // 金額の符号を設定
                    if (text.equals("支出")) {
                        fixAmount *= -1;
                    }

                    // SQL

                    Intent intent = new Intent(Fix.this, Look.class);
                    startActivity(intent);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    // 削除ボタンを押した場合の処理
    private class DeleteClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            // ダイアログを開く
            DeleteDialog dialogFragment = new DeleteDialog();
            dialogFragment.show(getSupportFragmentManager(),"DeleteDialog");
        }
    }

    // DB削除
    public void delete() {
        //SQL文

    }




}