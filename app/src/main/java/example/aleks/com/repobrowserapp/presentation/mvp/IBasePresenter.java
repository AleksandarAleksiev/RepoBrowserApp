package example.aleks.com.repobrowserapp.presentation.mvp;

import io.reactivex.disposables.Disposable;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IBasePresenter {

    void stop();

    void add(Disposable disposable);
}
