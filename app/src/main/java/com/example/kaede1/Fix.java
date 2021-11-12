package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class Fix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);

        Intent intent = getIntent();

        String fixDate = intent.getStringExtra("fixDate");
        String fixItem = intent.getStringExtra("fixItem");
        String fixAmount = intent.getStringExtra("fixAmount");
        String fixMemo = intent.getStringExtra("fixMemo");


        Calendar calendar = Calendar.getInstance();

        TextView fixDateText = findViewById(R.id.fixDate);
        TextView fixItemText = findViewById(R.id.fixItem);
        TextView fixAmountText = findViewById(R.id.fixAmount);
        TextView fixMemoText = findViewById(R.id.fixMemo);
        // ラジオグループのオブジェクトを取得
        RadioGroup rg = findViewById(R.id.flgIncomeExpenditure);

        // 金額をint型に変更
        int fixAmountInt = Integer.parseInt(fixAmount);

        if(fixAmountInt >= 0) {
            // 収入（金額が正の数の場合の処理）
            rg.check(R.id.flgIncome);

        } else {
            // 支出（金額が負の数の場合の処理）
            rg.check(R.id.flgExpenditure);
        }


        // テキストをxmlファイルにセット
        fixDateText.setText(fixDate);
        fixItemText.setText(fixItem);
        fixAmountText.setText(fixAmount);
        fixMemoText.setText(fixMemo);


        //EditTextにリスナーをつける
        fixDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 今日の日付取得
                //Calendarインスタンスを取得
                /*Calendar date = Calendar.getInstance();
                        date.get(Calendar.YEAR);
                        date.get(Calendar.MONTH);
                        date.get(Calendar.DATE);
                        int a = date.get(Calendar.DAY_OF_WEEK);*/


                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Fix.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                fixDateText.setText(String.format("%d / %02d / %02d", year, month+1, dayOfMonth));
                            }
                        },
                        // 初期値セット

                        2021,9,12
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



    }

    // 戻るボタンを押した場合の処理
    public void onBackButtonClick(View view) {

        finish();
    }

    // 修正ボタンを押した場合の処理
    private class FixClickListenerListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            // ラジオボタンの内容で金額場合分け
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.flgIncomeExpenditure);

            int checkedId = radioGroup.getCheckedRadioButtonId();

            // 選択されているラジオボタンの取得
            RadioButton radioButton = (RadioButton) findViewById(checkedId);

            // ラジオボタンのテキストを取得
            String text = radioButton.getText().toString();

            // 入力内容を取得
            TextView fixDateText = findViewById(R.id.fixDate);
            TextView fixItemText = findViewById(R.id.fixItem);
            TextView fixItemAmount = findViewById(R.id.fixAmount);
            TextView fixItemMemo = findViewById(R.id.fixMemo);

            String fixDate = fixDateText.getText().toString();
            String fixItem = fixItemText.getText().toString();
            String fixAmount = fixItemAmount.getText().toString();
            String fixMemo = fixItemMemo.getText().toString();


            // 金額の符号を設定
            if(fixAmount.equals("支出")) {
                int fixAmountInt = Integer.parseInt(fixAmount);
                fixAmountInt *= -1;
                fixAmount = Integer.toString(fixAmountInt);
            }

            // SQL

            Intent intent = new Intent(Fix.this, Look.class);
            startActivity(intent);
        }
    }

    // 削除ボタンを押した場合の処理
    private class DeleteClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            // DBの更新処理

            // finish();

            Intent intent = new Intent(Fix.this, Look.class);
            startActivity(intent);
        }
    }
}