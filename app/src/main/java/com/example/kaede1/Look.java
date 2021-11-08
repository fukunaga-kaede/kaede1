package com.example.kaede1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Look extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        ListView lvMenu = findViewById(R.id.look_list);
        List<Map<String, String>> menuList = new ArrayList<>();

        Map<String,String> menu = new HashMap<>();
        menu.put("item","ごはん");
        menu.put("memo","マック");
        menu.put("amount","1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("item","服");
        menu.put("memo","UNIQLO");
        menu.put("amount","10000円");
        menuList.add(menu);


        String[] from = {"item","memo","amount"};
        int[] to = {R.id.display_item, R.id.display_memo,R.id.display_amount};
        SimpleAdapter adapter = new SimpleAdapter(Look.this,menuList,R.layout.row,from,to);
        lvMenu.setAdapter(adapter);
    }
}