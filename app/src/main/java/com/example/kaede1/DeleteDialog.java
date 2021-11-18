package com.example.kaede1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DeleteDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        TextView msgView = new TextView(getActivity());
        // ダイアログビルダーを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // ダイアログのタイトルを設定
        builder.setTitle(R.string.dialog_title);
        // ダイアログのメッセージを設定
        builder.setMessage(R.string.dialog_msg);
        // Positive Buttonを設定。
        builder.setPositiveButton(R.string.positive_button, new DialogButtonClickListener());
        // Negative Buttonを設定。
        builder.setNegativeButton(R.string.negative_button, new DialogButtonClickListener());
        // ダイアログオブジェクトを生成し、リターン
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    // DELETE文実行
                    Fix fix = new Fix();
                    fix.delete();

                    // 画面遷移
                    Intent intent = new Intent(getActivity(), Look.class);
                    startActivity(intent);

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
