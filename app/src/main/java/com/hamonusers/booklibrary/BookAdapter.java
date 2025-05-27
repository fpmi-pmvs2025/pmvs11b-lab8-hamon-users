package com.hamonusers.booklibrary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final ArrayList<BookInfo> bookInfoArrayList;
    private final boolean isSavedList;
    private final Context context;
    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context context, boolean isSavedList) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.context = context;
        this.isSavedList = isSavedList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        int currentPosition = holder.getAdapterPosition();
        BookInfo book = bookInfoArrayList.get(currentPosition);

        holder.nameTV.setText(book.getTitle());
        holder.publisherTV.setText(book.getPublisher());
        holder.pageCountTV.setText("Pages: " + book.getPageCount());
        holder.dateTV.setText("Published On: " + book.getPublishedDate());

        if (!isSavedList) {
            holder.saveIB.setOnClickListener(v -> {

                try (BookDbHelper dbHelper = new BookDbHelper(context)) {

                    if (!dbHelper.isBookSaved(book.getPreviewLink())) {
                        boolean success = dbHelper.addBook(book);
                        if (success) {
                            Toast.makeText(context, "Book saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error while saving the book", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Book is already saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            holder.saveIB.setOnClickListener(v -> {

                try (BookDbHelper dbHelper = new BookDbHelper(context)) {
                    dbHelper.deleteBook(book.getPreviewLink());
                    bookInfoArrayList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, bookInfoArrayList.size());
                }

                Toast.makeText(context, "Book deleted from saved", Toast.LENGTH_SHORT).show();
            });
        }


        Glide.with(context).load(book.getThumbnail()).into(holder.bookIV);


        // displays book info on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetails.class);
            intent.putExtra("title", book.getTitle());
            intent.putExtra("subtitle", book.getSubtitle());
            intent.putExtra("authors", book.getAuthors());
            intent.putExtra("publisher", book.getPublisher());
            intent.putExtra("publishedDate", book.getPublishedDate());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("pageCount", book.getPageCount());
            intent.putExtra("thumbnail", book.getThumbnail());
            intent.putExtra("previewLink", book.getPreviewLink());
            intent.putExtra("infoLink", book.getInfoLink());
            intent.putExtra("buyLink", book.getBuyLink());


            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookInfoArrayList.size();
    }

    // ViewHolder class to hold UI elements for each item
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, publisherTV, pageCountTV, dateTV;
        ImageView bookIV;
        ImageButton saveIB;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.bookTitle);
            publisherTV = itemView.findViewById(R.id.publisher);
            pageCountTV = itemView.findViewById(R.id.pageCount);
            dateTV = itemView.findViewById(R.id.date);
            bookIV = itemView.findViewById(R.id.bookImage);
            saveIB = itemView.findViewById(R.id.saveButton);
        }
    }
}