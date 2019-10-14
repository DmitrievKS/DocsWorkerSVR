package kirdmt.com.docsworkersvr.Models;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kirdmt.com.docsworkersvr.CallBacks.ModelCallback;
import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.DatabaseHelper;
import kirdmt.com.docsworkersvr.ExcelData;
import kirdmt.com.docsworkersvr.util.Support;


public class Model {

    //save this link on fireBase server
    private static final String URL = "https://script.google.com/macros/s/AKfycbykkdBuaylDEGqWLpFIqlzhTU8VIgtwY4O0ytRs9OwosZowkcc/exec";

    Support support = new Support();

    Context addItemContext;

    DatabaseHelper db;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("history");

    public Model(Context context) {

        this.addItemContext = context;

        DbInit();
    }


    public void sendData(boolean dataFromBD, final ExcelData excelData, final ModelCallback modelCallback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        modelCallback.onCallBack(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        modelCallback.onCallBack(error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameter = new HashMap<>();

                //here we pass params
                parameter.put("action", excelData.getAction());
                parameter.put("stage", excelData.getStage());
                parameter.put("name", excelData.getName());
                parameter.put("roomNumber", excelData.getRoomNumber());
                parameter.put("need", excelData.getNeed());
                parameter.put("category", excelData.getCategory());
                parameter.put("responsible", excelData.getResponsible());
                parameter.put("notes", excelData.getNotes());
                parameter.put("house", excelData.getAdded());

//diabetes	roomNumber	name	need	category	whereBuy	responsible	birthday	anniversary	notes date
//Диабет	№ палаты	Имя	Нужности	Категория	Где покупать	Ответственный	Дни рождения	Юбилей апрель	Примечания

                return parameter;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(addItemContext);
        queue.add(stringRequest);

        insertFireBaseData(excelData, dataFromBD);
        //предположительно сюда вписать код добалвения истории в fireBase.

    }

    public void DbInit() {


        db = Room.databaseBuilder(addItemContext, DatabaseHelper.class, "DocsWorkerSvrDB")
                .allowMainThreadQueries()
                // .fallbackToDestructiveMigration() //new code
                .build();

    }

    public void addSqlData(ExcelData excelData) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        excelData.setId(timestamp.getTime());

        if (excelData.getNotes() == null) {
            excelData.setNotes("нет данных");
        }

        db.getDataDao().insert(excelData);

    }

    public List<ExcelData> getSqlRows() {

        if (db.getDataDao().count() > 0) {

            List<ExcelData> excelDataList = db.getDataDao().getAll();

            db.getDataDao().nukeTable();

            return excelDataList;
        } else {
            return null;
        }
    }

    public int getCountSqlRows() {
        return db.getDataDao().count();
    }



    protected void insertFireBaseData(final ExcelData excelData, boolean fromBD)
//protected void insertFireBaseData()
    {
        //fireBase example
    /*DatabaseReference usersRef = ref.child("users");
    Map<String, User> users = new HashMap<>();
    users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));*/

        final String method;

        if (fromBD) {
            method = "DataBase";
        } else {
            method = "Direct";
        }


        int houseIndex = 0;
        if (excelData.getAdded().contains("Калуга")) {
            houseIndex = 0;
        } else if (excelData.getAdded().contains("Волочек")) {
            houseIndex = 1;
        }

        final DatabaseReference housesRef = ref.child("house" + houseIndex);

        final String date = support.getCurrentMoscowTimeAndDate();

        getNewHistoryElementIndex(housesRef, new ModelCallback() {
            @Override
            public void onCallBack(String result) {

                String elementIndex = result;

                int number = Integer.parseInt(result);

                housesRef.child("historyElement" + elementIndex).setValue(new HistoryData(excelData.getName(), excelData.getAdded(), excelData.getResponsible(), excelData.getNeed(), date, method, number));
            }

        });

    }

    private void getNewHistoryElementIndex(DatabaseReference ref, final ModelCallback callback) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //long numChildren = dataSnapshot.getChildrenCount();

                callback.onCallBack(String.valueOf(dataSnapshot.getChildrenCount() + 1));

                //Log.d("fireBaseTag", " == " + numChildren);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
