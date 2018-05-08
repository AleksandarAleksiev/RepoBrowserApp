package example.aleks.com.repobrowserapp.presentation.user.repoistories;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.base.BaseFragment;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.adapter.UserRepositoriesAdapter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.IUserRepositoriesPresenter;
import example.aleks.com.repobrowserapp.utils.RecyclerViewItemsSpaceDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRepositoriesFragment extends BaseFragment implements IUserRepositoriesView {

    //region properties
    public static final String TAG = UserRepositoriesFragment.class.getSimpleName();

    @Inject
    UserRepositoriesAdapter userRepositoriesAdapter;

    @Inject
    IUserRepositoriesPresenter userRepositoriesPresenter;

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
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                userRepositoriesPresenter.loadUserRepositories(true);
            }
        });

        final int margin = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_margin);

        recyclerView.addItemDecoration(new RecyclerViewItemsSpaceDecoration(margin, margin, margin, margin));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.span_count)));
        recyclerView.setAdapter(userRepositoriesAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        userRepositoriesPresenter.loadUserRepositories(false);
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
    public void update(List<RepositoryItem> userRepositories) {

        userRepositoriesAdapter.updateRepositoryItems(userRepositories);
    }

    //endregion
}
