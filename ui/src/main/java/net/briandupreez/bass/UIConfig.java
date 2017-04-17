package net.briandupreez.bass;


public class UIConfig {
    private String OAuthCallbackUrl;
    private String implicitGrantUrl;
    private String resourceServerUrl;
    private String authServer;
    private String clientId;

    public void setOAuthCallbackUrl(final String OAuthCallbackUrl) {
        this.OAuthCallbackUrl = OAuthCallbackUrl;
    }

    public String getOAuthCallbackUrl() {
        return OAuthCallbackUrl;
    }


    public void setImplicitGrantUrl(final String implicitGrantUrl) {
        this.implicitGrantUrl = implicitGrantUrl;
    }

    public String getImplicitGrantUrl() {
        return implicitGrantUrl;
    }


    public String getResourceServerUrl() {
        return resourceServerUrl;
    }

    public void setResourceServerUrl(final String resourceServerUrl) {
        this.resourceServerUrl = resourceServerUrl;
    }


    public void setAuthServer(final String authServer) {
        this.authServer = authServer;
    }

    public String getAuthServer() {
        return authServer;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
