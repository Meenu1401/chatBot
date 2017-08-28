package meenu.com.chatbot.network;

import javax.inject.Singleton;

import dagger.Component;
import meenu.com.chatbot.SyncService;
import meenu.com.chatbot.presenter.ChatPresenterIMPL;

/**
 * Created by ${Meenu} on 27/8/17.
 */
@Singleton
@Component(modules = {NetworkModel.class})
public interface NetworkComponent {

    void inject(ChatPresenterIMPL chatPresenterIMPL);

    void inject(SyncService syncService);
}
