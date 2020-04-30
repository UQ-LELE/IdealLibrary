package com.example.ideallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.ideallibrary.adapters.BookAdapter;
import com.example.ideallibrary.adapters.LibraryAdapter;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.Library;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
    private List<Library> mLibraries;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        context = this;
        //NB: cette activité n'est qu'une ébauche. Le données sont en dur.
        Toolbar toolbar = findViewById(R.id.toolbar_library);
        setSupportActionBar(toolbar);


        mRecyclerView = findViewById(R.id.recycler_view_library);

        Library library1 = new Library("Bokkluben", R.drawable.ic_bookcase_bokklubben);
        Library library2 = new Library("Le Monde", R.drawable.ic_bookcase_monde);
        Library library3 = new Library("Ma Liste", R.drawable.ic_ideal_library);
        mLibraries = new ArrayList<>();
        mLibraries.add(library1);
        mLibraries.add(library2);
        mLibraries.add(library3);

        mAdapter = new LibraryAdapter(this, mLibraries);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LibraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Library library) {
                if (library.getTitle() == "Bokkluben") {
                    Intent intent = new Intent(LibraryActivity.this, BookListActivity.class);
                    startActivity(intent);
                } else if (library.getTitle() == "Ma Liste") {
                    Bundle bundle = new Bundle();
                    bundle.putString("goto", "LibraryActivity");
                    Intent intent = new Intent(LibraryActivity.this, MyListActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Fun.showToastMessage(context, "Bibliothèque vide !", Constants.TOAST_INFO);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.library_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                Intent intent = new Intent(LibraryActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}