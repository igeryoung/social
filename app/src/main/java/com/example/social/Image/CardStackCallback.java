package com.example.social.Image;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
// the callback of Card Stack
public class CardStackCallback extends DiffUtil.Callback {

    private List<ItemModel> old, New;
    // constructor
    public CardStackCallback(List<ItemModel> old, List<ItemModel> New) {
        this.old = old;
        this.New = New;
    }
    // return old list size
    @Override
    public int getOldListSize() {
        return old.size();
    }
    // return new list size
    @Override
    public int getNewListSize() { return New.size(); }
    // compare Image
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == New.get(newItemPosition).getImage();
    }
    // compare data
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == New.get(newItemPosition);
    }
}
