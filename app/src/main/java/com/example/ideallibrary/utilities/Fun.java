package com.example.ideallibrary.utilities;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideallibrary.R;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.entities.FilterPreferences;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.Comparator;

public class Fun {

    public static void loadFragment(FragmentManager fragmentManager, int idRessource, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(idRessource, fragment)
                .addToBackStack(null)
                .commit();
    }


    public static FilterPreferences getFilterPreferences(Context context) {

        String filterJson = SharedPreferencesRepository.getSharedPreferences(context, Constants.SHARED_FILTER);
        Gson gson = new Gson();
        FilterPreferences filter = gson.fromJson(filterJson, FilterPreferences.class);
        return filter;
    }

    public static String subtractAccents(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static int giveMeYear(String dateToConvert) {
        int dateConverted = 0;
        dateToConvert = dateToConvert.replaceAll("\\s", "");
        boolean isBeforceChrist = false;

        if (dateToConvert.contains("av.") || dateToConvert.contains("-")) {
            isBeforceChrist = true;
        }

        if (dateToConvert.length() > 4)
        {
            if (dateToConvert.contains("–")) {
                String[] twoDate = dateToConvert.split("–");
                dateToConvert = twoDate[0];
            }

            if (dateToConvert.contains("av.")) {
                String toRemove = "av\\.J\\.-C\\.";
                dateToConvert = dateToConvert.replaceAll(toRemove, "");
            }
            if (dateToConvert.contains("siècle")) {
                String toRemove = "siècle";
                dateToConvert = dateToConvert.replaceAll(toRemove, "");
            }

            if (StringUtils.isNumeric(dateToConvert))
            {
                dateConverted = Integer.parseInt(dateToConvert);
            } else {
                dateConverted = giveMeCentury(dateToConvert);
            }

            if (isBeforceChrist) {
                dateConverted = (~(dateConverted - 1));;
            }
        }
        else{
            dateConverted = Integer.parseInt(dateToConvert);
        }

        return dateConverted;
    }

    public static int giveMeCentury(String stringToCentury) {

        switch (stringToCentury) {
            case "Ier":
                return 0;
            case "IIe":
                return 100;
            case "IIIe":
                return 200;
            case "IVe":
                return 300;
            case "Ve":
                return 400;
            case "VIe":
                return 500;
            case "VIIe":
                return 600;
            case "VIIIe":
                return 700;
            case "IXe":
                return 800;
            case "Xe":
                return 900;
            case "XIe":
                return 1000;
            case "XIIe":
                return 1100;
            case "XIIIe":
                return 1200;
            case "XIVe":
                return 1300;
            case "XVe":
                return 1400;
            case "XVIe":
                return 1500;
            case "XVIIe":
                return 1600;
            case "XVIIIe":
                return 1700;
            case "XIXe":
                return 1800;
            case "XXe":
                return 1900;
            case "XXIe":
                return 2000;
            default:
                return 0;
        }
    }


    public static Comparator<Book> sortByPages = new Comparator<Book>() {
        @Override
        public int compare(Book book, Book t1) {
            return Integer.valueOf(book.getNbPages()).compareTo(Integer.valueOf(t1.getNbPages()));
        }
    };

    public static Comparator<Book> sortByAuthor = new Comparator<Book>() {
        @Override
        public int compare(Book book, Book t1) {
            return subtractAccents(book.getAuthorName()).compareTo(subtractAccents(t1.getAuthorName()));
        }
    };

    public static Comparator<Book> sortByCountry = new Comparator<Book>() {
        @Override
        public int compare(Book book, Book t1) {
            return subtractAccents(book.getCountry()).compareTo(subtractAccents(t1.getCountry()));
        }
    };

    public static Comparator<Book> sortByYear = new Comparator<Book>() {
        @Override
        public int compare(Book book, Book t1) {
            return Integer.valueOf(book.getYearInt()).compareTo(Integer.valueOf(t1.getYearInt()));
        }
    };

    public static void showToastMessage(Context context,String message, int type){

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(message);
        toastImage.setImageResource(type);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 27);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
