package ntk.android.academy.model.theme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThemeChild {

    @SerializedName("SortID")
    public int SortID;

    @SerializedName("LayoutTheme")
    public int LayoutTheme;

    @SerializedName("LayoutName")
    public String LayoutName;

    @SerializedName("LayoutChildConfig")
    public List<ThemeChildConfig> LayoutChildConfigs;

    @SerializedName("LayoutConfig")
    public List<ThemeChildConfig> LayoutConfig;

    @SerializedName("LayoutRequest")
    public String LayoutRequest;

    public ThemeChild(int sortID, int layoutTheme, String layoutName, List<ThemeChildConfig> childConfigs , List<ThemeChildConfig> config, String request) {
        this.SortID = sortID;
        this.LayoutTheme = layoutTheme;
        this.LayoutName = layoutName;
        this.LayoutChildConfigs = childConfigs;
        this.LayoutRequest = request;
        this.LayoutConfig = config;
    }
}
