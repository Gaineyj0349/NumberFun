/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gainwise.numberfun.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String NUMBERS_BASE_URL =
            "http://numbersapi.com";





  //TODO param query here is the type (date,trivia,year)
    public static URL buildUrl(String numberSearchQuery, int PARAM_QUERY) {
        String type = getType(PARAM_QUERY);
        Uri builtUri = Uri.parse(NUMBERS_BASE_URL).buildUpon()
                .appendPath(numberSearchQuery)
                .appendPath(type)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getType(int pos){
        String str;
        switch (pos){

            case 0:
                str = "trivia";

                break;

            case 1:
                str = "date";
                break;

            case 2:
                str = "year";
                break;

            case 3:
                str = "math";
                break;
            default:
                str = "";
                break;
        }
        return str;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally
         {
            urlConnection.disconnect();
        }
    }
}