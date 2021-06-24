package com.example.a190508003_sevval_imre;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class SI_BesinlerAdapter extends RecyclerView.Adapter<SI_BesinlerAdapter.ViewHolder> {
    private ArrayList<SI_Besinler> si_localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView si_besinAdi;
        private final TextView si_porsiyon;
        private final TextView si_ogun;
        private final TextView si_icecek;
        private final TextView si_cinsiyet;
        private final ImageView si_resim;
        public ViewHolder(View si_view) {
            super(si_view);
            si_besinAdi = (TextView) si_view.findViewById(R.id.si_besinAdi_list);
            si_porsiyon = (TextView) si_view.findViewById(R.id.si_porsiyon_list);
            si_ogun= (TextView) si_view.findViewById(R.id.si_ogun_list);
            si_icecek= (TextView) si_view.findViewById(R.id.si_icecek_list);
            si_cinsiyet= (TextView)si_view.findViewById(R.id.si_cinsiyet_list);
            si_resim = si_view.findViewById(R.id.si_besinResmi_list);
        }
    }
    public SI_BesinlerAdapter(ArrayList<SI_Besinler> dataSet) {
        si_localDataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.si_besinliste_item, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int si_position) {
        viewHolder.si_besinAdi.setText(si_localDataSet.get(si_position).si_besinAdi);
        viewHolder.si_porsiyon.setText(si_localDataSet.get(si_position).si_porsiyon);
        viewHolder.si_ogun.setText(si_localDataSet.get(si_position).si_ogun);
        viewHolder.si_icecek.setText(si_localDataSet.get(si_position).si_icecek);
        viewHolder.si_cinsiyet.setText(si_localDataSet.get(si_position).si_cinsiyet);
        String url = si_localDataSet.get(si_position).si_url;
        Picasso.get().load(url).into(viewHolder.si_resim);
    }
    @Override
    public int getItemCount() {
        return si_localDataSet.size();
    }
}
