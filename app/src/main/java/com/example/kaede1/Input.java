package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


            // DBの更新処理

            finish();
        }
    }
}