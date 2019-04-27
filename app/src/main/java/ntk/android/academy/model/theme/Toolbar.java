package ntk.android.academy.model.theme;

import com.google.gson.annotations.SerializedName;

public class Toolbar {

    @SerializedName("Type")
    public int Type;

    @SerializedName("BackgroundColor")
    public String BackgroundColor;

    @SerializedName("ColorBelowLine")
    public String ColorBelowLine;

    @SerializedName("HamberMenu")
    public HamberMenu HamberMenu;

    @SerializedName("SearchBox")
    public SearchBox SearchBox;

    @SerializedName("ShoppingCart")
    public ShoppingCart Cart;

    @SerializedName("Drawer")
    public Drawer Drawer;
}
