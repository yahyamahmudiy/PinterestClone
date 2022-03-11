package com.example.pinterest.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pinterest.Fragment.Chats.ChatsFragmentMessage
import com.example.pinterest.Fragment.Chats.ChatsFragmentUpdate
import com.example.pinterest.Fragment.DetailFragment

class ChatAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return ChatsFragmentUpdate()
        }
        return ChatsFragmentMessage()
    }
}