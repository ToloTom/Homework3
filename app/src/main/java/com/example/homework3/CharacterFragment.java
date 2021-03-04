package com.example.homework3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class CharacterFragment extends Fragment {

    private View view;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    private ImageView imageView;
    private int count;
    private static final String api_url = "https://rickandmortyapi.com/api/character";
    private AsyncHttpClient client = new AsyncHttpClient();
    private Random rand;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);

        textView1 = view.findViewById(R.id.textView_name);
        textView2 = view.findViewById(R.id.textView_status);
        textView3 = view.findViewById(R.id.textView_species);
        textView4 = view.findViewById(R.id.textView_gender);
        textView5 = view.findViewById(R.id.textView_originName);
        textView6 = view.findViewById(R.id.textView_locationName);
        textView7 = view.findViewById(R.id.textView_episodeAppearedIn);
        textView8 = view.findViewById(R.id.textView_episodesTitle);

        imageView = view.findViewById(R.id.imageView_face);

        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String (responseBody));
                    rand = new Random();
                    count = rand.nextInt(Integer.parseInt(jsonObject.getJSONObject("info").get("count").toString()));

                    client.get(api_url + "/" + count, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONObject json = new JSONObject(new String (responseBody));
                                textView1.setText(json.get("name").toString());
                                textView2.setText("Status: " + json.get("status").toString());
                                textView3.setText("Species: " + json.get("species").toString());
                                textView4.setText("Gender: " + json.get("gender").toString());
                                textView5.setText("Origin: " + json.getJSONObject("origin").get("name").toString());
                                textView6.setText("Location: " + json.getJSONObject("location").get("name").toString());
                                textView7.setText(getEpisodes(json.getJSONArray("episode")));
                                textView8.setText("Episode(s) appeared in:");
                                Picasso.get().load(json.get("image").toString()).into(imageView);
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



        return view;
    }

    public String getEpisodes(JSONArray json) throws JSONException {
        String allEpisodes = "";
        for (int i = 0; i < json.length(); i++){
            String[] split = json.get(i).toString().split("/");
            allEpisodes += split[split.length - 1] + ", ";
        }
        allEpisodes = allEpisodes.substring(0, allEpisodes.length() - 2); // cut out the last comma
        allEpisodes += ".";

        return allEpisodes;
    }
}
