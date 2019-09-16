package com.example.yychiu.todolistapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DoneFragment extends Fragment {
    List<Todostuff> list;
    MyDBHelper helper;
    RecyclerView recyclerView;
    MyAdapter adapter = null;
    static final String DONE = "1";
    int idArray[] = new int[100];
    int NUM = 0;


    public DoneFragment() {
    }

    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, null);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            list = new ArrayList<>();
            helper = new MyDBHelper(getActivity());
            Cursor cursor = helper.getReadableDatabase().query("exp", null,
                    "done=?", new String[]{DONE}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Todostuff todostuff = new Todostuff();
                    todostuff.setDate(cursor.getString(1));
                    todostuff.setInfo(cursor.getString(2));
                    list.add(todostuff);
                    idArray[NUM] = cursor.getInt(0);
                    NUM++;
                } while (cursor.moveToNext());
            }


            adapter = new MyAdapter(list);

            recyclerView = (RecyclerView) view;
            recyclerView.addItemDecoration(new DividerListItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);

            recyclerView.addOnItemTouchListener(new ItemClickListener(recyclerView,
                    new ItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                        }

                        @Override
                        public void onItemLongClick(View view, final int position) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("刪除選項")
                                    .setMessage("確定要刪除嗎?")
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ItemLongClickMethod(idArray[position], position);
                                        }
                                    }).show();
                        }
                    }));
        }
        return view;
    }

    public void ItemLongClickMethod(int del_id, int position) {
        String id = String.valueOf(del_id);
        MyDBHelper dbHelper = new MyDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("exp", "_id=?", new String[]{id});
        adapter.removeData(position);

        NUM = 0;
        list = new ArrayList<>();
        helper = new MyDBHelper(getActivity());
        Cursor cursor = helper.getReadableDatabase().query("exp", null,
                "done=?", new String[]{DONE}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Todostuff todostuff = new Todostuff();
                todostuff.setDate(cursor.getString(1));
                todostuff.setInfo(cursor.getString(2));
                list.add(todostuff);
                idArray[NUM] = cursor.getInt(0);
                NUM++;
            } while (cursor.moveToNext());
        }
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
