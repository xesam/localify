package polok.github.com.localify.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.polok.localify.LocalifyCallback;
import com.github.polok.localify.LocalifyClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import polok.github.com.localify.demo.model.User;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalifyClient localifyClient = new LocalifyClient.Builder()
                .withAssetManager(getAssets())
                .withResources(getResources())
                .build();

        localifyClient.localify()
                .loadAssetsFile("test.txt");

        localifyClient.localify()
                .loadRawFile(R.raw.test);

        localifyClient.localify()
                .async()
                .loadAssetsFile("test.txt", new LocalifyCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }
                });

        localifyClient.localify()
                .async()
                .loadRawFile(R.raw.test, new LocalifyCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }
                });

        localifyClient.localify()
                .rx()
                .loadAssetsFile("test.txt")
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });

        localifyClient.localify()
                .rx()
                .loadRawFile(R.raw.test)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });


        localifyClient.localify()
                .rx()
                .loadAssetsFile("test.json")
                .subscribeOn(Schedulers.io())
                .map(new Function<String, User>() {
                    @Override
                    public User apply(String s) throws Exception {
                        Gson gson = new GsonBuilder().create();
                        return gson.fromJson(s, User.class);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
