package com.huuthang.weather_json_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

//API keys: 5951d26a72e5eba05735d0f297609917
//ID city: 1581130
//https://api.openweathermap.org/data/2.5/weather?id=1581130&appid=5951d26a72e5eba05735d0f297609917
//Formater Json: https://jsonformatter.curiousconcept.com/
public class MainActivity extends AppCompatActivity{
    TextView toado, nhietdo, apsuat, tocdogio, may;
    ImageView hinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    public void init(){
        toado=(TextView) findViewById(R.id.toado);
        nhietdo=(TextView) findViewById(R.id.nhietdo);
        apsuat=(TextView) findViewById(R.id.apsuat);
        tocdogio=(TextView) findViewById(R.id.tocdogio);
        may=(TextView) findViewById(R.id.may);
        hinh=(ImageView) findViewById(R.id.img);

        JsonParser parser=new JsonParser(MainActivity.this,toado,nhietdo,apsuat,tocdogio,may,hinh);
        parser.execute("https://api.openweathermap.org/data/2.5/weather?id=1581130&appid=5951d26a72e5eba05735d0f297609917");
    }

}