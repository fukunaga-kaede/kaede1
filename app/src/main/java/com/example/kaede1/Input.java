package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

                // 入力内容を取得
                TextView inputDateText = findViewById(R.id.inputDate);
                TextView inputItemText = findViewById(R.id.inputItem);
                TextView inputItemAmount = findViewById(R.id.inputAmount);
                TextView inputItemMemo = findViewById(R.id.inputMemo);

                String inputDate = (String)inputDateText.getText();
                String inputItem = (String)inputItemText.getText();
                String inputAmount = (String)inputItemAmount.getText();
                String inputMemo = (String)inputItemMemo.getText();

                // 金額の符号を設定
                // if(inputAmount.)

                // SQL


            } else {
                // 何も選択されていない場合の処理
                // トーストを作る
            }


            // DBの更新処理

            // finish();
        }
    }
}