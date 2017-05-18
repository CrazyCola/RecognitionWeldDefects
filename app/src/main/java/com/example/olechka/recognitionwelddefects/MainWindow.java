package com.example.olechka.recognitionwelddefects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainWindow extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        Log.d(".........................","Main onCreate");
//        Получаем ссылку на кнопку
        Button startNewActivityButton = (Button) findViewById(R.id.button_main_screen);
//        С помощью метода кнопки setOnClickListener() добавляем слушателя
//        Слушатель - анонимный класс
        startNewActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//    Метод выполняется при нажатии на кнопку
//    Создать обьект класса Intent
                Intent intent_1 = new Intent(MainWindow.this, MenuActivity.class);
//    Запуск нового Activity
                startActivity(intent_1);

                MainWindow.this.finish();
            }
        });
//        Button startAutorization = (Button) findViewById(R.id.button_autorization);
//        startAutorization.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent intent_1 = new Intent(MainWindow.this, Authorization.class);
//                startActivity(intent_1);
//
//            }
//        });
    }
}


//    public void onNewButtunClick (View view){
//        TextView button_main_screen =(TextView)findViewById(R.id.text_main_screen);
//        button_main_screen.setText("Картинка \n" +
//                "загрузка изображения \n" +
//                "выход \n");
//    }

