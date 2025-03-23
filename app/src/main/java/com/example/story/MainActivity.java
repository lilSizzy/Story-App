package com.example.story;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.story.adapter.TruyenTranhAdapter;
import com.example.story.object.TruyenTranh;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    GridView gridViewTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> arrTruyen;
    EditText edtTimKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        khoitao();
        anhXa();
        setUp();
        setClick();

    }
    private void khoitao() {
        //Khoi tao danh sach truyen
        arrTruyen = new ArrayList<>();
        arrTruyen.add(new TruyenTranh("Conan", "Chapter 1", R.drawable.conannn));
        arrTruyen.add(new TruyenTranh("Doraemon", "Chapter 1", R.drawable.doraemon));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Võ Đang Kỳ Hiệp", "Chapter 1", R.drawable.vodangkyhiep));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        arrTruyen.add(new TruyenTranh("Killing Killer", "Chapter 1", R.drawable.killingkiller));
        adapter = new TruyenTranhAdapter(this, R.layout.item_truyen, arrTruyen);
    }
    private void anhXa() {
        gridViewTruyen = findViewById(R.id.gridViewTruyen);
        edtTimKiem = findViewById(R.id.edtTimKiem);
    }
    private void setUp() {
        gridViewTruyen.setAdapter(adapter);
    }
    private void setClick() {
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tenTruyen = edtTimKiem.getText().toString();
                adapter.sortTruyen(tenTruyen);
            }
        });
    }
}