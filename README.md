
# Preflin

Preflin is a wrapper over the SharedPreference class in Android. It supports storing objects and list objects other than the standard primitives while decreasing the boiler plate code. It also enables listening for changes on specific keys on a SharedPreference with RxJava support.

How to Use
-------

#### Usage

First initialize the Preflin library in your Application class.

```java
public class Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Preflin.init(this);
    }
}
```

Then, use the Preflin class to store and access data in SharedPreferences.

```java
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle inState) {
        // Default SharedPreferences
        Preflin.putString("title", "Deep Learning");
        Preflin.putDouble("page", 300);
        Preflin.putObject("book", new Book("Java", 300));
        Preflin.putObjectList("book_list", Arrays.asList(new Book("Java", 250),new Book("C++", 400)));

        Double page = Preflin.getDouble("page");
        Book javaBook = Preflin.getObject("book", Book.class);
        List<Book> bookList = Preflin.getObjectList(BOOK_LIST, Book.class);

        // Named SharedPreferences can also be used
        Preflin.from("Scores").putIntList("level_1", [60, 20, 80, 25, 30]);

        List<Integer> scores = Preflin.from("Scores").getIntList("level_1");
    }
}
```

Listen on changes to specific key in a SharedPreference

```java
// Listen on a key in default SharedPreferences
Preflin.listenOn("page").subscribe(new Action1<String>() {
    @Override
    public void call(String key) {
        Log.d(TAG, "1. Value of " + key + " changed to " + Preflin.getInt(key));
    }
});

// Listen on a key in named SharedPreferences
Preflin.from("scores").listenOn("user1").subscribe(new Action1<String>() {
    @Override
    public void call(String key) {
        Log.d(TAG, "2. Scores of " + key + " changed to " + Preflin.from(SP_SCORES).getDoubleList(key).toString());
    }
});
```

Do note that these subscriptions are standard RxJava subscriptions, hence they need to be unsubscribed, failing which will lead to leaking of subscriptions

Create Setter And getter and constructor for jackson

License
-------

    Copyright 2018 - 2019 Rasoul Samani

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
