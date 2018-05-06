package example.aleks.com.repobrowserapp.presentation.main.presenter;

import example.aleks.com.repobrowserapp.presentation.mvp.IBasePresenter;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IMainPresenter extends IBasePresenter {

    void start(String authCode);
}
