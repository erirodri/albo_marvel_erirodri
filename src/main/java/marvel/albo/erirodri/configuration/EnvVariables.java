package marvel.albo.erirodri.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category configuration
 *
 * Class to have access to .properties variables
 * --------------------------------------------------------------
 */
@ConfigurationProperties(prefix="marvel.api")
@Configuration
public class EnvVariables {
    private String gateway;
    private String apiKey;
    private String ts;
    private String hash;

    public EnvVariables() {
        // Environment Variables
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
