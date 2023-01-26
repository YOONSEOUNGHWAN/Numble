package com.numble.carot.common.aws;

public class ExternalURLs {

    /**
     * NHN Cloud 인증 토큰 발급 주소
     */
    public static final String NHNAuth = "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";

    /**
     * Object Storage Access URL
     */
    public static String NHNObjectStorage(String storageAccount,
                                          String containerName,
                                          String objectName) {
        return String.format("%s/%s/%s", NHNObjectStorage(storageAccount), containerName, objectName);
    }

    public static String NHNObjectStorage(String storageAccount) {
        return String.format("https://api-storage.cloud.toast.com/v1/%s", storageAccount);
    }

}