package com.example.fn.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdcsIntrospectionResponse {
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client_guid")
    @Expose
    private String clientGuid;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("iat")
    @Expose
    private Integer iat;
    @SerializedName("sub")
    @Expose
    private String sub;
    @SerializedName("aud")
    @Expose
    private List<String> aud = null;
    @SerializedName("iss")
    @Expose
    private String iss;
    @SerializedName("jti")
    @Expose
    private String jti;
    @SerializedName("tenant")
    @Expose
    private String tenant;
    @SerializedName("user_tz")
    @Expose
    private String userTz;
    @SerializedName("user_locale")
    @Expose
    private String userLocale;
    @SerializedName("user_displayname")
    @Expose
    private String userDisplayname;
    @SerializedName("user_tenantname")
    @Expose
    private String userTenantname;
    @SerializedName("user.tenant.name")
    @Expose
    private String userTenantName;
    @SerializedName("sub_mappingattr")
    @Expose
    private String subMappingattr;
    @SerializedName("client_tenantname")
    @Expose
    private String clientTenantname;
    @SerializedName("user_lang")
    @Expose
    private String userLang;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("gt")
    @Expose
    private Boolean gt;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientGuid() {
        return clientGuid;
    }

    public void setClientGuid(String clientGuid) {
        this.clientGuid = clientGuid;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public List<String> getAud() {
        return aud;
    }

    public void setAud(List<String> aud) {
        this.aud = aud;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getUserTz() {
        return userTz;
    }

    public void setUserTz(String userTz) {
        this.userTz = userTz;
    }

    public String getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(String userLocale) {
        this.userLocale = userLocale;
    }

    public String getUserDisplayname() {
        return userDisplayname;
    }

    public void setUserDisplayname(String userDisplayname) {
        this.userDisplayname = userDisplayname;
    }

    public String getUserTenantname() {
        return userTenantname;
    }

    public void setUserTenantname(String userTenantname) {
        this.userTenantname = userTenantname;
    }

    public String getUserTenantName() {
        return userTenantName;
    }

    public void setUserTenantName(String userTenantName) {
        this.userTenantName = userTenantName;
    }

    public String getSubMappingattr() {
        return subMappingattr;
    }

    public void setSubMappingattr(String subMappingattr) {
        this.subMappingattr = subMappingattr;
    }

    public String getClientTenantname() {
        return clientTenantname;
    }

    public void setClientTenantname(String clientTenantname) {
        this.clientTenantname = clientTenantname;
    }

    public String getUserLang() {
        return userLang;
    }

    public void setUserLang(String userLang) {
        this.userLang = userLang;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Boolean getGt() {
        return gt;
    }

    public void setGt(Boolean gt) {
        this.gt = gt;
    }
}
