package net.cokkee.nutrix.accptest.steps;

/**
 *
 * @author drupalex
 */
public abstract class NutrixAbstractSteps {

    private String serviceUrl = "http://localhost:8080/nutrix-app/ws/nutrix/api";

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String serviceUrl(String path) {
        StringBuilder url = new StringBuilder();
        if(serviceUrl != null) {
            url.append(serviceUrl);
            if (!serviceUrl.endsWith("/")) {
                url.append("/");
            }
        }
        url.append(path);
        return url.toString();
    }
}
