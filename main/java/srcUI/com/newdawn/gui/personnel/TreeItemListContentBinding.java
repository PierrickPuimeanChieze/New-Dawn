package com.newdawn.gui.personnel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.WeakListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class TreeItemListContentBinding<S>
        implements ListChangeListener<S>, WeakListener {

    private final WeakReference<List<TreeItem<S>>> listRef;

    public TreeItemListContentBinding(List<TreeItem<S>> paramList) {
        this.listRef = new WeakReference<>(paramList);
    }

    public void onChanged(ListChangeListener.Change<? extends S> paramChange) {
        List<TreeItem<S>> localList = this.listRef.get();
        if (localList == null) {
            paramChange.getList().removeListener(this);
        } else {
            while (paramChange.next()) {
                if (paramChange.wasPermutated()) {
                    localList.
                            subList(paramChange.getFrom(), paramChange.getTo()).
                            clear();
                    final List<? extends S> subList = paramChange.
                      getList().
                      subList(paramChange.getFrom(), paramChange.getTo());
                    List<TreeItem<S>> toAdd = convertList(subList);
    
                    localList.addAll(paramChange.getFrom(), toAdd);
                    continue;
                }
                if (paramChange.wasRemoved()) {
                    localList.subList(paramChange.getFrom(), paramChange.
                            getFrom() + paramChange.getRemovedSize()).clear();
                }
                if (paramChange.wasAdded()) {
                    final List<? extends S> listToConvert = paramChange.
                            getAddedSubList();
                    localList.addAll(paramChange.getFrom(), convertList(listToConvert));
                }
            }
        }
    }

    public boolean wasGarbageCollected() {
        return this.listRef.get() == null;
    }

    public int hashCode() {
        List localList = (List) this.listRef.get();
        return localList == null ? 0 : localList.hashCode();
    }

    public boolean equals(Object paramObject) {
        if (this == paramObject) {
            return true;
        }

        List localList1 = (List) this.listRef.get();
        if (localList1 == null) {
            return false;
        }

        if ((paramObject instanceof TreeItemListContentBinding)) {
            TreeItemListContentBinding localListContentBinding = (TreeItemListContentBinding) paramObject;
            List localList2 = (List) localListContentBinding.listRef.get();
            return localList1 == localList2;
        }
        return false;
    }

    private List<TreeItem<S>> convertList(final List<? extends S> subList) {
        List<TreeItem<S>> toAdd = new ArrayList<>();
        for (S sourceItem : subList) {
            toAdd.add(new TreeItem<>(sourceItem));
        }
        return toAdd;
    }
}
