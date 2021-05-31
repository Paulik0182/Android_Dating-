package com.example.lesson1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RemarkEditFragment extends Fragment {
    private static final String ARG_ITEM_INDEX = "RemarkEditFragment.item_index";
    private int mCurrentItemIndex = -1;


    public static RemarkEditFragment newInstance(int index) {
        RemarkEditFragment fragment = new RemarkEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_INDEX, index);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentItemIndex = getArguments().getInt(ARG_ITEM_INDEX, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remark_edit, container, false);

        final CardDataSource dataSource = CardDataSourceImpl.getInstance(getResources());
        Remark currentRemark = dataSource.getItemAt(mCurrentItemIndex);

        TextInputEditText editName = view.findViewById(R.id.list_item_text);
        editName.setText(currentRemark.getName());
        TextInputEditText editDescription = view.findViewById(R.id.remark_description_edit);
        editDescription.setText(currentRemark.getDescription());

        final MaterialButton btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            currentRemark.setName(editName.getText().toString());
            currentRemark.setDescription(editDescription.getText().toString());
            getFragmentManager().popBackStack();
        });

        final MaterialButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> getFragmentManager().popBackStack());
        return view;
    }
}
