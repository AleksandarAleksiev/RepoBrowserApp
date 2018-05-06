package example.aleks.com.repobrowserapp.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Named;

/**
 * Created by aleks on 06/05/2018.
 */

@Named
@Retention(RetentionPolicy.RUNTIME)
public @interface AppContext {
}
