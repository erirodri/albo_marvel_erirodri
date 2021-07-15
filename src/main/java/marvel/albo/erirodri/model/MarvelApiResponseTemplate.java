package marvel.albo.erirodri.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category model
 *
 * Model to collect data response from API Marvel
 * --------------------------------------------------------------
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarvelApiResponseTemplate {
    private int code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private String etag;
    private DataResponseTemplate data;

    public MarvelApiResponseTemplate() {
        // Constructor ...
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public DataResponseTemplate getData() {
        return data;
    }

    public void setData(DataResponseTemplate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarvelApiResponseTemplate{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", attributionText='" + attributionText + '\'' +
                ", attributionHTML='" + attributionHTML + '\'' +
                ", etag='" + etag + '\'' +
                ", data=" + data +
                '}';
    }

}


