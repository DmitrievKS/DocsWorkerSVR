package kirdmt.com.docsworkersvr.ui.history;

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
import kirdmt.com.docsworkersvr.R;

public class HistoryActivity extends AppCompatActivity implements HistoryContract.View {

    private HistoryCardAdapter adapter;
    private RecyclerView historyRecyclerView;
    private LinearLayoutManager layoutManager;
    private Spinner historySpinner;
    private ArrayAdapter spinnerAdapterHistory;
    private TextView versionTextView;
    private EditText authorEditText;
    private Button searchButton;

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

        presenter = new HistoryPresenter(this, getApplicationContext());

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

        adapter = new HistoryCardAdapter(list, getApplicationContext());
        historyRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showToast(int messageResId) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        presenter = null;

    }

    public void searchOnClickListener(View target) {

        int housePosition = historySpinner.getSelectedItemPosition();
        String houseName = historySpinner.getSelectedItem().toString();
        String author = authorEditText.getText().toString();

        presenter.getHistoryData(housePosition, houseName, author);

    }

}
