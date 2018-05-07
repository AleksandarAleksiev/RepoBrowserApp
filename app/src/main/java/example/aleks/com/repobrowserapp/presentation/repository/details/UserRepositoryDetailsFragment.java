package example.aleks.com.repobrowserapp.presentation.repository.details;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.base.BaseFragment;
import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.IUserRepositoryDetailsPresenter;

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

    private SwipeRefreshLayout swipeToRefreshLayout;
    private ImageView ownerAvatarImageView;
    private TextView repositoryTitleTextView;
    private TextView repositoryLanguageTextView;

    private String ownerName;
    private String repositoryName;

    @Inject
    IUserRepositoryDetailsPresenter repositoryDetailsPresenter;

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
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle arguments = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (arguments != null) {

            ownerName = arguments.getString(OWNER_KEY, "");
            repositoryName = arguments.getString(REPOSITORY_KEY, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_repository_details, container, false);

        swipeToRefreshLayout = rootView.findViewById(R.id.swipeToRefreshLayout);
        ownerAvatarImageView = rootView.findViewById(R.id.ownerAvatarImageView);
        repositoryTitleTextView = rootView.findViewById(R.id.repositoryTitleTextView);
        repositoryLanguageTextView = rootView.findViewById(R.id.repositoryLanguageTextView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(OWNER_KEY, ownerName);
        outState.putString(REPOSITORY_KEY, repositoryName);
    }

    @Override
    public void onResume() {
        super.onResume();

        repositoryDetailsPresenter.getDetails(ownerName, repositoryName);
    }

    @Override
    public void onPause() {
        super.onPause();

        repositoryDetailsPresenter.dispose();
    }

    //endregion

    //region IUserRepositoryDetailsView implementation
    @Override
    public void loading(boolean loading) {

        swipeToRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void update(String repositoryTitle, String ownerAvatar, String repositoryLanguage) {

        repositoryTitleTextView.setText(repositoryTitle);
        repositoryLanguageTextView.setText(repositoryLanguage);
        Glide.with(this)
                .load(ownerAvatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ownerAvatarImageView);
    }
    //endregion
}
