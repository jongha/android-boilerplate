package net.mrlatte.khanjar.ui.views;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Jong-Ha Ahn on 10/3/15.
 */
public class LayoutBuilder {
    public static void build(Context context, RecyclerView recyclerView) {
        build(context, recyclerView, new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST), null, false);
    }

    public static void build(Context context, RecyclerView recyclerView, RecyclerView.ItemDecoration decoration) {
        build(context, recyclerView, decoration, null, false);
    }

    public static void build(Context context, RecyclerView recyclerView, boolean reverseLayout) {
        build(context, recyclerView, new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST), null, reverseLayout);
    }

    public static void build(Context context, RecyclerView recyclerView, RecyclerView.ItemDecoration decoration, boolean reverseLayout) {
        build(context, recyclerView, decoration, null, reverseLayout);
    }

    public static void build(Context context, RecyclerView recyclerView, RecyclerView.ItemDecoration decoration, RecyclerView.ItemAnimator itemAnimator, boolean reverseLayout) {
        LinearLayoutManager layoutManager = getLayoutManager(context);
        layoutManager.scrollToPosition(0);
        layoutManager.setReverseLayout(reverseLayout);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
    }

    // TODO: clean code
    public static void fixedBuild(Context context, RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
    }

    public static LinearLayoutManager getLayoutManager(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    public static void buildWrapable(Context context, RecyclerView recyclerView, RecyclerView.ItemDecoration decoration) {
        LayoutManager layoutManager = new LayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
    }

}