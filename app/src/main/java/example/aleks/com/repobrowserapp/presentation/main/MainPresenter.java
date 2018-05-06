package example.aleks.com.repobrowserapp.presentation.main;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;

/**
 * Created by aleks on 05/05/2018.
 */

public class MainPresenter extends BasePresenter implements IMainPresenter{

    //region properties
    private final IMainView mainView;
    //endregion

    //region constructor
    @Inject
    public MainPresenter(IMainView presenterView) {
        mainView = presenterView;
    }
    //endregion

    //region IMainPresenter implementation

    @Override
    public void start() {

        mainView.login();
    }

    //endregion
}
