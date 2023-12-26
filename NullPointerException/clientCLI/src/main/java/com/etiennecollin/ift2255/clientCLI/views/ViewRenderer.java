/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.Tuple;

import java.util.ArrayList;

public class ViewRenderer {
    private final ArrayList<Tuple<View, Boolean>> viewHistory;
    private View nextView;

    public ViewRenderer() {
        viewHistory = new ArrayList<>();
    }

    public void addNextView(View view, boolean addToHistory) {
        this.nextView = view;
        viewHistory.add(new Tuple<>(view, addToHistory));
    }

    public void clearViewHistory() {
        viewHistory.clear();
    }

    public void renderViews() {
        while (!viewHistory.isEmpty()) {
            if (nextView != null) {
                View next = nextView;
                nextView = null;
                next.render();
            } else {
                int lastIndex = viewHistory.size() - 1;
                nextView = viewHistory.get(lastIndex - 1).first;
                viewHistory.remove(lastIndex);
            }
        }
    }
}
