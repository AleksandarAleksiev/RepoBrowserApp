package example.aleks.com.repobrowserapp.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import dagger.android.AndroidInjection;
import example.aleks.com.repobrowserapp.R;

/**
 * Created by aleks on 06/05/2018.
 */

public class BaseActivity extends AppCompatActivity {

    //region show screen
    public void showScreen(Fragment content,
                           String contentTag,
                           boolean addToBackStack,
                           boolean transitionContent) {

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Content area slide animation
        if (transitionContent) {

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }

        ft.replace(R.id.placeholder_content, content, contentTag);

        if (addToBackStack) {
            ft.addToBackStack(contentTag + System.identityHashCode(content));
        }

        ft.commitAllowingStateLoss();
    }
    //endregion

    //region AppCompatActivity implementation

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        switch (id) {

            case android.R.id.home: {

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    getSupportFragmentManager().popBackStack();

                    return true;
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
