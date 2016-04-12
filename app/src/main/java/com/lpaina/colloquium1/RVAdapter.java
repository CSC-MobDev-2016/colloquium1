package com.lpaina.colloquium1;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {

    private Cursor cursor;

    public RVAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    public void updateCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        if (cursor.moveToPosition(i)) {
            cardViewHolder.title = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TITLE));
            cardViewHolder.value = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_VALUE));

            cardViewHolder.textViewTitle.setText(cardViewHolder.title);
            cardViewHolder.textViewValue.setText(cardViewHolder.value);

            cardViewHolder.id = cursor.getInt(cursor.getColumnIndex(FeedsTable._ID));
        }
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textViewTitle;
        final TextView textViewValue;
        final CardView cardView;

        final Context context;

        int id;
        String title;
        String value;

        CardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewValue = (TextView) itemView.findViewById(R.id.value);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {

        }

    }

}
