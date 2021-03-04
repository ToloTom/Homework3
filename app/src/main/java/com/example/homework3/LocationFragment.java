package com.example.homework3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LocationFragment extends Fragment {

    private View view;
    private LocationAdapter adapter;
    private static final String api_url = "https://rickandmortyapi.com/api/location";
    private AsyncHttpClient client = new AsyncHttpClient();
    private RecyclerView recyclerView;
    private ArrayList<Location> locationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);

        locationList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView_locations);

        adapter = new LocationAdapter(locationList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for(int i = 0; i < jsonArray.length(); i++){
                        Location location = new Location(jsonArray.getJSONObject(i).get("name").toString(),jsonArray.getJSONObject(i).get("type").toString(),jsonArray.getJSONObject(i).get("dimension").toString());
                        locationList.add(location);
                    }

                    adapter = new LocationAdapter(locationList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //make sure this is correct

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return view;
    }
}
