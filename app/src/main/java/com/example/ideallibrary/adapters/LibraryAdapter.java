package com.example.ideallibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideallibrary.R;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.Library;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<Library> libraries;
    private Context Context;
    private LayoutInflater mInflater;
    private LibraryAdapter.OnItemClickListener listener;


    public LibraryAdapter(Context context, List<Library> libraries){
        this.libraries = libraries;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_library, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapter.ViewHolder holder, int position) {
        Library currentlibrary = libraries.get(position);
        holder.title.setText(currentlibrary.getTitle());
        holder.gridIcon.setImageResource(currentlibrary.getImage());
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridIcon;

        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.txt_library_title);
            gridIcon = itemView.findViewById(R.id.img_library_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(libraries.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Library library);
    }

    public void setOnItemClickListener(LibraryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
