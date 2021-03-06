package com.example.kaede1;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Look extends AppCompatActivity {

    static int month_count;
    static int displayMonth;
    static int displayYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.currently);
        setContentView(R.layout.activity_look);

        ListView lvMenu = findViewById(R.id.look_list);
        List<Map<String,Object>> menuList = new ArrayList<>();
        Map<String,Object> menu = new HashMap<>();

        // intentを受け取る
        Intent intent = getIntent();
        month_count = intent.getIntExtra("month_count",0);
        int displayReturnMonth = intent.getIntExtra("displayMonth",-1);
        int displayReturnYear = intent.getIntExtra("displayYear",-1);

        // 現在の年月を取得
        Calendar date = Calendar.getInstance();
        int nowMonth = date.get(Calendar.MONTH)+1;
        int nowYear = date.get(Calendar.YEAR);



        // 追加、編集画面から戻ってきた場合
        if(displayReturnMonth != -1) {
            displayMonth = displayReturnMonth;
            displayYear = displayReturnYear;
        } else {
            // 表示したい月
            displayMonth = nowMonth+month_count;
            int year_count = 0;
            if(displayMonth > 12) {
                while(displayMonth > 12){
                    displayMonth -= 12;
                    year_count++;
                }
            } else if (displayMonth < 1){
                while(displayMonth < 1){
                    displayMonth += 12;
                    year_count--;
                }
            }
            displayYear = nowYear+year_count;
        }


        TextView year_month = findViewById(R.id.year_month);
        year_month.setText(displayYear + "年" + displayMonth + "月");

        // ListView の背景設定
        ImageView imageView = findViewById(R.id.flower);
        if(displayMonth == 1) {
            imageView.setBackgroundResource(R.drawable.jan);
        } else if (displayMonth == 2) {
            imageView.setBackgroundResource(R.drawable.feb);
        } else if (displayMonth == 3) {
            imageView.setBackgroundResource(R.drawable.mar);
        } else if (displayMonth == 4) {
            imageView.setBackgroundResource(R.drawable.apr);
        } else if (displayMonth == 5) {
            imageView.setBackgroundResource(R.drawable.may);
        } else if (displayMonth == 6) {
            imageView.setBackgroundResource(R.drawable.jun);
        } else if (displayMonth == 7) {
            imageView.setBackgroundResource(R.drawable.jul);
        } else if (displayMonth == 8) {
            imageView.setBackgroundResource(R.drawable.aug);
        } else if (displayMonth == 9) {
            imageView.setBackgroundResource(R.drawable.sep);
        } else if (displayMonth == 10) {
            imageView.setBackgroundResource(R.drawable.out);
        } else if (displayMonth == 11) {
            imageView.setBackgroundResource(R.drawable.nov);
        } else if (displayMonth == 12) {
            imageView.setBackgroundResource(R.drawable.dec);
        }



        // 合計、収入、支出の変数定義、初期値設定
        int total = 0;
        int income = 0;
        int expenditure = 0;

        //DB接続準備
        DatabaseHelper helper = new DatabaseHelper(Look.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        //月を二桁表示
        Format f = new DecimalFormat("00");

        //年、月ごとにDB検索(合計)
        String totalSql = "SELECT TOTAL(amount) FROM tsuyu6 " +
                "WHERE date >= '" + displayYear + " / " + f.format(displayMonth) + " / 01'" +
                "AND date <= '" + displayYear + " / " + f.format(displayMonth)  + " / 31'" +
                "ORDER BY date";

        Cursor cur = db.rawQuery(totalSql,null);
        cur.moveToFirst();
        total = cur.getInt(0);

        //年、月ごとにDB検索(収入)
        String incomeSql = "SELECT TOTAL(amount) FROM tsuyu6 " +
                "WHERE amount > 0 " +
                "AND date >= '" + displayYear + " / " + f.format(displayMonth) + " / 01'" +
                "AND date <= '" + displayYear + " / " + f.format(displayMonth)  + " / 31'";

        cur = db.rawQuery(incomeSql,null);
        cur.moveToFirst();
        income = cur.getInt(0);

        //年、月ごとにDB検索(支出)
        String expenditureSql = "SELECT TOTAL(amount) FROM tsuyu6 " +
                "WHERE amount < 0 " +
                "AND date >= '" + displayYear + " / " + f.format(displayMonth) + " / 01'" +
                "AND date <= '" + displayYear + " / " + f.format(displayMonth)  + " / 31'";

        cur = db.rawQuery(expenditureSql,null);
        cur.moveToFirst();
        expenditure = cur.getInt(0);

        // 合計、収入、支出のTextView取得
        TextView totalText = findViewById(R.id.look_total);
        TextView incomeText = findViewById(R.id.look_income);
        TextView expenditureText = findViewById(R.id.look_expenditure);

        // 合計、収入、支出の内容をTextViewにセット
        totalText.setText(String.format("%,d", total));
        incomeText.setText(String.format("%,d", income));
        expenditureText.setText(String.format("%,d", expenditure));


        //DB操作(SELECT)
        if(helper == null){
            helper = new DatabaseHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }
        try {

            String sql = "SELECT * FROM tsuyu6";
            cur = db.rawQuery(sql,null);

            //DBの_idをリストに渡す
            String[] from = {"_id","date","item","memo","amount"};
            int[] to = {R.id.display_id, R.id.display_date, R.id.display_item, R.id.display_memo, R.id.display_amount};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(Look.this, R.layout.row, cur, from, to,0);
            lvMenu.setAdapter(adapter);

            //1行ずつDBからリストへ
            long Count = DatabaseUtils.queryNumEntries(db,"tsuyu6", null,null);
            for (int i = 0; i <= Count; i++){

                String selectSql = "SELECT * FROM tsuyu6 " +
                        "WHERE date >= '" + displayYear + " / " + f.format(displayMonth) + " / 01'" +
                        "AND date <= '" + displayYear + " / " + f.format(displayMonth)  + " / 31'" +
                        "ORDER BY date " +
                        "LIMIT " + i + "," + 1;
                cur = db.rawQuery(selectSql, null);
                String _id = "";
                String sdate = "";
                String item = "";
                String amount = "";
                String memo = "";

                while(cur.moveToNext()){
                    //DBの列番号(index)を取得
                    int idxId = cur.getColumnIndex("_id");
                    int idxDate = cur.getColumnIndex("date");
                    int idxItem = cur.getColumnIndex("item");
                    int idxAmount = cur.getColumnIndex("amount");
                    int idxMemo = cur.getColumnIndex("memo");

                    //列番号(index)にあるデータを取得
                    _id = cur.getString(idxId);
                    sdate = cur.getString(idxDate);
                    item = cur.getString(idxItem);
                    amount = cur.getString(idxAmount);
                    memo = cur.getString(idxMemo);

                    if(memo.equals("")) {

                    } else {
                        memo = "(" + memo + ")";
                    }


                    //取得したデータをリストのmapに入れる
                    menu = new HashMap<>();
                    menu.put("_id", _id);
                    menu.put("date", sdate);
                    menu.put("item", item);
                    menu.put("amount",amount);
                    menu.put("memo", memo);
                    menuList.add(menu);

                    cur.moveToFirst();
                }
            }


        }finally {
            db.close();
        }

        String[] from = {"_id","date","item","memo","amount"};
        int[] to = {R.id.display_id, R.id.display_date, R.id.display_item, R.id.display_memo, R.id.display_amount};
        SimpleAdapter adapter = new SimpleAdapter(Look.this,menuList,R.layout.row,from,to);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new ListItemClickListener());

        // 追加ボタンの取得
        Button addClick = findViewById(R.id.addClick);
        // 追加ボタンのリスナクラスのインスタンスを作成
        AddClickListener add_listener = new AddClickListener();
        // 追加ボタンにリスナを設定
        addClick.setOnClickListener(add_listener);

        // 来月ボタンの取得
        Button nextClick = findViewById(R.id.next_month);
        // 追加ボタンのリスナクラスのインスタンスを作成
        nextClickListener next_listener = new nextClickListener();
        // 追加ボタンにリスナを設定
        nextClick.setOnClickListener(next_listener);

        // 先月ボタンの取得
        Button lastClick = findViewById(R.id.last_month);
        // 追加ボタンのリスナクラスのインスタンスを作成
        lastClickListener last_listener = new lastClickListener();
        // 追加ボタンにリスナを設定
        lastClick.setOnClickListener(last_listener);

        // simulation radio buttonの取得
        RadioButton simulationClick = findViewById(R.id.flgSimulation);
        // simulation radio buttonのリスナクラスのインスタンスを作成
        SimulationClickListener simulation_Listener = new SimulationClickListener();
        // simulation radio buttonにリスナを設定
        simulationClick.setOnClickListener(simulation_Listener);

    }

    //　リストがクリックされた時の処理
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> item = (Map<String, Object>)parent.getItemAtPosition(position);

            String _id = item.get("_id").toString();
            String fixDate = item.get("date").toString();
            String fixItem = item.get("item").toString();
            String fixMemo = item.get("memo").toString();
            String fixAmount = item.get("amount").toString();

            // fix画面に送るデータの格納
            Intent intent = new Intent(Look.this, Fix.class);

            intent.putExtra("listId",_id);
            intent.putExtra("fixDate", fixDate);
            intent.putExtra("fixItem", fixItem);
            intent.putExtra("fixMemo", fixMemo);
            intent.putExtra("fixAmount", fixAmount);

            intent.putExtra("displayMonth", displayMonth);
            intent.putExtra("displayYear", displayYear);

            startActivity(intent);
            finish();
        }
    }

    // 追加ボタンを押した場合の処理
    private class AddClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {

            // DBの更新処理

            // 入力画面に遷移
            Intent intent = new Intent(Look.this, Input.class);
            intent.putExtra("month", displayMonth);
            intent.putExtra("displayYear", displayYear);
            startActivity(intent);
            finish();
        }
    }

    // nextボタンを押した場合の処理
    private class nextClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            month_count += 1;

            Intent intent = new Intent(Look.this, Look.class);
            intent.putExtra("month_count", month_count);
            startActivity(intent);
            finish();
        }
    }

    // lastボタンを押した場合の処理
    private class lastClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            month_count -= 1;

            Intent intent = new Intent(Look.this, Look.class);
            intent.putExtra("month_count", month_count);
            startActivity(intent);
            finish();
        }
    }

    // simulationボタンを押した場合の処理
    private class SimulationClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {

            Intent intent = new Intent(Look.this, FuLook.class);
            startActivity(intent);
            finish();

        }
    }
}