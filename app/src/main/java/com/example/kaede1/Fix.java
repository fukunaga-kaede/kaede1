package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
        fixAmountText.setText(fixAmount + "円");
        fixMemoText.setText(fixMemo);

    }

    public void onBackButtonClick(View view) {
        finish();
    }

}