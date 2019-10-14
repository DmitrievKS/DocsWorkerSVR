package kirdmt.com.docsworkersvr.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.Models.ModelHistory;
import kirdmt.com.docsworkersvr.Presenters.HistoryPresenter;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.adapters.CardAdapter;
import kirdmt.com.docsworkersvr.contractView.ContractViewHistory;

public class HistoryActivity extends AppCompatActivity implements ContractViewHistory, AdapterView.OnItemSelectedListener {

    static final String APP_VERSION = "1.4";

    private CardAdapter adapter;
    private RecyclerView historyRecyclerView;
    private LinearLayoutManager layoutManager;
    private Spinner historySpinner;
    private ArrayAdapter spinnerAdapterHistory;
    private TextView versionTextView;

    ModelHistory model;
    HistoryPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();

    }

    void init() {

        model = new ModelHistory();
        presenter = new HistoryPresenter(model, this, getApplicationContext());

        versionTextView = (TextView) findViewById(R.id.text_version);
        versionTextView.setText(getString(R.string.app_version_prefix) + " " + APP_VERSION);

        historySpinner = (Spinner) findViewById(R.id.spinner_houses);
        historySpinner.setOnItemSelectedListener(this);
        spinnerAdapterHistory = ArrayAdapter.createFromResource(this,
                R.array.array_houses, R.layout.spinner_item);
        spinnerAdapterHistory.setDropDownViewResource(R.layout.spinner_dropdown_item);
        historySpinner.setAdapter(spinnerAdapterHistory);

        historyRecyclerView = (RecyclerView) findViewById(R.id.card_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        historyRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void closeHistoryActivity() {
        finish();
    }

    @Override
    public void fillRecycler(List<HistoryData> list) {

        adapter = new CardAdapter(list, getApplicationContext());
        historyRecyclerView.setAdapter(adapter);

    }

    @Override
    public void createHousesSpinner(List<String> list) {

/*
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner_houses);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);*/

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // On selecting a spinner item
        // String item = adapterView.getItemAtPosition(i).toString();

        presenter.getHistoryData(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        presenter = null;
        model = null;

    }


}
