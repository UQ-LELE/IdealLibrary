package com.example.ideallibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideallibrary.entities.FilterPreferences;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.example.ideallibrary.utilities.Fun;
import com.google.gson.Gson;

public class FilterActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.ideallibrary.EXTRA_ID";

    // UI Components
    private CheckBox mCheckBoxByRead;
    private CheckBox mCheckBoxByNotRead;
    private RadioGroup mRadioGroupFilterSort;
    private RadioGroup mRadioGroupFilterOrder;
    private RadioButton mRadioButtonByAuthor;
    private RadioButton mRadioButtonByYear;
    private RadioButton mRadioButtonByCountry;
    private RadioButton mRadioButtonByPages;
    private RadioButton mRadioButtonByOrderUp;
    private RadioButton mRadioButtonByOrderDown;
    private Button mButtonSaveFilterPreferences;
    private TextView mTxtNbMaxPagesChoosen;
    private SeekBar mSeekBarNbMaxPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);
        setTitle("Filtre avancé");

        ActionBar actionbar = getSupportActionBar ();
        actionbar.setDisplayHomeAsUpEnabled ( true );
        actionbar.setHomeAsUpIndicator (R.drawable.ic_arrow_back);

        initWidget();

        FilterPreferences filter = Fun.getFilterPreferences(FilterActivity.this);

        setPreferencesToWidget(filter);

        addListenerToButtons();

    }

    private void initWidget() {
        mCheckBoxByRead = findViewById(R.id.cb_by_read);
        mCheckBoxByNotRead = findViewById(R.id.cd_by_not_read);
        mRadioGroupFilterSort = findViewById(R.id.rb_group_filter_sort);
        mRadioGroupFilterOrder = findViewById(R.id.rb_group_filter_order);
        mRadioButtonByAuthor = findViewById(R.id.rb_by_author);
        mRadioButtonByYear = findViewById(R.id.rb_by_year);
        mRadioButtonByCountry = findViewById(R.id.rb_by_country);
        mRadioButtonByPages = findViewById(R.id.rb_by_pages);
        mRadioButtonByOrderUp = findViewById(R.id.rb_by_order_up);
        mRadioButtonByOrderDown = findViewById(R.id.rb_by_order_down);
        mButtonSaveFilterPreferences = findViewById(R.id.btn_save_filter_preferences);
        mTxtNbMaxPagesChoosen = findViewById(R.id.txt_nb_max_pages_choosen);
        mSeekBarNbMaxPages = findViewById(R.id.sb_by_page);
    }

    private void setPreferencesToWidget(FilterPreferences filter) {
        mCheckBoxByRead.setChecked(filter.isShowRead());
        mCheckBoxByNotRead.setChecked(filter.isShowNotRead());
        mRadioButtonByAuthor.setChecked(filter.isByAuthor());
        mRadioButtonByYear.setChecked(filter.isByYear());
        mRadioButtonByCountry.setChecked(filter.isByCountry());
        mRadioButtonByPages.setChecked(filter.isByPages());
        mRadioButtonByOrderUp.setChecked(filter.isOrderUp());
        mRadioButtonByOrderDown.setChecked(filter.isOrderDown());

        if (filter.getNbMaxOfPages() == Constants.MAX_PAGES) {
            mTxtNbMaxPagesChoosen.setText("peu m'importe...");
        } else {
            mTxtNbMaxPagesChoosen.setText("" + filter.getNbMaxOfPages() + " pages maximun");

        }
        mSeekBarNbMaxPages.setProgress(filter.getNbMaxOfPages());
    }


    private void addListenerToButtons() {
        mSeekBarNbMaxPages.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 600) {
                    mTxtNbMaxPagesChoosen.setText("peu m'importe...");
                } else {
                    mTxtNbMaxPagesChoosen.setText("" + i + " pages maximun");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mButtonSaveFilterPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCheckBoxByRead.isChecked() && !mCheckBoxByNotRead.isChecked()) {
                    Fun.showToastMessage(FilterActivity.this, "Au moins un filtre par lecture obligatoire", Constants.TOAST_ERROR);
                } else {
                    FilterPreferences filter = getFilterPreferencesFromWidget();
                    saveFilterPreferences(filter);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void saveFilterPreferences(FilterPreferences filter) {
        Gson gson = new Gson();
        String filterJson = gson.toJson(filter);
        SharedPreferencesRepository.insertSharedPreferences(FilterActivity.this, Constants.SHARED_FILTER, filterJson);
    }


    private FilterPreferences getFilterPreferencesFromWidget() {
        FilterPreferences filter = new FilterPreferences();
        filter.setByAuthor(mRadioButtonByAuthor.isChecked());
        filter.setByCountry(mRadioButtonByCountry.isChecked());
        filter.setByPages(mRadioButtonByPages.isChecked());
        filter.setByYear(mRadioButtonByYear.isChecked());
        if(mSeekBarNbMaxPages.getProgress() == 600){
            filter.setNbMaxOfPages(Constants.MAX_PAGES);
        }else{
            filter.setNbMaxOfPages(mSeekBarNbMaxPages.getProgress());
        }
        filter.setShowRead(mCheckBoxByRead.isChecked());
        filter.setShowNotRead(mCheckBoxByNotRead.isChecked());
        filter.setOrderUp(mRadioButtonByOrderUp.isChecked());
        filter.setOrderDown(mRadioButtonByOrderDown.isChecked());

        return filter;
    }

}
