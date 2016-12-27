package com.newapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.newapplication.ui.FindActivity;
import com.newapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MargetFragment extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{

    GridView grid;
    SimpleAdapter simpleAdapter;
    int [] imageIds=new int[]
            {
                    R.drawable.margetph1,R.drawable.margetph2,R.drawable.margetph3,
                    R.drawable.margetph4,R.drawable.margetph5,R.drawable.margetph6
            };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Map<String,Object>> listItems=new ArrayList<>();
        for(int i=0;i<imageIds.length;i++)
        {
            Map<String,Object> listItem=new HashMap<>();
            listItem.put("image",imageIds[i]);
            listItems.add(listItem);
        }
        simpleAdapter=new SimpleAdapter(getActivity(),listItems,R.layout.cell,new String[]{"image"},new int[]{R.id.image1});

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_marget, container, false);
        grid=(GridView)view.findViewById(R.id.marget_grid);
        grid.setAdapter(simpleAdapter);
        grid.setOnItemSelectedListener(this);
        grid.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle data=new Bundle();
        data.putInt("position",position);
        Intent intent=new Intent();
        intent.setClass(getActivity(), FindActivity.class);
        intent.putExtras(data);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Bundle data=new Bundle();
        data.putInt("position",position);
        Intent intent=new Intent(getActivity(), FindActivity.class);
        intent.putExtras(data);
        startActivity(intent);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
