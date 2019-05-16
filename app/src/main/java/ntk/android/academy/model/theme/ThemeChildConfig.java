package ntk.android.academy.model.theme;

import com.google.gson.annotations.SerializedName;

public class ThemeChildConfig {

    @SerializedName("SortId")
    public int SortId;

    @SerializedName("Title")
    public String Title;

    @SerializedName("BgColor")
    public String BgColor;

    @SerializedName("FrontColor")
    public String FrontColor;

    @SerializedName("FontSize")
    public String FontSize;

    @SerializedName("Href")
    public String Href;

    @SerializedName("ActionName")
    public String ActionName;

    @SerializedName("ActionRequest")
    public String ActionRequest;


    public ThemeChildConfig(int sortID, String title, String color, String FrontColor, String size, String href, String layoutActionRequest, String layoutRequest) {
        this.SortId = sortID;
        this.Title = title;
        this.BgColor = color;
        this.FontSize = size;
        this.ActionName = layoutActionRequest;
        this.ActionRequest = layoutRequest;
        this.FrontColor = FrontColor;
        this.Href = href;
    }
}
