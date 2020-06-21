package com.example.social.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.example.social.Image.CardStackAdapter;
import com.example.social.Image.CardStackCallback;
import com.example.social.Image.ItemModel;
import com.example.social.PersonalInformation;
import com.example.social.R;
import com.example.social.SwipeActivity;
import com.example.social.database.PersonalInformationDB;
import com.example.social.database.RelationDB;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "TinderSwipe";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private String account;
    private PersonalInformationDB mPInformationDB;
    private RelationDB mRelationDB;
    private ArrayList<PersonalInformation> strangerList;
    private CardStackView cardStackView;
    private HomeViewModel homeViewModel;
    private Object HomeFragment;
    private Object AfterTest;

    private ImageButton Like;
    private ImageButton disLike;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        strangerList =  ((SwipeActivity)getActivity()).test1();
        System.out.println(strangerList);
        mPInformationDB = new PersonalInformationDB();
        mRelationDB = new RelationDB();
        System.out.println("55");
        System.out.println("SwipeActivity get username = " + account);
        intiPhoto(root);

        //String strtext = getArguments().getString("edttext");
        //System.out.println(strtext);
        Like = (ImageButton) root.findViewById(R.id.like);
        Like.setOnClickListener(likeOnClickListener);
        disLike = (ImageButton) root.findViewById(R.id.dislike);
        disLike.setOnClickListener(dislikeOnClickListener);


        return root;
    }

    private void intiPhoto(View root) {
        cardStackView = root.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){

                    swipeRight();
                }
                if (direction == Direction.Top){
                    //add into like list
                    swipeTop();
                }
                if (direction == Direction.Left){

                    swipeLeft();
                }
                if (direction == Direction.Bottom){

                    swipeBottom();
                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        // setup swipe attribute
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());


    }

    private void swipeBottom() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
    }

    private void swipeLeft() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
    }

    private void swipeTop() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
        //insert pos ACCOUNT -> DATABASE
    }

    private void swipeRight() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
        //insert pos ACCOUNT -> DATABASE

    }

    //listen like button and automatic right swipe
    private Button.OnClickListener likeOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        }
    };

    private Button.OnClickListener dislikeOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        }
    };

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }
    // add a list of pic, city, name, age into cardstack
    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("https://firebasestorage.googleapis.com/v0/b/social-d1c8c.appspot.com/o/a%2Fthumbnail.jpg?alt=media&token=642fd631-b8c6-4a1f-b501-05e941b4454a", "Markonah", "", "Jember"));
        items.add(new ItemModel("https://firebasestorage.googleapis.com/v0/b/social-d1c8c.appspot.com/o/a%2Fthumbnail.jpg?alt=media&token=642fd631-b8c6-4a1f-b501-05e941b4454a", "Markonah", "", "Jember"));
        items.add(new ItemModel("https://firebasestorage.googleapis.com/v0/b/social-d1c8c.appspot.com/o/a%2Fthumbnail.jpg?alt=media&token=642fd631-b8c6-4a1f-b501-05e941b4454a", "Markonah", "", "Jember"));

        return items;
    }
}