package com.asdevel.bullscows2.ui.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asdevel.bullscows2.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Fredy on 24/12/2014.
 */
public class InfoFragment extends Fragment {

    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_fragment, container, false);

        tv = (TextView) rootView.findViewById(R.id.app_tittle);
        String logo = "<font color='#78767A'>" + getString(R.string.bulls) + "</font><font color='#202020'>&</font><font color='#F4E1DE'>" + getString(R.string.cows) + "</font>";
        tv.setText(Html.fromHtml(logo));
//        rootView.findViewById(R.id.site_link).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+((TextView)v).getText().toString())));
//            }
//        });
//        rootView.findViewById(R.id.contact1_link).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView)v).getText().toString())));
//            }
//        });
//        rootView.findViewById(R.id.contact2_link).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView)v).getText().toString())));
//            }
//        });
        return rootView;
    }
}
