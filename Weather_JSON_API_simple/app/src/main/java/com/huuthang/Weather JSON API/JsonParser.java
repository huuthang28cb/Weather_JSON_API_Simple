package com.huuthang.weather_json_api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonParser extends AsyncTask<String, Integer,ArrayList> {
    ArrayList<Weather> data;
    Context context;
    TextView toado, nhietdo, apsuat, tocdogio, may;
    ImageView hinh;

    public JsonParser(Context context, TextView toado, TextView nhietdo, TextView apsuat, TextView tocdogio, TextView may, ImageView hinh) {
        this.context = context;
        this.toado = toado;
        this.nhietdo = nhietdo;
        this.apsuat = apsuat;
        this.tocdogio = tocdogio;
        this.may = may;
        this.hinh = hinh;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        data=new ArrayList<>();
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            InputStream is=url.openStream();
            InputStreamReader inputStreamReader=new InputStreamReader(is,"UTF-8");
            //Luồng độc xuống
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();//Dùng cái này là không cần dùng + chuỗi các thứ ă
            String line="";//Đầu tiên sẽ bằng rỗng
            while ((line=bufferedReader.readLine())!=null){//Đọc từng dòng là khi có data mới đọc
                stringBuilder.append(line+ "\n");
            }
            Weather thoitiet=new Weather();

            //Tạo ra cái Object lớn nhất
            JSONObject object=new JSONObject(stringBuilder.toString());
            //Tọa độ
            JSONObject coord=object.getJSONObject("coord");
            String kinhdo=coord.getString("lon");
            String vido=coord.getString("lat");
            String toado=kinhdo+", "+vido;
            thoitiet.setToado(toado);
            //Nhiệt độ
            JSONObject main=object.getJSONObject("main");
            String temp=main.getString("temp");
            String apsuat=main.getString("pressure");
            thoitiet.setNhetdo(temp);
            thoitiet.setApsuat(apsuat);
            //Tốc độ gió
            JSONObject wind=object.getJSONObject("wind");
            String speed=wind.getString("speed");
            thoitiet.setTocdogio(speed);
            //Mây
            JSONObject clouds=object.getJSONObject("clouds");
            String all=clouds.getString("all");
            thoitiet.setMay(all);
            //Hình
            JSONArray weather=object.getJSONArray("weather");
            JSONObject iconObj=weather.getJSONObject(0);
            String icon=iconObj.getString("icon");
            thoitiet.setHinh("https://openweathermap.org/img/w/"+icon+".png");

            //Giờ gán vào
            data.add(thoitiet);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        toado.setText(data.get(0).getToado());
        nhietdo.setText(data.get(0).getNhetdo());
        apsuat.setText(data.get(0).getApsuat());
        tocdogio.setText(data.get(0).getTocdogio());
        may.setText(data.get(0).getMay());
        Picasso.with(context).load(data.get(0).getHinh()).into(hinh);
    }
}
