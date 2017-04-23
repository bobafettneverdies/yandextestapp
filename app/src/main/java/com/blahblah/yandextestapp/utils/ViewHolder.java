package com.blahblah.yandextestapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dmitrii Komiakov on 23.04.2017.
 */

public class ViewHolder<E> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public interface OnHolderClickListener<E> {
        void onHolderClick(ViewHolder<E> holder);
        //поведение как у Fragment.onOptionsItemSelected;
        boolean onHolderElementClick(View view);
    }

    private E data;
    private OnHolderClickListener<E> onHolderClickListener;

    public ViewHolder(@NonNull final View itemView) {
        super(itemView);
        this.itemView.setOnClickListener(this);
    }

    public void setOnHolderClickListener(@Nullable OnHolderClickListener<E> onHolderClickListener) {
        this.onHolderClickListener = onHolderClickListener;
    }

    @CallSuper
    public void fill(@NonNull final E data) {
        this.data = data;
    }

    @Override
    public void onClick(@NonNull View view) {
        if (onHolderClickListener != null && !onHolderClickListener.onHolderElementClick(itemView)) {
            onHolderClickListener.onHolderClick(this);
        }
    }

    protected
    @NonNull
    Resources getResources() {
        return itemView.getResources();
    }

    protected
    @NonNull
    Context getContext() {
        return itemView.getContext();
    }


    public void setSelected(boolean selected) {
        this.itemView.setSelected(selected);
    }

}
