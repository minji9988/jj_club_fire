//package com.example.jj_club.activities.promotion;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class ChatFragment extends Fragment {
//    private DatabaseReference mDatabase;
//    private EditText mMessageEditText;
//    private Button mSendButton;
//    private RecyclerView mMessageRecyclerView;
//    // 채팅 메시지 어댑터
//    private ChatAdapter mChatAdapter;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_chat, container, false);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mMessageEditText = view.findViewById(R.id.edit_chat_message);
//        mSendButton = view.findViewById(R.id.btn_send);
//        mMessageRecyclerView = view.findViewById(R.id.recycler_view_chat);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mMessageRecyclerView.setLayoutManager(layoutManager);
//
//        // 여기서 적절한 Query를 설정하고 채팅 어댑터를 초기화하세요
//        // Query query = ...;
//        // mChatAdapter = new ChatAdapter(query);
//        // mMessageRecyclerView.setAdapter(mChatAdapter);
//
//        mSendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = mMessageEditText.getText().toString();
//                if (!TextUtils.isEmpty(message)) {
//                    // 메시지를 Firebase 데이터베이스에 저장합니다.
//                    ChatMessage chatMessage = new ChatMessage(message, /* 사용자 ID 등 다른 정보 */);
//                    mDatabase.child("chats").push().setValue(chatMessage);
//                    mMessageEditText.setText("");
//                }
//            }
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // 어댑터가 데이터베이스에서 업데이트를 받도록 시작합니다.
//        if (mChatAdapter != null) {
//            mChatAdapter.startListening();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        // 어댑터가 데이터베이스에서 업데이트를 받는 것을 중지합니다.
//        if (mChatAdapter != null) {
//            mChatAdapter.stopListening();
//        }
//    }
//}