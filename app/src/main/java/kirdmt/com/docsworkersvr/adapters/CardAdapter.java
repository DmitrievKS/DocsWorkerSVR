package kirdmt.com.docsworkersvr.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.util.Support;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    Support support = new Support();

    List<HistoryData> historyData = new ArrayList<HistoryData>();
    private Context historyContext;
    private String residentStr, authorStr, needStr, dateStr, methodStr;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;


        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CardAdapter(List<HistoryData> historyData, Context context) {

        this.historyData = historyData;
        historyContext = context;

    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new ViewHolder(cv);

    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView resident = (TextView) cardView.findViewById(R.id.resident_text);
        TextView author = (TextView) cardView.findViewById(R.id.author_text);
        TextView need = (TextView) cardView.findViewById(R.id.need_text);
        TextView date = (TextView) cardView.findViewById(R.id.date_text);
        TextView method = (TextView) cardView.findViewById(R.id.method_text);

        //Log.d("recyclerTAG", authorStrArr[position]);
        //Date currentTime = Calendar.getInstance().getTime();

        residentStr = support.stringCutter(historyData.get(position).getResident(), 25);
        authorStr = support.stringCutter(historyData.get(position).getAuthor(), 25);
        needStr = support.stringCutter(historyData.get(position).getNeed(), 25);
        dateStr = support.stringCutter(historyData.get(position).getDate(), 25);
        methodStr = support.stringCutter(historyData.get(position).getMethod(), 25);

        resident.setText(historyContext.getString(R.string.residentIs) + " " + residentStr);
        author.setText(historyContext.getString(R.string.author) + " " + authorStr);
        need.setText(historyContext.getString(R.string.need) + " " + needStr);
        date.setText(historyContext.getString(R.string.date) + " " + dateStr);
        method.setText(historyContext.getString(R.string.method) + " " + methodStr);

    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }
}
