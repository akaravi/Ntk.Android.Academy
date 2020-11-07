package ntk.android.academy.event;

public class RemoveAttachEvent {

    private final int position;

    public RemoveAttachEvent(int p) {
        this.position = p;
    }

    public int GetPosition() {
        return position;
    }
}
