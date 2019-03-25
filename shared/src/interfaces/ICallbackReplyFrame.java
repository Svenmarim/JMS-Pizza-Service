package interfaces;

import models.Reply;

public interface ICallbackReplyFrame {
    void onReplyReceived(Reply reply);
}
