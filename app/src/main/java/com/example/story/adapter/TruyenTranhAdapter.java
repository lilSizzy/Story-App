package com.example.story.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.story.R;
import com.example.story.object.TruyenTranh;

import java.util.ArrayList;


public class TruyenTranhAdapter extends ArrayAdapter<TruyenTranh> {
    private Context context;
    private int resource;
    private ArrayList<TruyenTranh> arrTruyen;


    public TruyenTranhAdapter(Context context, int resource, ArrayList<TruyenTranh> arrTruyen) {
        super(context, resource, arrTruyen);
        this.context = context;
        this.resource = resource;
        this.arrTruyen = arrTruyen;
    }
    public void sortTruyen(String tenTruyen){
        tenTruyen= tenTruyen.toLowerCase();
        int k = 0;
        for(int i=0;i<arrTruyen.size();i++){
            TruyenTranh truyen = arrTruyen.get(i);
            String tenTruyen1 = truyen.getTenTruyen().toLowerCase();
            if(tenTruyen1.indexOf(tenTruyen)>=0){
                arrTruyen.set(i,arrTruyen.get(k));
                arrTruyen.set(k,truyen);
                k++;
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgAnhTruyen = convertView.findViewById(R.id.imgAnhTruyen);
            viewHolder.txtTenChap = convertView.findViewById(R.id.txtTenChap);
            viewHolder.txtTenTruyen = convertView.findViewById(R.id.txtTenTruyen);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TruyenTranh truyen = arrTruyen.get(position);
        viewHolder.imgAnhTruyen.setImageResource(truyen.getAnhTruyen());
        viewHolder.txtTenChap.setText(truyen.getTenChap());
        viewHolder.txtTenTruyen.setText(truyen.getTenTruyen());
        return convertView;
    }
    private static class ViewHolder {
        ImageView imgAnhTruyen;
        TextView txtTenChap;
        TextView txtTenTruyen;
    }
}
