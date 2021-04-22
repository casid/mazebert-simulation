package com.mazebert.simulation;

import com.mazebert.simulation.replay.StreamReplayReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.net.ssl.*;
import java.net.URL;
import java.security.cert.X509Certificate;

/**
 * How to use
 * - Enter game id obtained from dsync report
 * - Run check method, this should log a dsync error
 * - Copy the dsync turn and insert as turn parameter
 */
public class DsyncDebugger {

    @BeforeAll
    static void beforeAll() {
        trustAllCertificates();
    }

    @Test
    void check() throws Exception {
        checkGame("47286ff6-98d0-44d9-967c-768d5c281fdb-45838", 10189, 24);
    }

    @SuppressWarnings("SameParameterValue")
    private void checkGame(String game, int turn, int version) throws Exception {
        int separatorIndex = game.lastIndexOf('-');
        String gameId = game.substring(0, separatorIndex);
        String playerId = game.substring(separatorIndex + 1);

        try (StreamReplayReader replayReader = new StreamReplayReader(new URL("https://mazebert.com/rest/game/download?gameId=" + gameId + "&playerId=" + playerId).openStream(), version)) {
            new SimulationValidator().validate(version, replayReader, null, null, turn - 1, true);
        }
    }

    private static void trustAllCertificates() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
