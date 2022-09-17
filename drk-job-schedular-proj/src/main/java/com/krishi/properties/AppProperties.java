package com.krishi.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${media.blob.url}")
    private String mediaUrl;

    @Value("${files-rightCertificate.basePath}")
    private String rightCertificateBasePath;

    @Value("${exist.farmer.api}")
    private String existFarmerApi;

//    @Value("${azure.storage.environment}")
    private String absEnv = "drk";

    @Value("${cdt.api-key}")
    private String cdtApiKey;

//    @Value("${mailer.host}")
    private String mailerHost;

//    @Value("${mailer.port}")
    private String mailerPort;

//    @Value("${mailer.address}")
    private String mailAddress;

//    @Value("${mailer.from-mail-id}")
    private String fromMailId;

//    @Value("${mailer.password}")
    private String mailPassword;

//    @Value("${mailer.username}")
    private String mailUserName;

//    @Value("${mailer.mail-to}")
    private String mailTo;

    @Value("${aws.s3.path}")
    private String userDirPath;

    @Value("${file-manager.url}")
    private String fileManagerURL;

    public String getFileManagerURL()
    {
        return fileManagerURL;
    }

    public void setFileManagerURL(String fileManagerURL)
    {
        this.fileManagerURL = fileManagerURL;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public Object getMailerHost() {
        return mailerHost;
    }

    public Object getMailerPort() {
        return mailerPort;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setFromMailId(String fromMailId) {
        this.fromMailId = fromMailId;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public void setMailUserName(String mailUserName) {
        this.mailUserName = mailUserName;
    }

    public String getCdtApiKey() {
        return cdtApiKey;
    }

    public void setCdtApiKey(String cdtApiKey) {
        this.cdtApiKey = cdtApiKey;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getRightCertificateBasePath() {
        return rightCertificateBasePath;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getExistFarmerApi() {
        return existFarmerApi;
    }

    public void setExistFarmerApi(String existFarmerApi) {
        this.existFarmerApi = existFarmerApi;
    }

    public String getAbsEnv()
    {
        return absEnv;
    }

    public void setAbsEnv(String absEnv)
    {
        this.absEnv = absEnv;
    }

    public String getMailHost() {
        return mailerHost;
    }

    public String getMailPort() {
        return mailerPort;
    }

    public void setMailerHost(String mailerHost) {
        this.mailerHost = mailerHost;
    }

    public void setMailerPort(String mailerPort) {
        this.mailerPort = mailerPort;
    }

    public String getMailUserName() {
        return mailUserName;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getFromMailId() {
        return fromMailId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getUserDirPath()
    {
        return userDirPath;
    }

    public void setUserDirPath(String userDirPath)
    {
        this.userDirPath = userDirPath;
    }
}
