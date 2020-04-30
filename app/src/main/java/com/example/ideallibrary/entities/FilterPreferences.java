package com.example.ideallibrary.entities;

public class FilterPreferences {

    private boolean byAuthor;

    private boolean byYear;

    private boolean byCountry;

    private boolean byPages;

    private boolean orderUp;

    private boolean orderDown;

    private int nbMaxOfPages;

    private boolean showRead;

    private boolean showNotRead;

    public boolean isByAuthor() {
        return byAuthor;
    }

    public void setByAuthor(boolean byAuthor) {
        this.byAuthor = byAuthor;
    }

    public boolean isByYear() {
        return byYear;
    }

    public void setByYear(boolean byYear) {
        this.byYear = byYear;
    }

    public boolean isByCountry() {
        return byCountry;
    }

    public void setByCountry(boolean byCountry) {
        this.byCountry = byCountry;
    }

    public boolean isByPages() {
        return byPages;
    }

    public void setByPages(boolean byPages) {
        this.byPages = byPages;
    }

    public boolean isOrderUp() {
        return orderUp;
    }

    public void setOrderUp(boolean orderUp) {
        this.orderUp = orderUp;
    }

    public boolean isOrderDown() {
        return orderDown;
    }

    public void setOrderDown(boolean orderDown) {
        this.orderDown = orderDown;
    }

    public int getNbMaxOfPages() {
        return nbMaxOfPages;
    }

    public void setNbMaxOfPages(int nbMaxOfPages) {
        this.nbMaxOfPages = nbMaxOfPages;
    }

    public boolean isShowRead() {
        return showRead;
    }

    public void setShowRead(boolean showRead) {
        this.showRead = showRead;
    }

    public boolean isShowNotRead() {
        return showNotRead;
    }

    public void setShowNotRead(boolean showNotRead) {
        this.showNotRead = showNotRead;
    }
}
