package ru.practicum.laterapp.item;

import java.time.Instant;

public interface UrlMetaDataRetriever {
    UrlMetadata retrieve(String url);

    interface UrlMetadata {
        String getNormalUrl();
        String getResolvedUrl();
        String getMimeType();
        String getTitle();
        boolean isHasImage();
        boolean isHasVideo();
        Instant getDateResolved();
    }
}
