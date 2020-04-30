package com.example.ideallibrary.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideallibrary.R;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.utilities.Fun;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> implements Filterable {
    private List<Book> books = new ArrayList<>();
    private ArrayList<Book> booksFull = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book currentBook = books.get(position);
        holder.textViewTitle.setText(currentBook.getTitle());
        if(currentBook.getAuthorFirstName() != null){
            holder.textViewAuthor.setText(currentBook.getAuthorFirstName() + " " + currentBook.getAuthorName() +", " +currentBook.getYearString());
        }else{
            holder.textViewAuthor.setText(currentBook.getAuthorName() +", " +currentBook.getYearString());
        }

        if (currentBook.isReadFinish()) {
            int swipableToLeft = 1;
            holder.itemView.setBackgroundColor(Color.parseColor("#C3C0B1"));
            holder.itemView.setId(swipableToLeft);
        } else {
            int swipableToRight = 0;
            holder.itemView.setId(swipableToRight);
            holder.itemView.setBackgroundColor(Color.parseColor("#ECF0F1"));
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        booksFull = new ArrayList<>(books);
        notifyDataSetChanged();
    }

    public Book getBookAt(int position) {
        return books.get(position);
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAuthor;

        public BookHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_item_title);
            textViewAuthor = itemView.findViewById(R.id.text_item_author);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(books.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return bookFilter;
    }

    private Filter bookFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(@Nullable CharSequence charSequence) {
            List<Book> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(booksFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Book itemBook : booksFull) {
                    if (Fun.subtractAccents(itemBook.getTitle()).toLowerCase().contains(filterPattern) || itemBook.getAuthorName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(itemBook);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            books.clear();
            books.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
