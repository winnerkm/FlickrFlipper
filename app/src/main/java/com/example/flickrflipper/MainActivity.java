package com.example.flickrflipper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.flickrflipper.FeedsPage.FeedsFragment;
import com.example.flickrflipper.model.FlickrResponse;
import com.example.flickrflipper.network.ApiClient;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FeedsFragment.OnFragmentInteractionListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private FlickrResponse mFlickrResponse = new FlickrResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (isNetworkAvailable())
            apiCall();
        else {
            showAlert(getString(R.string.no_internet), getString(R.string.internet_error_msg));
        }
    }

    private void showAlert(String title, String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        if (errorMsg != null) builder.setMessage(R.string.internet_error_msg);
        else builder.setMessage(getString(R.string.retry));
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    private void apiCall() {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading Feeds.. !!!");
        progress.show();

        ApiClient.FlickrInterface flickrInterface = ApiClient.getClient().create(ApiClient.FlickrInterface.class);

        Call<ResponseBody> call = flickrInterface.getUserData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(response.body().toString(), "");

                if (progress != null) progress.hide();

                //Try to get response body
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        if (line.equalsIgnoreCase("jsonFlickrFeed({"))
                            sb.append("{");
                        else sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String result = sb.toString().substring(0, sb.toString().length() - 1);
                Gson gsonObj = new Gson();

                mFlickrResponse = gsonObj.fromJson(result, FlickrResponse.class);

                openFragment();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(t.getLocalizedMessage(), "Failure");
                if (progress != null) progress.hide();
                showAlert(t.getLocalizedMessage(), null);
            }
        });

    }

    private void openFragment() {
        Fragment plusOneFragment = FeedsFragment.newInstance(mFlickrResponse);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, plusOneFragment).commit();
    }
}
