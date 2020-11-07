package ntk.android.academy.event;

public class EvDownload {

    private final boolean DataChange;

    public EvDownload(boolean DC) {
        this.DataChange = DC;
    }

    public boolean DataChange() {
        return DataChange;
    }
}
