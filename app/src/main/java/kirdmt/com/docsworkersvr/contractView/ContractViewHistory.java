package kirdmt.com.docsworkersvr.contractView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.Data.HistoryData;

public interface ContractViewHistory {

    /*void showProgress(String operationExplanation);

    void hideProgress();

    void startMain();*/

    void closeHistoryActivity();

    void fillRecycler(List<HistoryData> list);

    void createHousesSpinner(List<String> list);

    Context getContext();

    void showToast(String message);

  }
