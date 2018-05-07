package example.aleks.com.repobrowserapp.presentation.repository.details;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
 * Use the {@link UserUserRepositoryDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserUserRepositoryDetailsFragment extends BaseFragment implements IUserRepositoryDetailsView {

    //region properties
    @Inject
    ViewModelFactory viewModelFactory;

    private UserRepositoryDetailsViewModel userRepositoryDetailsViewModel;
    //endregion

    //region constructor
    public UserUserRepositoryDetailsFragment() {
        // Required empty public constructor
    }


    public static UserUserRepositoryDetailsFragment newInstance() {
        return new UserUserRepositoryDetailsFragment();
    }
    //endregion

    //region fragment methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRepositoryDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserRepositoryDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_repository_details, container, false);

        return rootView;
    }
    //endregion

}
