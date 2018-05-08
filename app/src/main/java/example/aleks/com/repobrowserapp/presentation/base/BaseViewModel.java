package example.aleks.com.repobrowserapp.presentation.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Aleksandar on 8.5.2018 г..
 */

public class BaseViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void dispose() {

        compositeDisposable.clear();
    }

    public void add(Disposable disposable) {

        compositeDisposable.add(disposable);
    }
}
