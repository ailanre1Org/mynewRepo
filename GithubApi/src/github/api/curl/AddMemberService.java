package github.api.curl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AddMemberService { 

    
    public void updateTeamMaintainer(String userId, String teamId, String token, String requestMethod) {
        String data;

        try {
            
        	data = "{\"role\":\"maintainer\"}";
            // Disable cert validation
            disableCertificateValidation();

            HttpURLConnection con = (HttpURLConnection) new URL("https://api.github.com/teams/" + teamId +"/memberships/" + userId).openConnection();
            con.setRequestMethod(requestMethod);
            con.setRequestProperty("Authorization", "token " + token);
            con.setRequestProperty("Accept","*/*");
            con.setDoOutput(true);
            con.getOutputStream().write(data.getBytes("UTF-8"));

            int status = con.getResponseCode();
            System.out.println("Response code: " + status);
            System.out.println("Response: " + con.getResponseMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void disableCertificateValidation() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() { 
                return new X509Certificate[0]; 
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }
        }};

        // Ignore differences between given hostname and certificate hostname
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {
            // Do nothing
        }
    }

	public void removeFromOrganization(String userId, String orgId, String token) {
		
		try {
            // Disable cert validation
            disableCertificateValidation();

            HttpURLConnection con = (HttpURLConnection) new URL("https://api.github.com/orgs/" + orgId +"/memberships/" + userId).openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Authorization", "token " + token);
            con.setRequestProperty("Accept","*/*");
            con.setDoOutput(true);
            //con.getOutputStream().write(data.getBytes("UTF-8"));

            int status = con.getResponseCode();
            System.out.println("Response code: " + status);
            System.out.println("Response: " + con.getResponseMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}