package anu.g35.sharebooks.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.databinding.FragmentHomeBinding;

/**
 * Fragment for the chat room
 *
 * @Author Huizhe_Ruan, u7723366
 * @since 2024-05-05
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private ChatAdapter chatAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chatRecyclerView = binding.chatRecyclerView;
        messageEditText = binding.messageEditText;
        sendButton = binding.sendButton;

        ArrayList<UserAction> userActions = homeViewModel.getUserActions();
        chatAdapter = new ChatAdapter(userActions);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);

        // Observe the search results
        homeViewModel.getLiveUserActions().observe(getViewLifecycleOwner(), results -> {
            chatAdapter.notifyDataSetChanged();
            // Scroll to the last item
            if (results == null || results.isEmpty()) {
                return;
            }
            chatRecyclerView.scrollToPosition(results.size() - 1);
        });

        // Send the message to the chat room
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (!message.isEmpty()) {
                    homeViewModel.receiveMyMessage(message);
                    messageEditText.setText("");
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}