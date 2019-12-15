package kirdmt.com.docsworkersvr.ui.main;

//TODO Улучшение дизайна.(мелочи, выпадающий список, цвет элементов EditText. Плохо видно на слонце.)
//TODO ТЗ Володи Высокого. (возможно стоит ознакомиться, какие идеи есть?)
//TODO Перенос приложения на iOS.
//TODO Улучшеить исполнения паттерна МВП. (0 особено для main)
//TODO остается проблема связанная с плохим соединением с интернетом.
//TODO: Realize push notifications.

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.ui.add.AddItemActivity;
import kirdmt.com.docsworkersvr.ui.history.HistoryActivity;
import kirdmt.com.docsworkersvr.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainContract.View {

    static final String FIREBASE_AUTH_TAG = "fireBaseAuthTag";
    private FirebaseAuth mAuth;

    private FloatingActionButton fab;
    private Button buttonAddItem, buttonLogin, buttonRegistration;
    private TextView emailText, passwordText, helloText;
    private EditText emailEdit, passwordEdit;
    private View divider1, divider2, divider3;
    private ProgressDialog progressDialog;

    private static List<String> housesList;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        housesList = new ArrayList<>();
        housesList = extras.getStringArrayList("housesList");

        presenter = new MainPresenter(this);

        mAuth = FirebaseAuth.getInstance();

        emailText = (TextView) findViewById(R.id.email_textView);
        passwordText = (TextView) findViewById(R.id.password_TextView);
        helloText = (TextView) findViewById(R.id.hello_textView);

        buttonAddItem = (Button) findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);

        buttonLogin = (Button) findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(this);

        buttonRegistration = (Button) findViewById(R.id.btn_registration);
        buttonRegistration.setOnClickListener(this);

        emailEdit = (EditText) findViewById(R.id.email_EditText);
        passwordEdit = (EditText) findViewById(R.id.password_EditText);

        divider1 = findViewById(R.id.divider_1);
        divider2 = findViewById(R.id.divider_2);
        divider3 = findViewById(R.id.divider_3);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {


        if (v == buttonAddItem) {

            presenter.detachView();

            Intent addItemIntent = new Intent(getApplicationContext(), AddItemActivity.class);
            addItemIntent.putStringArrayListExtra("housesList", (ArrayList<String>) housesList);
            startActivity(addItemIntent);

        } else if (v == fab) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.alert_title))
                    .setMessage(getString(R.string.alert_main_message))
                    // .setIcon(R.drawable.ic_android_cat)
                    .setCancelable(true)
                    .setNegativeButton(getString(R.string.alert_write_mail),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  dialog.cancel();

                                    //send email with extra data
                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                    emailIntent.setData(Uri.parse(getString(R.string.mailto)));
                                    startActivity(emailIntent);

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

        } else if (v == buttonLogin) {

            String email = emailEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            //check fields filling
            if (email.length() > 0 & password.length() > 0) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(FIREBASE_AUTH_TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(FIREBASE_AUTH_TAG, "signInWithEmail:failure", task.getException());
                                    showToast(R.string.no_access);
                                    updateUI(null);
                                }

                            }
                        });
            } else {

                showToast(R.string.fill_fields);

            }
        } else if (v == buttonRegistration) {
//open email intent with extra data.
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(getString(R.string.mailto)))
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.registration_subject))
                    .putExtra(Intent.EXTRA_TEXT, getString(R.string.registration_request));
            startActivity(emailIntent);

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
        if (id == R.id.action_information) {
            actionInformation();
        } else if (id == R.id.action_logout) {
            mAuth.signOut();
            updateUI(null);
        }
        return super.onOptionsItemSelected(item);
    }


    private void actionInformation() {
        presenter.detachView();
        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        historyIntent.putStringArrayListExtra("housesList", (ArrayList<String>) housesList);
        startActivity(historyIntent);
    }


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            helloText.setVisibility(View.GONE);
            emailText.setVisibility(View.GONE);
            passwordText.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.GONE);
            buttonRegistration.setVisibility(View.GONE);
            emailEdit.setVisibility(View.GONE);
            passwordEdit.setVisibility(View.GONE);
            divider1.setVisibility(View.GONE);
            divider2.setVisibility(View.GONE);
            divider3.setVisibility(View.GONE);

            buttonAddItem.setVisibility(View.VISIBLE);

        } else {

            buttonAddItem.setVisibility(View.GONE);

            helloText.setVisibility(View.VISIBLE);
            emailText.setVisibility(View.VISIBLE);
            passwordText.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.VISIBLE);
            buttonRegistration.setVisibility(View.VISIBLE);
            emailEdit.setVisibility(View.VISIBLE);
            passwordEdit.setVisibility(View.VISIBLE);
            divider1.setVisibility(View.VISIBLE);
            divider2.setVisibility(View.VISIBLE);
            divider3.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(MainActivity.this, getString(stringId),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

}