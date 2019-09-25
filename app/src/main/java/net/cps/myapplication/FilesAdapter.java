package net.cps.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cps.myapplication.model.BackupBean;

import java.util.List;
/**
 * Created by Administrator on 2016/11/2.
 */
public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {
    private List<BackupBean> mData;
    public FilesAdapter(List<BackupBean> data) {
        mData = data ;
    }
    public OnItemClickListener itemClickListener;
    public  void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mData.get(position).name+ position);
        holder.timeView.setText(mData.get(position).time+ position);
        holder.sizeView.setText(mData.get(position).size+ position);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView,timeView,sizeView ;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            timeView = (TextView) itemView.findViewById(R.id.time);
            sizeView = (TextView) itemView.findViewById(R.id.size);
            textView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(view,getPosition());
            }
        }
    }
}