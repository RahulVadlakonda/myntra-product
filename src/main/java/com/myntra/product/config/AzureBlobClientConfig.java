package com.myntra.product.config;

import com.azure.storage.blob.BlobClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobClientConfig {

    @Value("${spring.cloud.azure.storage.blob.account-name}")
    private String storageAccountName;
    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;
    @Value("${spring.cloud.azure.storage.blob.account-key}")
    private String accountKey;

    public String storageConnectionString = "";

    @Bean
    public BlobClientBuilder containerClient(){
        storageConnectionString = storageConnectionString + "DefaultEndpointsProtocol=https;" +
                "AccountName=" + storageAccountName +
                ";AccountKey=" + accountKey +
                ";EndpointSuffix=core.windows.net";
        System.out.println("storageConnectionString" + storageConnectionString);
        BlobClientBuilder clientBuilder = new BlobClientBuilder();
        clientBuilder.connectionString(storageConnectionString);
        clientBuilder.containerName(containerName);
        return clientBuilder;
    }

}
