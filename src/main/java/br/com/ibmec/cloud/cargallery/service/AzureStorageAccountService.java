package br.com.ibmec.cloud.cargallery.service;

import com.azure.core.implementation.util.BinaryDataHelper;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class AzureStorageAccountService {
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=cargalleryimagestorage;AccountKey=qVcYBpRHH8pwPNqes2mGrGmWa1y7TaA/5auM1OE13OgYgYVnqjDkk9XNW2D1rFZjuj62cYPWzuZA+AStv65HgA==;EndpointSuffix=core.windows.net";
    public String uploadFileToAzure(String imageBase64) throws IOException {
        BlobContainerClient client = new BlobContainerClientBuilder()
                .connectionString(this.connectionString)
                .containerName("images")
                .buildClient();

        String fileName = UUID.randomUUID() + ".png";
        BlobClient blob = client.getBlobClient(fileName);

        byte[] image = Base64.getDecoder().decode(imageBase64);

        BinaryData binDataImage = BinaryData.fromBytes(image);

        blob.upload(binDataImage, true);

        return "https://cargalleryimagestorage.blob.core.windows.net/images/" + fileName;

    }
}
