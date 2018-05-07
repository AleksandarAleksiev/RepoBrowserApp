package example.aleks.com.repobrowserapp.presentation.repository.details;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.base.BaseFragment;
import example.aleks.com.repobrowserapp.presentation.model.ViewModelFactory;
import example.aleks.com.repobrowserapp.presentation.repository.details.model.UserRepositoryDetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRepositoryDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRepositoryDetailsFragment extends BaseFragment implements IUserRepositoryDetailsView {

    //region properties
    public static final String TAG = UserRepositoryDetailsFragment.class.getSimpleName();
    private static final String OWNER_KEY = "UserRepositoryDetailsFragment.owner";
    private static final String REPOSITORY_KEY = "UserRepositoryDetailsFragment.repository";
    @Inject
    ViewModelFactory viewModelFactory;

    private UserRepositoryDetailsViewModel userRepositoryDetailsViewModel;
    //endregion

    //region constructor
    public UserRepositoryDetailsFragment() {
        // Required empty public constructor
    }


    public static UserRepositoryDetailsFragment newInstance(String ownerName, String repositoryName) {

        final Bundle bundle = new Bundle();
        bundle.putString(OWNER_KEY, ownerName);
        bundle.putString(REPOSITORY_KEY, repositoryName);

        final UserRepositoryDetailsFragment userRepositoryDetailsFragment = new UserRepositoryDetailsFragment();
        userRepositoryDetailsFragment.setArguments(bundle);
        return userRepositoryDetailsFragment;
    }
    //endregion

    //region fragment methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRepositoryDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserRepositoryDetailsViewModel.class);

        final Bundle arguments = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (arguments != null) {

            userRepositoryDetailsViewModel.setOwnerName(arguments.getString(OWNER_KEY, ""));
            userRepositoryDetailsViewModel.setRepositoryName(arguments.getString(REPOSITORY_KEY, ""));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_repository_details, container, false);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(OWNER_KEY, userRepositoryDetailsViewModel.getOwnerName());
        outState.putString(REPOSITORY_KEY, userRepositoryDetailsViewModel.getRepositoryName());
    }

    @Override
    public void onResume() {
        super.onResume();

        userRepositoryDetailsViewModel.getRepositoryDetails();
    }

    @Override
    public void onPause() {
        super.onPause();

        userRepositoryDetailsViewModel.dispose();
    }

    //endregion

}
