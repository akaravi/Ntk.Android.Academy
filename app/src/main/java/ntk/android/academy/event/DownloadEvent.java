package ntk.android.academy.event;

public class DownloadEvent {

    private final boolean DataChange;

    public DownloadEvent(boolean DC) {
        this.DataChange = DC;
    }

    public boolean DataChange() {
        return DataChange;
    }
}
