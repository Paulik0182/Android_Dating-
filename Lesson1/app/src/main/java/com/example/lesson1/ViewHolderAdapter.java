package com.example.lesson1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//определяем класс ViewHolderAdapter внутри класса списка (RemarksListFragment)
//в этом классе также реализуем слушатели нажатия
public class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final RemarksListFragment mFragment;
    private final LayoutInflater mInflater;
    private final CardDataSource mDataSource;
    private RemarksListFragment.OnClickListener mOnClickListener;

    public ViewHolderAdapter(RemarksListFragment fragment, CardDataSource cardDataSource) {
        mFragment = fragment;
        mInflater = fragment.getLayoutInflater ();
        mDataSource = cardDataSource;
    }

    public void setOnClickListener(RemarksListFragment.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate ( R.layout.list_item, parent, false );
        return new ViewHolder ( v );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Remark remarkData = mDataSource.getItemAt ( position );
        holder.populate ( mFragment, remarkData );

        //клик по View
        holder.itemView.setOnClickListener ( v -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick ( v, position );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return mDataSource.getItemsCount ();
    }
}
