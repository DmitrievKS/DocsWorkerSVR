package kirdmt.com.docsworkersvr.ui.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.ui.main.MainActivity;

//TODO возможноо shared preferences стоит реализовать здесь или в отдельном классе.
public class StartActivity extends AppCompatActivity implements StartContract.View {

    private static final String ACTIVITY_TAG = "ActivityTAG";

    private StartPresenter presenter;
    private ArrayList<String> houseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppThemeLauncher);
        super.onCreate(savedInstanceState);

        presenter = new StartPresenter(this);

    }

    @Override
    public void startMainActivity() {

        try {
            presenter.detachView();
            presenter = null;
        } catch (NullPointerException e) {
            Log.d(ACTIVITY_TAG, "NullPointException is : " + e.getMessage());
        } catch (Exception e) {
            Log.d(ACTIVITY_TAG, "Exception is : " + e.getMessage());
        } finally {
            Intent intentMain = new Intent(this, MainActivity.class);
            intentMain.putStringArrayListExtra("housesList", houseList);
            startActivity(intentMain);
            //чтобы потом эта активити не открывалась при нажатии на кнопку "Назад": finish();
            finish();
        }

    }

    @Override
    public Context getContext() {

        return getApplicationContext();
    }

    @Override
    public void setHouseList(ArrayList<String> houseList) {

        this.houseList = houseList;
    }
}
