package interfaces;

public interface ICallbackFrame {
    <T> void onRequestReplyReceived(T type);
}
