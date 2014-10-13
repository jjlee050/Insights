package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Event;
import com.fypj.insightsLocal.options.Settings;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsMedicalHistory.InsightsMedicalHistory;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jess on 19-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAllLatestEventsFragment extends Fragment {


    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> implements Settings{
        private InsightsEvent myApiService = null;
        private Context context;

        public EndpointsAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
                InsightsEvent.Builder builder = new InsightsEvent.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl(REMOTE_API_URL)
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }
            try {
                return myApiService.listEvents().getCursor();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }


    private final String ARG_SECTION_NUMBER = "section_num--ber";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public Fragment newInstance(int sectionNumber) {
        ViewAllLatestEventsFragment fragment = new ViewAllLatestEventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.latest_events,menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_all_latest_events, container, false);
        getActivity().getActionBar().setTitle("Lifestyle Events");
        final ListView lvLatestEvents = (ListView) rootView.findViewById(R.id.lv_latest_events);
        new EndpointsAsyncTask(ViewAllLatestEventsFragment.this.getActivity()).execute();
        final ArrayList<Event> latestEventArrList = new ArrayList<Event>();
        latestEventArrList.add(new Event(Long.parseLong("1"),"Monthly Brisk Walk","Saturday, September 20, 2014 7:00 AM","NIL","Brisk Walk for elderly residents","Ang Mo Kio CC","62087913","Ang Mio Kio Community Centre"));
        latestEventArrList.add(new Event(Long.parseLong("2"),"Walkathon","27 September 2014 3.00PM to 6.00PM",null,"A long walk from Seragoon CC to Hougang CC.",null,null,null));

        LatestEventsListAdapter adapter = new LatestEventsListAdapter(this.getActivity(), android.R.id.text1, latestEventArrList);
        lvLatestEvents.setAdapter(adapter);

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);

        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeView.setEnabled(false);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);

                    }
                }, 3000);
            }
        });

        lvLatestEvents.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeView.setEnabled(true);
                else
                    swipeView.setEnabled(false);
            }
        });

        lvLatestEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewAllLatestEventsFragment.this.getActivity(),ViewEventActivity.class);
                intent.putExtra("id",latestEventArrList.get(position).getEventID());
                intent.putExtra("name",latestEventArrList.get(position).getName());
                intent.putExtra("dateAndTime",latestEventArrList.get(position).getDateAndTime());
                intent.putExtra("guestOfHonour",latestEventArrList.get(position).getGuestOfHonour());
                intent.putExtra("desc",latestEventArrList.get(position).getDesc());
                intent.putExtra("organizer",latestEventArrList.get(position).getOrganizer());
                intent.putExtra("contactNo",latestEventArrList.get(position).getContactNo());
                intent.putExtra("location",latestEventArrList.get(position).getLocation());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainPageActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
