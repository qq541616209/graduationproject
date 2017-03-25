package com.example.memory.dsn_kuaiyue;

import android.support.v4.app.Fragment;
import android.widget.ViewSwitcher.ViewFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.os.Bundle;
/**
 * Created by Memory on 2017/3/17.
 */

public class Fragmentcar extends Fragment implements ViewFactory {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final Button bigbus;
        final Button smallbus;
        final Button smallcar;
        final ImageSwitcher imgswt;
        View view = inflater.inflate(R.layout.frgmtordercar, container, false);
        bigbus = (Button)view.findViewById(R.id.bigbus);
        smallcar = (Button)view.findViewById(R.id.smallcar);
        smallbus = (Button)view.findViewById(R.id.smallbus);
        imgswt = (ImageSwitcher)view.findViewById(R.id.imgswt);
        imgswt.setFactory(this);
        imgswt.setImageResource(R.drawable.bigbus);
        imgswt.setInAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in ));
        imgswt.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
        bigbus.setBackgroundColor(android.graphics.Color.GREEN);
        bigbus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                bigbus.setBackgroundColor(android.graphics.Color.GREEN);
                smallbus.setBackgroundColor(android.graphics.Color.WHITE);
                smallcar.setBackgroundColor(android.graphics.Color.WHITE);
                imgswt.setImageResource(R.drawable.bigbus);
            }
        });
        smallcar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                smallcar.setBackgroundColor(android.graphics.Color.GREEN);
                smallbus.setBackgroundColor(android.graphics.Color.WHITE);
                bigbus.setBackgroundColor(android.graphics.Color.WHITE);
                imgswt.setImageResource(R.drawable.smallcar);
            }
        });
        smallbus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                smallbus.setBackgroundColor(android.graphics.Color.GREEN);
                bigbus.setBackgroundColor(android.graphics.Color.WHITE);
                smallcar.setBackgroundColor(android.graphics.Color.WHITE);
                imgswt.setImageResource(R.drawable.smallbus);
            }
        });
        return view;
    }

    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        final ImageView i = new ImageView(getActivity());
        i.setBackgroundColor(0xff000000);
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return i ;
    }
}
