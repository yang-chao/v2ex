package com.price.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.price.v2ex.R;
import com.price.v2ex.base.ListDataAdapter;
import com.price.v2ex.model.Node;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NodeHolder nodeHolder = (NodeHolder) holder;
        Node node = mData.get(position);
        nodeHolder.title.setText(node.getTitle());
        nodeHolder.count.setText(mContext.getString(R.string.node_suffix, node.getTopics()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public List getData() {
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
