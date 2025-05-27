package com.hamonusers.booklibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final ArrayList<BookInfo> bookInfoArrayList;
    private final Context context;
    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context context) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return bookInfoArrayList.size();
    }

    // ViewHolder class to hold UI elements for each item

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, publisherTV, pageCountTV, dateTV;
        ImageView bookIV;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.bookTitle);
            publisherTV = itemView.findViewById(R.id.publisher);
            pageCountTV = itemView.findViewById(R.id.pageCount);
            dateTV = itemView.findViewById(R.id.date);
            bookIV = itemView.findViewById(R.id.bookImage);
        }
    }
}