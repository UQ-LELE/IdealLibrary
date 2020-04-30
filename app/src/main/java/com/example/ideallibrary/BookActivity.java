package com.example.ideallibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.Favorite;
import com.example.ideallibrary.entities.Favorites;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    public static final String TAG = "Détail";

    // UI components
    private TextView mTxtBookTitle;
    private TextView mTxtBookAuthor;
    private TextView mTxtBookYear;
    private TextView mTxtBookGenre;
    private TextView mTxtBookCountry;
    private TextView mTxtBookNbPages;
    private TextView mTxtDescription;
    private CheckBox mCkbBookRead;
    private FloatingActionButton buttonFavotrite;
    private MenuItem saveItem;

    //vars
    private Book book;
    private Favorites favorites;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar_book);
        setSupportActionBar(toolbar);
        setTitle(TAG.toUpperCase());

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        initWidget();

        Intent intent = getIntent();
        book = intent.getExtras().getParcelable("selected_book");

        addListener();

        setDataToWidget(book);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                Intent intent = new Intent(BookActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addListener() {
        if (alreadyFavorite()) {
            buttonFavotrite.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.textColor)));
            buttonFavotrite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRedAccent)));
            buttonFavotrite.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
            buttonFavotrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String favoriteJson = SharedPreferencesRepository.getSharedPreferences(BookActivity.this, Constants.SHARED_FAVORITE);
                    Gson gson = new Gson();
                    Favorites favorites = gson.fromJson(favoriteJson, Favorites.class);
                    for(Favorite myFavorite : favorites){
                        if(myFavorite.getIdBook() == book.getId()){
                            favorites.remove(myFavorite);
                            break;
                        }
                    }
                    String favoritesJson = gson.toJson(favorites);
                    SharedPreferencesRepository.insertSharedPreferences(BookActivity.this, Constants.SHARED_FAVORITE, favoritesJson);
                    Fun.showToastMessage(context, "Livre supprimé de votre liste", Constants.TOAST_INFO);
                    addListener();
                }
            });
        } else {
            buttonFavotrite.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.textColor)));
            buttonFavotrite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenAccent)));
            buttonFavotrite.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
            buttonFavotrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String favoriteJson = SharedPreferencesRepository.getSharedPreferences(BookActivity.this, Constants.SHARED_FAVORITE);
                    Gson gson = new Gson();
                    Favorites favorites = gson.fromJson(favoriteJson, Favorites.class);
                    if (favorites.size() < 8) {
                        Favorite favorite = new Favorite(book.getId());
                        favorites.add(favorite);
                        String favoritesJson = gson.toJson(favorites);
                        SharedPreferencesRepository.insertSharedPreferences(BookActivity.this, Constants.SHARED_FAVORITE, favoritesJson);
                        Fun.showToastMessage(context, "Livre ajouté à votre liste", Constants.TOAST_VALIDATION);
                        addListener();
                    } else {
                        Fun.showToastMessage(context, "Votre liste est complète !", Constants.TOAST_ERROR);
                    }
                    addListener();
                }
            });
        }
    }

    private void initWidget() {
        mTxtBookTitle = findViewById(R.id.txt_book_title);
        mTxtBookAuthor = findViewById(R.id.txt_book_author);
        mTxtBookYear = findViewById(R.id.txt_book_year);
        mTxtBookGenre = findViewById(R.id.txt_book_genre);
        mTxtBookCountry = findViewById(R.id.txt_book_country);
        mTxtBookNbPages = findViewById(R.id.txt_book_nbPages);
        mCkbBookRead = findViewById(R.id.ckb_read);
        mTxtDescription = findViewById(R.id.txt_book_description);
        buttonFavotrite = findViewById(R.id.button_to_my_favorite);
    }

    private void setDataToWidget(Book book) {
        mTxtBookTitle.setText(book.getTitle());

        if (book.getAuthorFirstName() != null) {
            mTxtBookAuthor.setText(book.getAuthorFirstName() + " " + book.getAuthorName());
        } else {
            mTxtBookAuthor.setText(book.getAuthorName());
        }

        mTxtBookYear.setText(book.getYearString());
        mTxtBookGenre.setText(book.getGenre());
        mTxtBookCountry.setText(book.getCountry());
        if (book.getNbPages() == 0) {
            mTxtBookNbPages.setText("Nombre de pages inconnu");
        } else {
            mTxtBookNbPages.setText(book.getNbPages() + " p.");
        }
        mTxtDescription.setText(getDescription());
        mCkbBookRead.setChecked(book.isReadFinish());
    }

    public String getDescription() {
        String description = "";
        try {
            InputStream is = getAssets().open(book.getDescription());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            description = new String(buffer);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return description;
    }

    public boolean alreadyFavorite() {
        String favoriteJson = SharedPreferencesRepository.getSharedPreferences(BookActivity.this, Constants.SHARED_FAVORITE);
        Gson gson = new Gson();
        favorites = gson.fromJson(favoriteJson, Favorites.class);

        if (favorites.isEmpty() || favorites.size() == 0) {
            return false;
        }

        for (Favorite favorite : favorites) {
            if (favorite.getIdBook() == book.getId()) {
                return true;
            }
        }
        return false;
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

    private Intent getParentActivityIntentImplement(){
        Intent intent = null;

        Bundle extras = getIntent().getExtras();
        String goToIntent = extras.getString("goto");

        if(goToIntent.equals("BookListActivity")){
            intent = new Intent(this, BookListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }else{
            intent = new Intent(this, MyListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        return intent;
    }
}
