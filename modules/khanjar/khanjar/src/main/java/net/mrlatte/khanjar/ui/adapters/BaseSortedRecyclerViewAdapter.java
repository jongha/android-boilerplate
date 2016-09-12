package net.mrlatte.khanjar.ui.adapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jongha on 4/8/16.
 */
public abstract class BaseSortedRecyclerViewAdapter<T extends RecyclerView.ViewHolder, K> extends RecyclerView.Adapter<T> {
    private SortedList<K> mItems;

    public BaseSortedRecyclerViewAdapter() {
    }

    public void initializeSortedList(Class<K> klass, final Callback sortedList) {
        mItems = new SortedList<K>(klass, new SortedList.Callback<K>() {
            @Override
            public int compare(K o1, K o2) {
                return sortedList.compare(o1, o2);
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(K oldItem, K newItem) {
                return sortedList.areContentsTheSame(oldItem, newItem);
            }

            @Override
            public boolean areItemsTheSame(K item1, K item2) {
                return sortedList.areItemsTheSame(item1, item2);
            }
        });
    }

    public SortedList<K> getItems() {
        return mItems;
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

    public K get(int position) {
        return mItems.get(position);
    }

    public void addAll(List<K> items) {
        if (items != null) {
            mItems.beginBatchedUpdates();
            for (K item : items) {
                mItems.add(item);
            }
            mItems.endBatchedUpdates();
        }
    }

    public void addAll(K[] items) {
        addAll(Arrays.asList(items));
    }

    public void add(K item) {
        mItems.add(item);
    }

    public int indexOf(K item) {
        return mItems.indexOf(item);
    }

    public void updateItemAt(int index, K item) {
        mItems.updateItemAt(index, item);
    }

    public boolean remove(K item) {
        return mItems.remove(item);
    }

    public K removeItemAt(int index) {
        return mItems.removeItemAt(index);
    }

    public void clear() {
        mItems.beginBatchedUpdates();
        //remove items at end, to avoid unnecessary array shifting
        while (mItems.size() > 0) {
            mItems.removeItemAt(mItems.size() - 1);
        }
        mItems.endBatchedUpdates();
    }

    public abstract class Callback {
        abstract public int compare(K o1, K o2);

        abstract public boolean areContentsTheSame(K oldItem, K newItem);

        abstract public boolean areItemsTheSame(K item1, K item2);
    }
}
