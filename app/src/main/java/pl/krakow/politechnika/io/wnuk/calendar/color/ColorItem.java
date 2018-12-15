package pl.krakow.politechnika.io.wnuk.calendar.color;

public class ColorItem {
    private String colorName;
    private int mFlagItem;

    public ColorItem(String colorName, int mFlagItem) {
        this.colorName = colorName;
        this.mFlagItem = mFlagItem;
    }

    public String getColorName() {
        return colorName;
    }

    public int getmFlagItem() {
        return mFlagItem;
    }
}
