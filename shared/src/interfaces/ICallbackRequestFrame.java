package interfaces;

import models.Request;

public interface ICallbackRequestFrame {
    void onRequestReceived(Request request);
}
