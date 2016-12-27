package com.newapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.newapplication.R;
import com.newapplication.ui.ShowFoundActivity;
import com.newapplication.ui.ShowLostActivity;

public class LostFragment extends Fragment implements View.OnClickListener{

    ImageView imageView0,imageView1;
    @Override
    public void onCreate(Bundle svaedInstanceState){
            super.onCreate(svaedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lost, container, false);
        imageView0=(ImageView)view.findViewById(R.id.lost_image0);
        imageView1=(ImageView)view.findViewById(R.id.lost_image1);
        imageView0.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        return view;
    }

    Intent intent;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lost_image0:
                intent=new Intent(getActivity(), ShowFoundActivity.class);
                startActivity(intent);
                break;
            case R.id.lost_image1:
                intent=new Intent(getActivity(), ShowLostActivity.class);
                startActivity(intent);
                break;
        }

    }
}
