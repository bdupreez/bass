package net.briandupreez.bass;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Controller
public class ConfigController {

    @Value("${bass.oauth.authserver}")
    private String authserver;
    @Value("${bass.oauth.callbackUrl}")
    private String callbackUrl;
    @Value("${bass.oauth.implicitGrantUrl}")
    private String implicitGrantUrl;
    @Value("${bass.oauth.clientId}")
    private String clientId;
    @Value("${bass.oauth.scopes}")
    private String scopes;
    @Value("${resourceServerUrl}")
    private String resourceServerUrl;

    @GetMapping(value = "/new-config/")
    @CrossOrigin
    public UIConfig getAppConfig() throws UnknownHostException {

        final String host = determineHost();

        final UIConfig appConfig = new UIConfig();
        appConfig.setAuthServer(authserver);
        appConfig.setOAuthCallbackUrl(callbackUrl);
        appConfig.setClientId(clientId);
        appConfig.setImplicitGrantUrl(authserver + implicitGrantUrl
            .replace("__callbackUrl__", callbackUrl)
            .replace("__clientId__", clientId)
            .replace("__scopes__", scopes));
        appConfig.setResourceServerUrl(resourceServerUrl);
        return appConfig;
    }

    private String determineHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }


    @RequestMapping(value = "**/{[path:[^\\.]*}")
    @CrossOrigin
    public String redirectAswell() {
        return "forward:/";
    }
}
