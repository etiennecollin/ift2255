/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Aboubakre Walid Diongue #20198446, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.views;

import com.etiennecollin.ift2255.clientCLI.Tuple;

import java.util.ArrayList;

/**
 * The {@code ViewRenderer} class is responsible for managing the rendering of views in the CLI application. It maintains
 * a view history, allowing navigation through previously rendered views.
 * <p>
 * The class keeps track of the next view to be displayed and provides methods to add views to the rendering queue,
 * clear the view history, and initiate the rendering process.
 */
public class ViewRenderer {
    /**
     * The viewHistory field maintains a history of Views and their display status.
     * Each element in the list is a Tuple containing a View and a boolean indicating whether it should be added to the history.
     */
    private final ArrayList<Tuple<View, Boolean>> viewHistory;
    /**
     * The nextView field represents the next View to be displayed.
     * It is used to keep track of the upcoming View in the rendering process.
     */
    private View nextView;

    /**
     * Constructs a new ViewRenderer with an empty view history.
     */
    public ViewRenderer() {
        viewHistory = new ArrayList<>();
    }

    /**
     * Adds the specified view to the list of views to be rendered next.
     *
     * @param view         The next view to be rendered.
     * @param addToHistory Indicates whether to add the view to the view history.
     */
    public void addNextView(View view, boolean addToHistory) {
        this.nextView = view;
        viewHistory.add(new Tuple<>(view, addToHistory));
    }

    /**
     * Clears the view history, removing all stored views.
     */
    public void clearViewHistory() {
        viewHistory.clear();
    }

    /**
     * Renders views according to the view history until it is empty.
     */
    public void renderViews() {
        while (!viewHistory.isEmpty()) {
            if (nextView != null) {
                View next = nextView;
                nextView = null;
                next.render();
            } else {
                int lastIndex = viewHistory.size() - 1;
                if (viewHistory.get(lastIndex - 1).second) {
                    nextView = viewHistory.get(lastIndex - 1).first;
                }
                viewHistory.remove(lastIndex);
            }
        }
    }
}
