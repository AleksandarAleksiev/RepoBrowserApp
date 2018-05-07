package example.aleks.com.repobrowserapp.presentation.user.repoistories;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.base.BaseFragment;
import example.aleks.com.repobrowserapp.presentation.model.ViewModelFactory;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.adapter.UserRepositoriesAdapter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoriesViewState;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.UserRepositoriesViewModel;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.IUserRepositoriesPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRepositoriesFragment extends BaseFragment implements IUserRepositoriesView {

    //region properties
    public static final String TAG = UserRepositoriesFragment.class.getSimpleName();

    @Inject
    IUserRepositoriesPresenter userRepositoriesPresenter;

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    UserRepositoriesAdapter userRepositoriesAdapter;

    private UserRepositoriesViewModel userRepositoriesViewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    //endregion

    //region constructor
    public UserRepositoriesFragment() {
        // Required empty public constructor
    }

    public static UserRepositoriesFragment newInstance() {
        return new UserRepositoriesFragment();
    }
    //endregion

    //region fragment methods

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRepositoriesViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserRepositoriesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_repositories, container, false);

        recyclerView = rootView.findViewById(R.id.repositoriesRecyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeToRefreshLayout);

        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                userRepositoriesPresenter.loadUserRepositories(new RepositoriesViewState(userRepositoriesViewModel.getRepositoryItem(), userRepositoriesViewModel.getPage(), true));
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(userRepositoriesAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        userRepositoriesPresenter.loadUserRepositories(new RepositoriesViewState(userRepositoriesViewModel.getRepositoryItem(), userRepositoriesViewModel.getPage(), false));
    }

    @Override
    public void onPause() {
        super.onPause();

        userRepositoriesPresenter.dispose();
    }

    //endregion

    //region IUserRepositoriesView implementation

    @Override
    public void loading(boolean isLoading) {

        swipeRefreshLayout.setRefreshing(isLoading);
    }

    @Override
    public void update(List<RepositoryItem> userRepositories, int loadedPage, int totalPages) {

        userRepositoriesViewModel.getRepositoryItem().clear();
        userRepositoriesViewModel.getRepositoryItem().addAll(userRepositories);
        userRepositoriesAdapter.notifyDataSetChanged();
    }

    //endregion
}
