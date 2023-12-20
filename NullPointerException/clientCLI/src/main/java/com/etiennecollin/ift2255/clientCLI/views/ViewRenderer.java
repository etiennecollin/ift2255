/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import java.util.ArrayList;

public class ViewRenderer {
    private ArrayList<View> viewHistory;
    private View nextView;

    public ViewRenderer() {
        viewHistory = new ArrayList<>();
    }

    public void addNextView(View view, boolean addToHistory) {
        this.nextView = view;
        if (addToHistory) {
            viewHistory.add(view);
        }
    }

    public void clearViewHistory() {
        viewHistory.clear();
    }

    public void renderViews() {
        while (viewHistory.size() > 0) {
            if (nextView != null) {
                View next = nextView;
                nextView = null;
                next.render();
            }
            else {
                int lastIndex = viewHistory.size() - 1;
                viewHistory.get(lastIndex).render();
                if (nextView == null) {
                    viewHistory.remove(lastIndex);
                }
            }
        }
    }
}
