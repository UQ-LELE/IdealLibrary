package com.example.ideallibrary;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideallibrary.adapters.BookAdapter;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.FilterPreferences;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BookListActivity extends AppCompatActivity {
    public static final int BOOK_REQUEST = 1;
    public static final int FILTER_REQUEST = 2;

    public static final String TAG = "Bokklubben";

    // UI
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView txtCountReader;
    private TextView txtBookListName;

    //vars
    private BookViewModel bookViewModel;
    private BookAdapter adapter;
    private FilterPreferences filterPreferences;
    private List<Book> filteredBooks;
    private List<Book> allBooks;
    private List<Integer> emptylist;
    private Context context;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar_book_list);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar ();
        actionbar.setDisplayHomeAsUpEnabled ( true );
        actionbar.setHomeAsUpIndicator (R.drawable.ic_arrow_back);
        actionbar.setDisplayShowTitleEnabled(false);

        txtBookListName = findViewById(R.id.txt_book_list_name);
        txtBookListName.setText(TAG.toUpperCase());

        txtCountReader = findViewById(R.id.txt_count_reader);

        initRecylclerView();

        FloatingActionButton buttonFavotrite = findViewById(R.id.button_favorite);
        buttonFavotrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("goto", "BookListActivity");
                Intent intent = new Intent(BookListActivity.this, MyListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        filterPreferences = Fun.getFilterPreferences(context);
        filteredBooks = new ArrayList<>();
        emptylist = new ArrayList<>();
        emptylist.add(1);

        bookViewModel = ViewModelProviders.of(this, new BookViewModelFactory(this.getApplication(), emptylist)).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable List<Book> books) {
                allBooks = books;
                count = 0;
                for(Book book : books){
                    if(book.isReadFinish()){
                        count++;
                    }
                }
                filteredBooks = getFilteredBooks();
                txtCountReader.setText("j'ai lu " + count + "/" +  books.size());
                adapter.setBooks(filteredBooks);
            }
        });

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
                    Fun.showToastMessage(context,"Terminé : " + book.getTitle(), Constants.TOAST_VALIDATION);
                } else {
                    book.setReadFinish(false);
                    bookViewModel.update(book);
                    Fun.showToastMessage(context,"Non lu : " + book.getTitle(), Constants.TOAST_INFO);
                }

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Bundle bundle = new Bundle();
                bundle.putString("goto", "BookListActivity");
                Intent intentBook = new Intent(BookListActivity.this, BookActivity.class);
                intentBook.putExtra("selected_book", book);
                intentBook.putExtras(bundle);
                startActivity(intentBook);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(!searchView.isIconified()){
            searchView.setIconified(true);
        }

        if (requestCode == FILTER_REQUEST && resultCode == RESULT_OK) {
            filteredBooks = getFilteredBooks();
            adapter.setBooks(filteredBooks);
            adapter.notifyDataSetChanged();
            Fun.showToastMessage(context,"Filtre sauvegardé", Constants.TOAST_VALIDATION);
        } else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) BookListActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(BookListActivity.this.getComponentName()));
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                Intent intent = new Intent(BookListActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_filter:
                Intent intentFilter = new Intent(BookListActivity.this, FilterActivity.class);
                startActivityForResult(intentFilter, FILTER_REQUEST);
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void initRecylclerView() {
        recyclerView = findViewById(R.id.recycler_view_book_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Book> getFilteredBooks() {
        List<Book> filteredBooks = new ArrayList<>();
        filterPreferences = Fun.getFilterPreferences(context);

        boolean isAscendingSort = filterPreferences.isOrderUp();

        for (Book book : allBooks) {
            if(book.getNbPages()<= filterPreferences.getNbMaxOfPages()){
                if (book.isReadFinish() && filterPreferences.isShowRead()) {
                    filteredBooks.add(book);
                }
                if (!book.isReadFinish() && filterPreferences.isShowNotRead()) {
                    filteredBooks.add(book);
                }
            }
        }

        if (isAscendingSort) {
            if (filterPreferences.isByAuthor()) {
                Collections.sort(filteredBooks, Fun.sortByAuthor);
            } else if (filterPreferences.isByCountry()) {
                Collections.sort(filteredBooks, Fun.sortByCountry);
            } else if (filterPreferences.isByPages()) {
                Collections.sort(filteredBooks, Fun.sortByPages);
            } else if (filterPreferences.isByYear()) {
                Collections.sort(filteredBooks, Fun.sortByYear);
            }
        } else {
            if (filterPreferences.isByAuthor()) {
                Collections.sort(filteredBooks, Fun.sortByAuthor.reversed());
            } else if (filterPreferences.isByCountry()) {
                Collections.sort(filteredBooks, Fun.sortByCountry.reversed());
            } else if (filterPreferences.isByPages()) {
                Collections.sort(filteredBooks, Fun.sortByPages.reversed());
            } else if (filterPreferences.isByYear()) {
                Collections.sort(filteredBooks, Fun.sortByYear.reversed());
            }
        }
        return filteredBooks;
    }

}
