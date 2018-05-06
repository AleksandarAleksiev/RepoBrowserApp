package example.aleks.com.repobrowserapp.presentation.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by aleks on 05/05/2018.
 */

public abstract class BasePresenter implements IBasePresenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    @Override
    public void add(Disposable disposable) {

        compositeDisposable.add(disposable);
    }
}
