package com.example.ideallibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ideallibrary.adapters.BookAdapter;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.Favorite;
import com.example.ideallibrary.entities.Favorites;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity {
    public static final int BOOK_REQUEST = 1;

    //vars
    private BookViewModel bookViewModel;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private Favorites favorites;
    private List<Integer> idFavorites;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        context = this;

        initRecylclerView();

        initToolbar();

        getFavorite();

        bookViewModel = ViewModelProviders.of(this, new BookViewModelFactory(this.getApplication(), idFavorites)).get(BookViewModel.class);
        bookViewModel.getAllMyFavoriteBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                adapter.setBooks(books);
            }
        });

        addListener();
    }

    private void initRecylclerView() {
        recyclerView = findViewById(R.id.recycler_view_my_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_my_list);
        setSupportActionBar(toolbar);
        setTitle("Ma liste de lecture");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

    }

    private void getFavorite(){
        String favoriteJson = SharedPreferencesRepository.getSharedPreferences(MyListActivity.this, Constants.SHARED_FAVORITE);
        Gson gson = new Gson();
        favorites = gson.fromJson(favoriteJson, Favorites.class);

        if (favorites.isEmpty() || favorites.size() == 0) {
            Fun.showToastMessage(context, "Vous n'avez aucun favoris", Constants.TOAST_INFO);
        }
        idFavorites = new ArrayList<>();

        for (Favorite favorite : favorites) {
            idFavorites.add(favorite.getIdBook());
        }
    }

     private void addListener(){
       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
               ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
               int swipableToLeft = 1;
               if (viewHolder.itemView.getId() == swipableToLeft) {
                   return ItemTouchHelper.LEFT;
               } else {
                   return ItemTouchHelper.RIGHT;
               }
           }

           @Override
           public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

               Book book = adapter.getBookAt(viewHolder.getAdapterPosition());
               if (direction == ItemTouchHelper.RIGHT) {
                   book.setReadFinish(true);
                   bookViewModel.update(book);
                   Fun.showToastMessage(context, "Terminé :" + book.getTitle(), Constants.TOAST_VALIDATION);
               } else {
                   book.setReadFinish(false);
                   bookViewModel.update(book);
                   Fun.showToastMessage(context, "Non lu : " + book.getTitle(), Constants.TOAST_INFO);
               }

           }
       }).attachToRecyclerView(recyclerView);

       adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(Book book) {
               Bundle bundle = new Bundle();
               bundle.putString("goto", "MyListActivity");
               Intent intentBook = new Intent(MyListActivity.this, BookActivity.class);
               intentBook.putExtra("selected_book", book);
               intentBook.putExtras(bundle);
               startActivity(intentBook);
           }
       });
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                openDialogDeleteAll();
                return true;
            case R.id.action_account:
                Intent intent = new Intent(MyListActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialogDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Vider ma liste de lecture ?")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeAllFavorite();
                    }
                });
        builder.create();
        builder.show();
    }

    public void removeAllFavorite(){
        Favorites favorites = new Favorites();
        Gson gson = new Gson();
        String favoritesJson = gson.toJson(favorites);
        SharedPreferencesRepository.insertSharedPreferences(MyListActivity.this, Constants.SHARED_FAVORITE, favoritesJson);
        initRecylclerView();
    }


    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImplement();
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImplement();
    }

    private Intent getParentActivityIntentImplement() {
        Intent intent = null;

        Bundle extras = getIntent().getExtras();
        String goToIntent = extras.getString("goto");

        if (goToIntent.equals("LibraryActivity")) {
            intent = new Intent(this, LibraryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            intent = new Intent(this, BookListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        return intent;
    }

}
