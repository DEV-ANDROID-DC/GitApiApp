package com.debin.gitapiapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.debin.gitapiapp.R;
import com.debin.gitapiapp.model.data.GitRepo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GitAdapter extends RecyclerView.Adapter<GitAdapter.GitRepoViewHolder>{

    private List<GitRepo> gitRepoList;

    public GitAdapter(List<GitRepo> gitReposList) {
        this.gitRepoList = gitReposList;
    }

    public void setGitRepo(List<GitRepo> gitRepoList) {
        this.gitRepoList = gitRepoList;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.gitRepoList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GitRepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_item, parent, false);
        return new GitRepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GitRepoViewHolder holder, int position) {
       holder.nameTextView.setText(gitRepoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return gitRepoList.size();
    }

    class GitRepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.git_imageView)
        ImageView gitImageView;

        @BindView(R.id.name_textView)
        TextView nameTextView;

        public GitRepoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
 }
