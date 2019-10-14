package kirdmt.com.docsworkersvr.Activities;


//TODO использовать build.config как минимум для хранения и подьягивания из него версии.
//TODO Улучшение дизайна.
//TODO Авторизация? ТЗ Володи Высокого. Перенос приложения на iOS.
//TODO Add another houses to project.(Vyshnii Volochek first).


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import kirdmt.com.docsworkersvr.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button buttonAddItem;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonAddItem = (Button) findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == buttonAddItem) {

            Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
            startActivity(intent);

        } else if (v == fab) {


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("О приложении:")
                    .setMessage("Приложение написано для практики разработки для Android. Приложение полностью бесплатное и свободно для распространения. По всем вопросам, пожеланиям, идеям добавлений в новые версии, ошибкам - обращаться по электронной почте LLirikNN@gmail.com.")
                    // .setIcon(R.drawable.ic_android_cat)
                    .setCancelable(true)
                    .setNegativeButton("Написать письмо",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  dialog.cancel();

                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                    emailIntent.setData(Uri.parse("mailto:LLirikNN@gmail.com"));
                                    startActivity(emailIntent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information) {

            actionInformation();
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void actionInformation() {

        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(historyIntent);
    }


}