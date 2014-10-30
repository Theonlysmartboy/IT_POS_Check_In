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

        mCcomputerList = (CardListView) view.findViewById(R.id.computers_list);
        mCardsList = new ArrayList<Card>();
        for(int i = 0; i < 10; i++) {
            Card card = new Card(getActivity());
            CardHeader header = new CardHeader(getActivity());
            card.addCardHeader(header);
            header.setTitle("Client " + i);
            card.setTitle("Computer " + i);
            card.setShadow(true);
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

    //TODO parse JSON returned from http request and add them to list
}
