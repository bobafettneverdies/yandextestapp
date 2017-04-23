package com.blahblah.yandextestapp.ui.history;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.binding.translation.ListItemBinding;
import com.blahblah.yandextestapp.domain.translation.Translation;
import com.blahblah.yandextestapp.utils.ViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Dmitrii Komiakov on 23.04.2017.
 */

public class TranslationAdapter extends RealmRecyclerViewAdapter<Translation, TranslationAdapter.TranslationViewHolder> {

    private ViewHolder.OnHolderClickListener onHolderClickListener;

    public TranslationAdapter(@Nullable OrderedRealmCollection<Translation> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public TranslationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = onCreateItemView(parent);
        TranslationViewHolder viewHolder = new TranslationViewHolder(view);
        viewHolder.setOnHolderClickListener(onHolderClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TranslationViewHolder holder, int position) {
        holder.fill(getData().get(position));
    }

    public final View onCreateItemView(@NonNull final ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_translation, parent, false);
    }

    public void setOnHolderClickListener(ViewHolder.OnHolderClickListener onHolderClickListener) {
        this.onHolderClickListener = onHolderClickListener;
    }

    public static class TranslationViewHolder extends ViewHolder<Translation> {

        private ListItemBinding binding;

        public TranslationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListItemBinding.bind(itemView);
        }

        @Override
        public void fill(@NonNull Translation data) {
            super.fill(data);
            binding.setLisItem(data);
        }
    }
}
