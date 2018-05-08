package example.aleks.com.repobrowserapp.utils.scheduler;

import io.reactivex.Scheduler;

/**
 * Created by aleks on 06/05/2018.
 */

public interface ISchedulersProvider {

    Scheduler ioScheduler();

    Scheduler uiScheduler();

    Scheduler computationScheduler();
}
