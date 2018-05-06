package example.aleks.com.repobrowserapp.di.component;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import example.aleks.com.repobrowserapp.RepoBrowserApp;
import example.aleks.com.repobrowserapp.di.module.AppModule;
import example.aleks.com.repobrowserapp.di.module.BuildModule;
import example.aleks.com.repobrowserapp.di.module.NetworkModule;

/**
 * Created by aleks on 05/05/2018.
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, NetworkModule.class, BuildModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(RepoBrowserApp app);
        Builder network(NetworkModule networkModule);
        AppComponent build();
    }

    void inject(RepoBrowserApp app);
}
