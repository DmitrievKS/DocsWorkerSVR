package kirdmt.com.docsworkersvr.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kirdmt.com.docsworkersvr.Data.ExcelData;
import kirdmt.com.docsworkersvr.util.Constants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NetworkService extends AsyncTask<Void, Void, Integer> {

    private static final String SERVICE_TAG = "ServiceTAG";

    private OkHttpClient client;
    private ArrayList<ExcelData> excelDataArrayList = new ArrayList<ExcelData>();

    private Response response;
    public NetworkServiceResponse delegate;

    public NetworkService(NetworkServiceResponse delegate) {
        this.delegate = delegate;
    }

    public void setData(ArrayList<ExcelData> excelDataArrayList) {

        this.excelDataArrayList = excelDataArrayList;
    }

    public void setData(ExcelData data) {

        this.excelDataArrayList.add(data);
    }

    @Override
    protected void onPreExecute() {

        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        super.onPreExecute();

    }

    @Override
    protected Integer doInBackground(Void... voids) {

        //Log.d(SERVICE_TAG, "Data amount is: " + excelDataArrayList.size());

        for (int i = 0; i < excelDataArrayList.size(); i++) {

            RequestBody formBody = new FormBody.Builder()
                    .add("roomNumber", excelDataArrayList.get(i).getRoomNumber())
                    .add("notes", excelDataArrayList.get(i).getNotes())
                    .add("stage", excelDataArrayList.get(i).getStage())
                    .add("need", excelDataArrayList.get(i).getNeed())
                    .add("responsible", excelDataArrayList.get(i).getResponsible())
                    .add("name", excelDataArrayList.get(i).getName())
                    .add("action", excelDataArrayList.get(i).getAction())
                    .add("category", excelDataArrayList.get(i).getCategory())
                    .add("house", excelDataArrayList.get(i).getAdded())
                    .build();

            final Request request = new Request.Builder()
                    .url(Constants.WHOLE_DATA_URL)
                    .post(formBody)
                    .build();

            try {

                response = client.newCall(request).execute();
                //Log.d("OkhttpResponseTAG", response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return response.code();
    }

    @Override
    protected void onPostExecute(Integer response) {

        delegate.processFinish(response);

    }

    /*    @Override
      protected void onProgressUpdate(Integer... values) {
          super.onProgressUpdate(values);

      }*/
    public interface NetworkServiceResponse {
        void processFinish(Integer result);
    }
}


//Асинхронный запрос:
      /*  RequestBody formBody = new FormBody.Builder()
                .add("roomNumber", "1")
                .add("notes", "Your message")
                .add("stage", "2")
                .add("need", "Your message")
                .add("responsible", "Your message")
                .add("name", "Your message")
                .add("action", "addItem")
                .add("category", "телевизоры")
                .add("house", "Вязьма")
                .build();
        final Request request = new Request.Builder()
                .url(Constants.WHOLE_DATA_URL)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.d("OkhttpResponseTAG", "Fail is: " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Log.d("OkhttpResponseTAG", "Response is: " + response.toString());
            }
        });*/