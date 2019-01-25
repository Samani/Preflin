package com.rsamani.preflin.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rsamani.preflin.Preflin;

public class MainActivity extends AppCompatActivity {

    public static final String SP_SCORES = "SP_SCORES";
    public static final String PAGE = "PAGE";
    public static final String USER1 = "USER1";
    public static final String BOOK1 = "BOOK1";
    public static final String BOOK_LIST = "BOOK_LIST";
    private static final String TAG = "MainActivity";

    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disposables = new CompositeDisposable();

        // Named SharedPreferences
        Preflin.from(SP_SCORES).putDoubleList(USER1, Arrays.asList(1.1, 2.2, 3.33));
        Log.d(TAG, "scores of USER1 =" + Preflin.from(SP_SCORES).getDoubleList(USER1).toString());

        // Default SharedPreferences
        Preflin.putObject(BOOK1, new Book("Deep Learning", 400));
        Log.d(TAG, "BOOK1=" + Preflin.getObject(BOOK1, Book.class));

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Java", 250));
        bookList.add(new Book("C++", 400));
        Preflin.putObjectList(BOOK_LIST, bookList);
        Log.d(TAG, "BOOK_LIST=" + Preflin.getObjectList(BOOK_LIST, Book.class));

        Button incrementButton = (Button) findViewById(R.id.increment);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Incrementing page from " + Preflin.getInt(PAGE, 0));
                Preflin.putInt(PAGE, Preflin.getInt(PAGE, 0) + 1);
            }
        });

        // Listen on a key for default SharedPreferences
        Disposable disposable1 = Preflin.listenOn(PAGE).subscribe(new Consumer<String>() {
            @Override
            public void accept(String key) throws Exception {
                Log.d(TAG, "1. Value of " + key + " changed to " + Preflin.getInt(key));
                Toast.makeText(getApplicationContext(),
                        "1. Value of " + key + " changed to " + Preflin.getInt(key), Toast.LENGTH_SHORT).show();
            }
        });
        disposables.add(disposable1);

        // Listen on a key for named SharedPreferences
        Disposable disposable2 = Preflin.from(SP_SCORES).listenOn(USER1).subscribe(new Consumer<String>() {
            @Override
            public void accept(String key) throws Exception {
                Log.d(TAG, "2. Scores of " + key + " changed to " + Preflin.from(SP_SCORES)
                        .getDoubleList(key)
                        .toString());
            }
        });
        disposables.add(disposable2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Preflin.from(SP_SCORES).putDoubleList(USER1, Arrays.asList(3.3, 4.4, 5.5));
            }
        }, 1000);

        //Check Key exists or not
        Log.d(TAG, "isExist BOOK1=" + Preflin.isKeyExists(BOOK1));

        //Remove element by Key
        Log.d(TAG, "delete BOOK1=" + Preflin.deleteValue(BOOK1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (disposables != null) {
            disposables.dispose();
            disposables = null;
        }
    }

    static class Book implements Serializable {
        String title;
        int page;

        public Book() {
        }

        public Book(String title, int page) {
            this.title = title;
            this.page = page;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        @Override
        public String toString() {
            return "Book{" + "title='" + title + '\'' + ", page=" + page + '}';
        }
    }
}
