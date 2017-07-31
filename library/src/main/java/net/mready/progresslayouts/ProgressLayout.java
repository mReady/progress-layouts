package net.mready.progresslayouts;

public interface ProgressLayout {

    void setLoading(boolean loading);

    boolean isLoading();

    void setLoadingMessage(String message);
}