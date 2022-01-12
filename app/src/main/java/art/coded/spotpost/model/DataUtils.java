package art.coded.spotpost.model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import art.coded.spotpost.model.entity.Event;
import art.coded.spotpost.model.entity.Session;
import art.coded.spotpost.model.entity.SessionSet;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The actions required to fetch and format raw Elements
 */
public class DataUtils {

    private static final String LOG_TAG = DataUtils.class.getSimpleName();
    private static final String DEFAULT_VALUE_STR = "";
    private static final int DEFAULT_VALUE_NUM = -1;

    public static void spotPost(
            String baseUrl, @Nullable String getPath, @Nullable String postPath) {
        String getResponse = getRequest(baseUrl, getPath);
        List<Event> events = parseEvents(getResponse);
        List<Session> sessions = makeSessions(events);
        List<SessionSet> sessionSets = makeSessionSets(sessions);
        String formattedSessionSets = formatSessionSets(sessionSets);
        postRequest(baseUrl, postPath, formattedSessionSets);
    }

    public static String getRequest (
            String baseUrl, @Nullable String getPath) {

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendPath(getPath)
                .build();
        String url = getUrl(uri).toString();
        Log.d(LOG_TAG, url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response responses = null;
        String responseStr = "";

        try {
            responses = client.newCall(request).execute();
            responseStr = responses.body().string();
            Log.v(LOG_TAG, "Get response: " + responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    public static void postRequest(String baseUrl, @Nullable String getPath, String data) {

        Uri uri = Uri.parse(baseUrl).buildUpon().appendPath(getPath).build();
        String url = getUrl(uri).toString();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody post = RequestBody.create(mediaType, data);

        Request request = new Request.Builder()
                .url(url)
                .post(post)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        Response responses = null;
        String responseBody = "";

        try {
            responses = client.newCall(request).execute();
            responseBody = responses.body().string();
            Log.v(LOG_TAG, "Post response: " + responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Event> parseEvents(String response) {
        List<Event> events = null;
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray JSONArray = jsonObject.getJSONArray("events");
            events = new ArrayList<>(JSONArray.length());

            for (int i = 0; i < JSONArray.length(); i++) {
                JSONObject JSONObject = JSONArray.getJSONObject(i);
                Event event = parseEvent(JSONObject);
                events.add(event);
            }
        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
            Log.e(LOG_TAG, jsonEx.getMessage());

        } return events;
    }

    // Parses single raw Element
    public static Event parseEvent(JSONObject jsonObject) throws JSONException {

        String visitorId = nullToDefaultStr(jsonObject.getString(Event.KEY_VISITORID));
        String url = nullToDefaultStr(jsonObject.getString(Event.KEY_URL));
        Long timestamp = nullToDefaultNum(jsonObject.getLong(Event.KEY_TIMESTAMP)).longValue();

        Event event = new Event();
        event.setVisitorId(visitorId);
        event.setUrl(url);
        event.setTimestamp(timestamp);
        event.setEventId(event.hashCode());
        return event;
    }

    public static String formatSessionSets(List<SessionSet> sessionSets) {
        JSONObject formattedSessions = new JSONObject();
        try {
            JSONArray ssArray = new JSONArray();
            for (SessionSet ss : sessionSets) {
                JSONObject ssObject = new JSONObject();
                List<Session> sessions = ss.getSessions();
                for (Session s : sessions) {
                    JSONArray sArray = new JSONArray();
                    JSONObject sObject = new JSONObject();
                    sObject.put(Session.KEY_STARTTIME, s.getStartTime());
                    sObject.put(Session.KEY_DURATION, s.getDuration());
                    sObject.put(Session.KEY_USERID, s.getUserId());
                    for (String p : s.getPages()) {
                        JSONObject pObject = new JSONObject();
                        pObject.put(Session.KEY_PAGES, p);
                    }
                    sObject.put(Session.KEY_PAGES, s.getPages());
                    sArray.put(sObject);
                    ssObject.put(ss.getUserId(), sArray);
                    ssArray.put(ssObject);
                }
            }
            formattedSessions.put("sessionsByUserId", ssArray);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return formattedSessions.toString();
    }

    public static List<SessionSet> makeSessionSets(List<Session> sessions) {

        List<SessionSet> sessionSets = new ArrayList<>();
        for (Session s : sessions) {
            boolean userAdded = false;
            for (SessionSet ss : sessionSets) {
                if (s.getUserId().equals(ss.getUserId())) {
                    userAdded = true;
                    boolean sessionAdded = false;
                    for (Session innerSession : ss.getSessions()) {
                        if (innerSession.equals(s)) { sessionAdded = true; break; }
                    }
                    if (!sessionAdded) {
                        ss.getSessions().add(s);
                    }
                }
            }
            if (!userAdded) {
                SessionSet newSet = new SessionSet();
                newSet.setUserId(s.getUserId());
                newSet.getSessions().add(s);
                sessionSets.add(newSet);
            }
        } return sessionSets;
    }

    public static List<Session> makeSessions(List<Event> events) {

        List<Session> sessions = new ArrayList<>();
        if (events == null) return sessions;
        for (Event e : events) {
            boolean pageAdded = false;
            for (Session s : sessions) {
                if (e.getVisitorId().equals(s.getUserId())
                && TimeUnit.MILLISECONDS.toMinutes(Math.abs(s.getStartTime() - e.getTimestamp())) <= 10) {
                    if (e.getTimestamp() < s.getStartTime()) s.setStartTime(e.getTimestamp());
                    s.getPages().add(e.getUrl());
                    pageAdded = true;
                    break;
                }
            }
            if (pageAdded) continue;
            Session s = new Session();
            s.setUserId(e.getVisitorId());
            s.setStartTime(e.getTimestamp());
            s.setDuration(0);
            s.setPages(new ArrayList<>());
            s.getPages().add(e.getUrl());
            sessions.add(s);
        } return sessions;
    }

    // Translates built Uri into URL for opening an HttpURLConnection
    public static URL getUrl(Uri uri) {
        URL url = null;
        try {
            String urlStr = URLDecoder.decode(uri.toString(), "UTF-8");
            url = new URL(urlStr);
            Log.v(LOG_TAG, String.format("Fetch URL: %s", url.toString()));
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Log.e(LOG_TAG, String.format("Unable to convert Uri of %s to URL:", e.getMessage()));
        }
        return url;
    }

    // Translates null to default String to prevent NPExceptions on accessing certain Elements
    public static String nullToDefaultStr(String str) {
        return (str.equals("null")) ? DEFAULT_VALUE_STR : str;
    }

    // Translates null to default Integer to prevent NPExceptions on accessing certain Elements
    public static Number nullToDefaultNum(Number n) {
        return (n == null) ? DEFAULT_VALUE_NUM : n;
    }
}