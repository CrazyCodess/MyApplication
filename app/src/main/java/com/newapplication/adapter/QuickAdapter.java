package com.newapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.newapplication.adapter.base.BaseAdapterHelper;
import com.newapplication.adapter.base.BaseQuickAdapter;

import java.util.List;

import static com.newapplication.adapter.base.BaseAdapterHelper.get;

public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, BaseAdapterHelper> {

    public QuickAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public QuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context,layoutResId,data);
    }

	protected BaseAdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {
		return get(context, convertView, parent, layoutResId, position);
	}

}
