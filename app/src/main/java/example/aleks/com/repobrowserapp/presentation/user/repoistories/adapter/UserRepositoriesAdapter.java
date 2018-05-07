package example.aleks.com.repobrowserapp.presentation.user.repoistories.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.UserRepositoriesViewModel;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoriesAdapter extends RecyclerView.Adapter<UserRepositoriesAdapter.RepositoryViewHolder> {

    private final UserRepositoriesViewModel userRepositoriesViewModel;

    @Inject
    public UserRepositoriesAdapter(UserRepositoriesViewModel userRepositoriesViewModel) {

        this.userRepositoriesViewModel = userRepositoriesViewModel;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.layout_user_repository, parent, false);

        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {

        final RepositoryItem repositoryItem = userRepositoriesViewModel.getRepositoryItem().get(position);
        holder.bind(repositoryItem);
    }

    @Override
    public int getItemCount() {
        return userRepositoriesViewModel.getRepositoryItem().size();
    }

    //region ViewHolder
    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        private TextView repositoryNameTextView;
        private TextView repositoryLanguageTextView;
        private ImageView ownerAvatarImageView;

        RepositoryViewHolder(View itemView) {
            super(itemView);

            repositoryNameTextView = itemView.findViewById(R.id.repositoryNameTextView);
            repositoryLanguageTextView = itemView.findViewById(R.id.repositoryLanguageTextView);
            ownerAvatarImageView = itemView.findViewById(R.id.ownerAvatarImageView);
        }

        void bind(RepositoryItem repositoryItem) {

            if (repositoryItem != null) {

                repositoryNameTextView.setText(repositoryItem.getRepositoryTitle());
                repositoryLanguageTextView.setText(repositoryItem.getRepositoryLanguage());
                Glide.with(this.itemView)
                        .load(repositoryItem.getOwnerAvatarUrl())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ownerAvatarImageView);
            }
        }
    }

    //endregion
}
