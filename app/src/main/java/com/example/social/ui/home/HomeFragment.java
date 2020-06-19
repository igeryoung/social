package com.example.social.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.example.social.Swipe.CardStackAdapter;
import com.example.social.Swipe.CardStackCallback;
import com.example.social.Swipe.ItemModel;
import com.example.social.PersonalInformation;
import com.example.social.R;
import com.example.social.SwipeActivity;
import com.example.social.database.PersonalInformationDB;
import com.example.social.database.RelationDB;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPInformationDB = new PersonalInformationDB();
        mRelationDB = new RelationDB();

        System.out.println("SwipeActivity get username = " + account);
        intiPhoto(root);

        Button button = (Button) root.findViewById(R.id.like);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("LIKE");
                swipeRight();
            }
        });
        Button button2 = (Button) root.findViewById(R.id.dislike);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                swipeLeft();
            }
        });
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
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    public void swipeBottom() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
    }

    public void swipeLeft() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
    }

    public void swipeTop() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
    }

    public void swipeRight() {
        String tmp = "which position" + manager.getTopPosition();
        System.out.println(manager.getTopPosition());
        //insert pos ACCOUNT -> DATABASE
    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("https://firebasestorage.googleapis.com/v0/b/social-d1c8c.appspot.com/o/a%2Fthumbnail.jpg?alt=media&token=642fd631-b8c6-4a1f-b501-05e941b4454a", "Markonah", "", "Jember"));
        return items;
    }

}