package ntk.android.academy.event;

public class EvNotify {

    private final boolean DataChange;

    public EvNotify(boolean DC) {
        this.DataChange = DC;
    }

    public boolean DataChange() {
        return DataChange;
    }
}
