package com.phaiecobyte.pos.backend.media.service;

import java.io.InputStream;

public interface MediaStorage{
    void save(InputStream inputStream, String path, String contentType);
    InputStream load(String path);
    void delete(String path);
}

