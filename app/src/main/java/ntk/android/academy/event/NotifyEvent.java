package ntk.android.academy.event;

public class NotifyEvent {

    private final boolean DataChange;

    public NotifyEvent(boolean DC) {
        this.DataChange = DC;
    }

    public boolean DataChange() {
        return DataChange;
    }
}
