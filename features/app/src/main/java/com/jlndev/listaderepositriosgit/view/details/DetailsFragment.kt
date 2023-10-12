package com.jlndev.listaderepositriosgit.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jlndev.listaderepositriosgit.R
import com.jlndev.listaderepositriosgit.bases.fragment.BaseBottomSheetFragment
import com.jlndev.listaderepositriosgit.databinding.FragmentDetailsBinding
import com.jlndev.listaderepositriosgit.utils.ext.getTypedParcelable
import com.jlndev.listaderepositriosgit.utils.ext.loadImage
import com.jlndev.listaderepositriosgit.utils.ext.setBoldSubstring
import com.jlndev.listaderepositriosgit.view.home.HomeFragment.Companion.KEY_GIT_REPOSITORY_ITEM
import com.jlndev.listaderepositriosgit.view.home.HomeViewModel
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment: BaseBottomSheetFragment<FragmentDetailsBinding, HomeViewModel>() {

    private lateinit var item: GitRepositoryItem

    override val viewModel: HomeViewModel by viewModel()

    override fun onInitData() {
        item = arguments?.getTypedParcelable(KEY_GIT_REPOSITORY_ITEM, GitRepositoryItem::class.java) ?: GitRepositoryItem.EMPTY
    }

    override fun onGetViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)

    override fun onInitViews() {
        with(binding) {
            itemRepositoryNameView.text = getString(R.string.repository_name_value, item.repositoryName).setBoldSubstring(getString(R.string.repository))
            itemOwnerNameView.text = getString(R.string.owner_name_value, item.ownerName).setBoldSubstring(getString(R.string.owner_name))
            itemTitleStarView.text = getString(R.string.stars_value, item.stargazersCount).setBoldSubstring(getString(R.string.stars))
            itemTitleForkView.text = getString(R.string.forks_value, item.forksCount).setBoldSubstring(getString(R.string.forks))
            itemLanguageView.text = getString(R.string.language_value, item.language).setBoldSubstring(getString(R.string.language))
            itemVisibilityView.text = getString(R.string.visibility_value, item.visibility).setBoldSubstring(getString(R.string.visibility))
            itemDescriptionView.text =  item.description
            itemImageview.loadImage(item.avatarUrl)
        }
    }

    override fun onInitViewModel() {
    }
}