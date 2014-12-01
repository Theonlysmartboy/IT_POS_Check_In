package com.itpos.itposcheckin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itpos.itposcheckin.R;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by kylealanr on 9/4/14.
 */
public class ToDo extends DefaultFragment {

    private List<Card> mCardsList;
    private CardListView mCcomputerList;
    private CardArrayAdapter mCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo, container, false);

        Computer c0 = new Computer();
        Computer c1 = new Computer("MacBook Pro", "Marcus Gabilheri", false, "Out of disk space");
        Computer c2 = new Computer("Dell Dimension", "Old Person", true, "Too old");
        Computer c3 = new Computer("MacBook", "Hali Deubler", false, "Windows installed");
        Computer c4 = new Computer("MacBook Pro", "Ryan Dawkins", false, "Arch exploded");
        Computer c5 = new Computer("Lenovo Y500", "Aaron Weaver", false, "No retinas");

        ArrayList<Computer> computers = new ArrayList<Computer>(10);
        computers.add(c0);
        computers.add(c1);
        computers.add(c2);
        computers.add(c3);
        computers.add(c4);
        computers.add(c5);

        mCcomputerList = (CardListView) view.findViewById(R.id.computers_list);
        mCardsList = new ArrayList<Card>();
        for(int i = 0; i < computers.size(); i++) {
            // get current computer in list
            Computer temp = computers.get(i);

            //create card to display the computer
            Card card = new Card(getActivity());

            //create and add the header for the card from the owner name
            CardHeader header = new CardHeader(getActivity());
            card.addCardHeader(header);
            header.setTitle(temp.getOwnerName());

            //set the content of the card to the model name and problem with the computer
            card.setTitle("Model:" + "\n    " + temp.getModelName() + "\n\nProblem:" + "\n    " + temp.getProblem());
            card.setShadow(true);

            // select drawable based on whether or not the computer is a desktop
            CardThumbnail thumb = new CardThumbnail(getActivity());
            if (temp.isDesktop()) {
                thumb.setDrawableResource(R.drawable.ic_desktop);
            } else {
                thumb.setDrawableResource(R.drawable.ic_laptop);
            }
            card.addCardThumbnail(thumb);
            mCardsList.add(card);
        }

        mCardAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mCardAdapter.setEnableUndo(true);
        mCcomputerList.setAdapter(mCardAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class Computer {
        private String modelName;
        private String ownerName;
        private boolean isDesktop;
        private String problem;

        Computer(String modelName, String ownerName, boolean isDesktop, String problem) {
            this.modelName = modelName;
            this.ownerName = ownerName;
            this.isDesktop = isDesktop;
            this.problem = problem;
        }

        Computer() {
            this.modelName = "Galago UltraPro";
            this.ownerName = "Kyle Riedemann";
            this.isDesktop = false;
            this.problem = "Too Awesome \n";
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public boolean isDesktop() {
            return isDesktop;
        }

        public void setDesktop(boolean isDesktop) {
            this.isDesktop = isDesktop;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }
    }

    //TODO parse JSON returned from http request and add them to list
}