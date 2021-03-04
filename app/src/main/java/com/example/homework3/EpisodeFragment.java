package com.example.homework3;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class EpisodeFragment extends Fragment {

    private View view;
    private TextView textView1, textView2, textView3;
    private static final String api_url = "https://rickandmortyapi.com/api/episode";
    private int count;
    private String info;
    private String notificationTitle;
    private String notification_url;
    private EpisodeAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private RecyclerView recyclerView;
    private Button button;
    private ArrayList<Episode> episodeList;
    private Random rand;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episode, container, false);

        episodeList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView_episodeCharacters);
        textView1 = view.findViewById(R.id.textView_episodeName);
        textView2 = view.findViewById(R.id.textView_episodeAired);
        textView3 = view.findViewById(R.id.textView_episodeCharacters);
        button = view.findViewById(R.id.button_notification);
        rand = new Random();


        adapter = new EpisodeAdapter(episodeList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    count = rand.nextInt(Integer.parseInt(json.getJSONObject("info").get("count").toString()));

                    client.get(api_url + "/" + count, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONObject jsonj = new JSONObject(new String(responseBody));
                                textView1.setText(jsonj.get("episode").toString() + "\n" + jsonj.get("name").toString());
                                textView2.setText(jsonj.get("air_date").toString());
                                textView3.setText("Character(s) in this episode");
                                notification_url = "https://rickandmorty.fandom.com/wiki/" + jsonj.get("name").toString().replaceAll(" ","_");
                                info = "To read more information about Episode " + jsonj.get("episode").toString() + ", please visit: " + notification_url;
                                notificationTitle = jsonj.get("episode").toString() + ": " + jsonj.get("name").toString();



                                for(int i = 0; i < jsonj.getJSONArray("characters").length(); i++){
                                    String url = jsonj.getJSONArray("characters").get(i).toString();
                                    client.get(url, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(new String (responseBody));
                                                Episode episode = new Episode(jsonObject.get("image").toString());
                                                episodeList.add(episode);

                                                adapter = new EpisodeAdapter(episodeList);
                                                recyclerView.setAdapter(adapter);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });


        button.setOnClickListener(v -> launchNotification(v));

        return view;
    }


    public void launchNotification(View view) {

        Uri webpage = Uri.parse(notification_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(getActivity());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(info))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
        notificationManager1.notify(1, builder.build());

    }
}
