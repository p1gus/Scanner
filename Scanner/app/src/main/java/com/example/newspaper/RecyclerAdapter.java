package com.example.newspaper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context context;
    private Cursor cursor;
    private int IdNews;
    private int IDUser;

    public RecyclerAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        holder.txtTitle.setText(cursor.getString(1));
        holder.txtDateOfPublication.setText(cursor.getString(3));
        IdNews = cursor.getInt(0);
        holder.itemView.setTag(IdNews);
        IDUser = cursor.getInt(4);
        Cursor res = holder.databaseHelper.getData(IDUser);
        while (res.moveToNext()) {
            holder.txtAuthor.setText(res.getString(2));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle, txtDateOfPublication, txtAuthor;
        public DataBaseHelper databaseHelper;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDateOfPublication = itemView.findViewById(R.id.txtDateOfPublication);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            databaseHelper = new DataBaseHelper(itemView.getContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, NewsInfo.class);
                    intent.putExtra("IdNews", (int) itemView.getTag());
                    context.startActivity(intent);
                }
            });
        }
    }
}
