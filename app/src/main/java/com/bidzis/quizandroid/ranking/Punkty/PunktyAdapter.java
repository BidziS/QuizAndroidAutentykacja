package com.bidzis.quizandroid.ranking.Punkty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bidzis.quizandroid.R;
import com.bidzis.quizandroid.ranking.RankingGlobalnyActivity;

import java.util.ArrayList;

/**
 * Created by Bidzis on 1/18/2017.
 */

public class PunktyAdapter extends BaseAdapter {
    Context context;
    ArrayList<PunktyClass> data;
    private LayoutInflater inflater = null;

    public PunktyAdapter(Context context, ArrayList<PunktyClass> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        PunktyClass item = data.get(position);
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(item.getMiejsce()+"      "+item.getUzytkownik()+"      "+item.getPunkty());
        return vi;
    }
}
