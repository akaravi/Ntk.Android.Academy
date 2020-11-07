package ntk.android.academy.event;

public class clickContentEvent {

    private final int ID;

    public clickContentEvent(int ID) {
        this.ID = ID;
    }

    public int GetID() {
        return ID;
    }
}
