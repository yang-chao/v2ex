package com.price.v2ex.adapter;

import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.price.v2ex.R;
import com.price.v2ex.common.ActivityHelper;
import com.price.v2ex.common.ListDataAdapter;
import com.price.v2ex.fragment.NodeFragment;
import com.price.v2ex.io.model.Node;

import java.util.List;

/**
 * Created by YC on 15-1-9.
 */
public class NodesAdapter extends ListDataAdapter<Node> {

    private Context mContext;

    public NodesAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_node, null);
        return new NodeHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final NodeHolder nodeHolder = (NodeHolder) holder;
        final Node node = mData.get(position);
        nodeHolder.title.setText(node.getTitle());
        nodeHolder.count.setText(mContext.getString(R.string.node_suffix, node.getTopics()));

        nodeHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(holder.itemView, 0, 0, 0, 0);
                ActivityHelper.startActivity(mContext, NodeFragment.newInstance(node.getId(), node.getName()), optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public List<Node> getData() {
        return mData;
    }

    class NodeHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView count;

        public NodeHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            count = (TextView) itemView.findViewById(R.id.count);
        }
    }
}
