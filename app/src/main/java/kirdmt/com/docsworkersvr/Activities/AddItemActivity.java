package kirdmt.com.docsworkersvr.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.ExcelData;
import kirdmt.com.docsworkersvr.Presenters.Presenter;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.contractView.ContractView;

//TODO: Перенести кнопку отправки на верх?

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener, ContractView, AdapterView.OnItemSelectedListener {

    SharedPreferences myPrefs;

    private ProgressDialog progressDialog = null;

    Presenter presenter;
    ExcelData excelData = new ExcelData();

    private EditText editTextName, editTextRoomNumber, editTextNeed, editTextResponsible, editTextNotes;
    private Button buttonAddItem;
    private Spinner spinnerStages;
    private Spinner spinnerCategories;
    private Spinner spinnerHouses;

    private ArrayAdapter spinnerAdapterCategory;
    private ArrayAdapter spinnerAdapterStage;
    private ArrayAdapter spinnerAdapterHouses;

    private List<String> housesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        init();
    }

    private void init() {

        Bundle extras = getIntent().getExtras();

        housesList = new ArrayList<>();
        housesList = extras.getStringArrayList("housesList");

        myPrefs = getSharedPreferences("settings", getApplicationContext().MODE_PRIVATE);
        String savedName = myPrefs.getString("nameKey", "no name");
        int savedStagePosition = myPrefs.getInt("stagePositionKey", 0);
        int savedHousesPosition = myPrefs.getInt("housesPositionKey", 0);

        presenter = new Presenter(this, getApplicationContext(), housesList);

        spinnerAdapterCategory = ArrayAdapter.createFromResource(this,
                R.array.array_categories, R.layout.spinner_item);
        spinnerAdapterStage = ArrayAdapter.createFromResource(this,
                R.array.array_stages, R.layout.spinner_item);
        spinnerAdapterHouses =  new ArrayAdapter<String>(this, R.layout.spinner_item, housesList);

        spinnerAdapterCategory.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAdapterStage.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAdapterHouses.setDropDownViewResource(R.layout.spinner_dropdown_item);

        buttonAddItem = (Button) findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);

        spinnerStages = (Spinner) findViewById(R.id.spinner_stages);
        spinnerStages.setAdapter(spinnerAdapterStage);
        spinnerStages.setSelection(0, true);

        spinnerCategories = (Spinner) findViewById(R.id.spinner_category);
        spinnerCategories.setAdapter(spinnerAdapterCategory);
        spinnerCategories.setSelection(0, true);

        spinnerHouses = (Spinner) findViewById(R.id.spinner_houses);
        spinnerHouses.setAdapter(spinnerAdapterHouses);
        spinnerHouses.setSelection(0, true);

        editTextRoomNumber = (EditText) findViewById(R.id.roomNumber_EditText);
        editTextName = (EditText) findViewById(R.id.name_EditText);
        editTextNeed = (EditText) findViewById(R.id.need_EditText);
        editTextResponsible = (EditText) findViewById(R.id.responsible_EditText);
        editTextNotes = (EditText) findViewById(R.id.notes_EditText);


        if (!savedName.equalsIgnoreCase("no name")) {
            editTextResponsible.setText(savedName);
        }
        if (savedStagePosition != 0) {
            spinnerStages.setSelection(savedStagePosition);
        }
        if (savedHousesPosition != 0) {
            spinnerHouses.setSelection(savedHousesPosition);
        }

    }

    @Override
    public void onClick(View v) {

        if (v == buttonAddItem) {

            presenter.addItem();

        }
    }

    @Override
    public void showProgress(String operationExplanation) {

        progressDialog = ProgressDialog.show(this, operationExplanation, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {

        Toast.makeText(AddItemActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public ExcelData getData() {

        final String stage = spinnerStages.getSelectedItem().toString().trim(); // trim - удаяет пробелы в начале и конце строки.
        final String category = spinnerCategories.getSelectedItem().toString().trim();
        final String house = spinnerHouses.getSelectedItem().toString().trim();

        final String name = editTextName.getText().toString().trim();
        final String roomNumber = editTextRoomNumber.getText().toString().trim();
        final String need = editTextNeed.getText().toString().trim();
        final String responsible = editTextResponsible.getText().toString().trim();
        final String notes = editTextNotes.getText().toString().trim();


        if (house.contains("-") & house.length() < 2) {

            Toast.makeText(AddItemActivity.this, getString(R.string.select_house), Toast.LENGTH_LONG).show();
            return null;
        } else if (stage.contains("-")) //проверка на выбор этажа
        {
            Toast.makeText(AddItemActivity.this, getString(R.string.select_stage), Toast.LENGTH_LONG).show();
            return null;
        } else if (category.contains("-") & (category.length() < 2)) //проверка на выбор этажа
        {
            Toast.makeText(AddItemActivity.this, getString(R.string.select_category), Toast.LENGTH_LONG).show();
            return null;
        } else if (name.length() == 0 || roomNumber.length() == 0 || need.length() == 0) //проверка на заполненость обязательных полей.
        {
            Toast.makeText(AddItemActivity.this, getString(R.string.fill_fields), Toast.LENGTH_LONG).show();
            return null;
        }

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("nameKey", responsible);
        editor.putInt("stagePositionKey", spinnerStages.getSelectedItemPosition());
        editor.putInt("housesPositionKey", spinnerHouses.getSelectedItemPosition());
        editor.apply();

        excelData.setAdded(house); // incorrect Data.name (Added). - this is field for set house.
        excelData.setCategory(category);
        excelData.setName(name);
        excelData.setNeed(need);
        excelData.setNotes(notes);
        excelData.setResponsible(responsible);
        excelData.setRoomNumber(roomNumber);
        excelData.setStage(stage);

        return excelData;
    }

    @Override
    public void startMain() {
        presenter.detachView();

        presenter = null;
        excelData = null;

        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);*/

        onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // On selecting a spinner item
        // String item = adapterView.getItemAtPosition(i).toString();

        //presenter.getHistoryData(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}