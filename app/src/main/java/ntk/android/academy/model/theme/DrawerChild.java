package ntk.android.academy.model.theme;

import com.google.gson.annotations.SerializedName;

public class DrawerChild {
    @SerializedName("Id")
    public int Id;

    @SerializedName("Type")
    public int Type;

    @SerializedName("Title")
    public String Title;

    @SerializedName("Icon")
    public String Icon;

    public DrawerChild(int id, int type, String title, String icon) {
        this.Id = id;
        this.Type = type;
        this.Title = title;
        this.Icon = icon;
    }
}
