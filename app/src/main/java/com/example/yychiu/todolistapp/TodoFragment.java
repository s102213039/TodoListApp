package com.example.yychiu.todolistapp;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    MyAdapter adapter = null;
    List<Todostuff> list;
    MyDBHelper helper;
    RecyclerView recyclerView;
    int  idArray [] = new int [100];
    static final String NOT_DONE="0";
    int NUM=0;

    public TodoFragment() {}

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_todo, container, false);
        if(view instanceof RecyclerView) {
            Context context = view.getContext();
            list = new ArrayList<>();
            helper = new MyDBHelper(getActivity());
            Cursor cursor = helper.getReadableDatabase().query("exp", null,
                    "done=?", new String[]{NOT_DONE}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Todostuff todostuff = new Todostuff();
                    todostuff.setDate(cursor.getString(1));
                    todostuff.setInfo(cursor.getString(2));
                    list.add(todostuff);

                    idArray [NUM] = cursor.getInt(0);
                    System.out.println("idArray[]"+NUM+"::"+idArray[NUM]);
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
                        public void onItemClick(View view, final int position) {

                            new AlertDialog.Builder(getActivity())
                                    .setMessage("完成這個項目?")
                                    .setNegativeButton("取消",null)
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ItemClickMethod(idArray[position],position);
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onItemLongClick(View view, final int position) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("刪除選項")
                                    .setMessage("確定要刪除嗎?")
                                    .setNegativeButton("取消",null)
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ItemLongClickMethod(idArray[position],position);
                                        }
                                    }).show();
                        }
                    }));
        }
        return view;
    }

    private void ItemClickMethod(int done_id,int position) {
        String id = String.valueOf(done_id);
        MyDBHelper dbHelper = new MyDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("done","1");
        db.update("exp",values,"_id=?",new String[]{id});
        adapter.updateData(position);

        NUM = 0;
        list.clear();
        helper = new MyDBHelper(getActivity());
        Cursor cursor = helper.getReadableDatabase().query("exp", null,
                "done=?", new String[]{NOT_DONE}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Todostuff todostuff = new Todostuff();
                todostuff.setDate(cursor.getString(1));
                todostuff.setInfo(cursor.getString(2));
                list.add(todostuff);

                idArray [NUM] = cursor.getInt(0);
                System.out.println("idArray[]"+NUM+"::"+idArray[NUM]);
                NUM++;
            } while (cursor.moveToNext());
        }
        MainActivity a = (MainActivity)getActivity();
        a.getAdapter().reLoad();
    }

    public void ItemLongClickMethod (int del_id,int position){
        String id = String.valueOf(del_id);
        System.out.println(id);
        System.out.println("刪除第"+position);
        MyDBHelper dbHelper = new MyDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("exp","_id=?", new String[]{id});
        adapter.removeData(position);

        NUM = 0;
        list = new ArrayList<>();
        helper = new MyDBHelper(getActivity());
        Cursor cursor = helper.getReadableDatabase().query("exp", null,
                "done=?", new String[]{NOT_DONE}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Todostuff todostuff = new Todostuff();
                todostuff.setDate(cursor.getString(1));
                todostuff.setInfo(cursor.getString(2));
                list.add(todostuff);

                idArray [NUM] = cursor.getInt(0);
                System.out.println("idArray[]"+NUM+"::"+idArray[NUM]);
                NUM++;
            } while (cursor.moveToNext());
        }
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
