package kirdmt.com.docsworkersvr;


import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Model {

    private static final String URL = "https://script.google.com/macros/s/AKfycbykkdBuaylDEGqWLpFIqlzhTU8VIgtwY4O0ytRs9OwosZowkcc/exec";

    Context addItemContext;

    DatabaseHelper db;

    Model(Context context) {
        this.addItemContext = context;

        DbInit();
    }


    public void sendData(final ExcelData excelData, final ModelCallback modelCallback) {
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


}
