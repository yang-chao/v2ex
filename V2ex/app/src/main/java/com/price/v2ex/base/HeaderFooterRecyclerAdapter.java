package com.price.v2ex.base;/*
 * Copyright (C) 2014 sebnapi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebnapi on 08.11.14.
 * <p/>
 * If you extend this Adapter you are able to add a Header, a Footer or both
 * by a similar ViewHolder pattern as in RecyclerView.
 * <p/>
 * If you want to omit changes to your class hierarchy you can try the Plug-and-Play
 * approach HeaderRecyclerViewAdapterV1.
 * <p/>
 * Don't override (Be careful while overriding)
 * - onCreateViewHolder
 * - onBindViewHolder
 * - getItemCount
 * - getItemViewType
 * <p/>
 * You need to override the abstract methods introduced by this class. This class
 * is not using generics as RecyclerView.Adapter make yourself sure to cast right.
 * <p/>
 * TOTALLY UNTESTED - USE WITH CARE - HAVE FUN :)
 */
public abstract class HeaderFooterRecyclerAdapter extends ListDataAdapter {

    private static final int TYPE_HEADER = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateBasicItemViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            onBindHeaderView(holder, position);
        } else if (holder.getItemViewType() == TYPE_FOOTER) {
            onBindFooterView(holder, position);
        } else {
            onBindBasicItemView(holder, position - (useHeader() ? 1 : 0));
        }
    }

    @Override
    public final int getItemCount() {
        int itemCount = getBasicItemCount();
        if (useHeader()) {
            itemCount += 1;
        }
        if (useFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    public int getBasicItemCount() {
        return mData.size();
    }

    @Override
    public final int getItemViewType(int position) {
        if (position == 0 && useHeader()) {
            return TYPE_HEADER;
        }
        if (useFooter() &&
                (useHeader() && position == getBasicItemCount() + 1) || (!useHeader() && position == getBasicItemCount())) {
            return TYPE_FOOTER;
        }
        if (getBasicItemType(position) <= Integer.MIN_VALUE + 1) {
            new IllegalStateException("BasicItemType starts from " + Integer.MIN_VALUE + 2 + ".");
        }
        return getBasicItemType(position);
    }

    public abstract boolean useHeader();

    public abstract ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindHeaderView(ViewHolder holder, int position);

    public abstract boolean useFooter();

    public abstract ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindFooterView(ViewHolder holder, int position);

    public abstract ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBasicItemView(ViewHolder holder, int position);

    /**
     * Make sure you don't use [Integer.MIN_VALUE, Integer.MIN_VALUE + 1] as BasicItemViewType,
     * in case that conflicts with header and footer.
     *
     * @param position
     * @return
     */
    public abstract int getBasicItemType(int position);

}
