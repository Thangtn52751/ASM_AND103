package dev.md19303.asm_and103.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.md19303.asm_and103.Model.CakeModel;
import dev.md19303.asm_and103.R;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {

    private Context context;
    private List<CakeModel> cakeList;
    private OnCakeInteractionListener listener;

    public CakeAdapter(Context context, List<CakeModel> cakeList, OnCakeInteractionListener listener) {
        this.context = context;
        this.cakeList = cakeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cake, parent, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {
        CakeModel cake = cakeList.get(position);

        holder.tvName.setText(cake.getName());
        holder.tvDescription.setText(cake.getDescription());
        holder.tvPrice.setText(String.valueOf(cake.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditCake(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onDeleteCake(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cakeList != null ? cakeList.size() : 0;
    }

    public static class CakeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription, tvPrice;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCakeName);
            tvDescription = itemView.findViewById(R.id.tvCakeDescription);
            tvPrice = itemView.findViewById(R.id.tvCakePrice);
        }
    }

    public interface OnCakeInteractionListener {
        void onEditCake(int position);

        void onDeleteCake(int position);
    }
}
