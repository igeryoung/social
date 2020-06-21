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
import com.example.social.model.PersonalInformation;
import com.example.social.R;
import com.example.social.Image.SwipeActivity;
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
// A fragment that controls swipe card
public class HomeFragment extends Fragment {
    private static final String TAG = "TinderSwipe";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private String account;
    private PersonalInformationDB mPInformationDB;
    private RelationDB mRelationDB;
    private ArrayList<PersonalInformation> strangerList;
    private ArrayList<PersonalInformation> managerlist;
    private CardStackView cardStackView;
    private HomeViewModel homeViewModel;
    private Object HomeFragment;
    private Object AfterTest;

    private PersonalInformation mPI;
    private ImageButton Like;
    private ImageButton disLike;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        strangerList =  ((SwipeActivity)getActivity()).getStrangerList(); // A list that stores personal information
        mPI = ((SwipeActivity)getActivity()).getmPI();
        //System.out.println(strangerList);

        mPInformationDB = new PersonalInformationDB();
        mRelationDB = new RelationDB();
        System.out.println("55");
        System.out.println("SwipeActivity get username = " + account);
        intiPhoto(root);

        // link like and dislike button
        Like = (ImageButton) root.findViewById(R.id.like);
        Like.setOnClickListener(likeOnClickListener);
        disLike = (ImageButton) root.findViewById(R.id.dislike);
        disLike.setOnClickListener(dislikeOnClickListener);


        return root;
    }
    // start swiping
    private void intiPhoto(View root) {
        cardStackView = root.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            //invoked when the pic is dragged
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }
            // determine the direction of card swipe
            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    //add into like list
                    swipeLike();
                }
                if (direction == Direction.Top){
                    //add into like list
                    swipeLike();
                }
                if (direction == Direction.Left){

                }
                if (direction == Direction.Bottom){

                }
                // Paginating if top position exceed max item count
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }
            //invoked when card rewounded
            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }
            //invoked when card canceled
            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }
            // bind card with data
            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }
            // invoked when card dissapeared
            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
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
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);//enable automatic swipe and manual swipe
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }
    // add user to like list
    private void swipeLike() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
        //System.out.println(strangerList.get(manager.getTopPosition()-1).toString());
        //insert pos ACCOUNT -> DATABASE
        mRelationDB.addLike(mPI.getId(), managerlist.get(manager.getTopPosition()-1).getId());
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
    //listen dislike button and automatic left swipe
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
    // add more data if list is almost empty
    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> New = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(New);
        result.dispatchUpdatesTo(adapter);
    }
    // add a list of pic, city, name, age into cardstack, and remove user itself from hte list
    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();

        managerlist = new ArrayList(strangerList);
        for(int i = 0; i < managerlist.size(); i++)
            if(managerlist.get(i).getId().equals(mPI.getId())) {
                managerlist.remove(i);
                break;
            }
        for(PersonalInformation i : managerlist){
            items.add(new ItemModel(i.getGraph(), i.getName(), i.getCity(), i.getAge()));
        }
        return items;
    }
}