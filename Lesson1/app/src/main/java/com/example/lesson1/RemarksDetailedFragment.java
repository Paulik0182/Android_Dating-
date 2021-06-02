package com.example.lesson1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RemarksDetailedFragment extends Fragment {

    protected static final String ARG_REMARK = "remark";

    private Remark remark;

    public RemarksDetailedFragment() {
    }

    public static RemarksDetailedFragment newInstance(Remark remark) {
        RemarksDetailedFragment fragment = new RemarksDetailedFragment ();
        Bundle args = new Bundle ();
        args.putParcelable ( ARG_REMARK, remark );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (getArguments () != null) {
            remark = getArguments ().getParcelable ( ARG_REMARK );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate ( R.layout.fragment_remarks_detailed, container, false );

        TextView remarkDescription = view.findViewById ( R.id.remarkDescription );
        remarkDescription.setText ( remark.getDescription () );

        TextView remarkName = view.findViewById ( R.id.remarkName );
        remarkName.setText ( remark.getName () );

        TextView remarkDate = view.findViewById ( R.id.remarkDate );
        remarkDate.setText ( remark.getDate () );
        return view;
    }
}
