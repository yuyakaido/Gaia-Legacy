package com.yuyakaido.android.blueprint.util;

import java.io.File;

import okhttp3.mockwebserver.MockResponse;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by yuyakaido on 2/16/16.
 */
public class ResponseUtil {

    private static final int HTTP_STATUS_CODE_200 = 200;
    private static final int HTTP_STATUS_CODE_500 = 500;

    public static MockResponse createMockResponse(File file) throws Exception {
        BufferedSource source = Okio.buffer(Okio.source(file));
        StringBuilder builder = new StringBuilder();
        while (!source.exhausted()) {
            builder.append(source.readUtf8Line());
        }

        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody(builder.toString());
        mockResponse.setResponseCode(HTTP_STATUS_CODE_200);

        return mockResponse;
    }

    public static MockResponse createInternalServerErrorMockResponse() throws Exception {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(HTTP_STATUS_CODE_500);
        return mockResponse;
    }

}
