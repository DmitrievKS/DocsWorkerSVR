package kirdmt.com.docsworkersvr.Activities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.Models.ModelHistory;
import kirdmt.com.docsworkersvr.Presenters.HistoryPresenter;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.adapters.CardAdapter;
import kirdmt.com.docsworkersvr.contractView.ContractViewHistory;

public class HistoryActivity extends AppCompatActivity implements ContractViewHistory {

    private CardAdapter adapter;
    private RecyclerView historyRecyclerView;
    private LinearLayoutManager layoutManager;
    private Spinner historySpinner;
    private ArrayAdapter spinnerAdapterHistory;
    private TextView versionTextView;
    private EditText authorEditText;
    private Button searchButton;

    ModelHistory model;
    HistoryPresenter presenter;

    List<String> housesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();

    }

    void init() {

        Bundle extras = getIntent().getExtras();

        housesList = new ArrayList<>();
        housesList = extras.getStringArrayList("housesList");

        model = new ModelHistory();
        presenter = new HistoryPresenter(model, this, getApplicationContext());

        versionTextView = (TextView) findViewById(R.id.text_version);
        versionTextView.setText(getString(R.string.app_version_prefix) + " " + getString(R.string.version_name));

        authorEditText = (EditText) findViewById(R.id.name_filter_EditText);

        searchButton = (Button) findViewById(R.id.btn_search);

        historySpinner = (Spinner) findViewById(R.id.spinner_houses);
        spinnerAdapterHistory = new ArrayAdapter<String>(this, R.layout.spinner_item, housesList);
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
    public Context getContext() {
        return null;
    }

    @Override
    public void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        presenter = null;
        model = null;

    }

    public void searchOnClickListener(View target) {

        int i = historySpinner.getSelectedItemPosition();
        String author = authorEditText.getText().toString();

        presenter.getHistoryData(i, author);

    }

}
