package net.mrlatte.khanjar.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jongha on 4/8/16.
 */
public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder, K> extends RecyclerView.Adapter<T> {
    private List<K> mItems;

    public BaseRecyclerViewAdapter() {
        mItems = new ArrayList<>();
    }

    public List<K> getItems() {
        return mItems;
    }

    public K get(int location) {
        return mItems.get(location);
    }

    public void set(int location, K object) {
        mItems.set(location, object);
    }

    public K remove(int location) {
        return mItems.remove(location);
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<K> items) {
        int start = getItemCount();
        for (K item : items) {
            mItems.add(item);
        }

        notifyItemRangeInserted(start, items.size());
    }

    public void add(K item) {
        mItems.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAt(int index, K item) {
        mItems.add(index, item);
        notifyItemInserted(index);
    }

    public void addAll(K[] items) {
        int start = getItemCount();
        addAll(Arrays.asList(items));
        notifyItemRangeInserted(start, items.length);
    }

    public int indexOf(K item) {
        return mItems.indexOf(item);
    }

    public void updateItemAt(int index, K item) {
        mItems.set(index, item);
        notifyItemChanged(index);
    }

    public K remove(K item) {
        int index = mItems.indexOf(item);
        return removeItemAt(index);
    }

    public K removeItemAt(int index) {
        K item = mItems.remove(index);
        notifyItemRemoved(index);
        return item;
    }

    public void clear() {
        mItems.clear();
        notifyItemRangeRemoved(0, getItemCount());
    }
}
