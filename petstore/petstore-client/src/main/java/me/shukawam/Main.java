package me.shukawam;

import me.shukawam.oci.petstore.ApiClient;
import me.shukawam.oci.petstore.ApiException;
import me.shukawam.oci.petstore.Configuration;
import me.shukawam.oci.petstore.api.PetsApi;
import me.shukawam.oci.petstore.model.Pet;


public class Main {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://pdjk3ikdxkuktzcrvalmyu2ftq.apigateway.ap-tokyo-1.oci.customer-oci.com/api");

        PetsApi apiInstance = new PetsApi(defaultClient);
        try {
            System.out.println("*** GET /pets ***");
            apiInstance.listPets(10).forEach(pet -> System.out.println(pet.getId() + " " + pet.getName()));
            System.out.println("*** POST /pets ***");
            apiInstance.createPets();
            System.out.println("Created pets.");
            System.out.println("*** GET /pets/{petId} ***");
            Pet pet = apiInstance.showPetById("1");
            System.out.println(pet.getId() + " " + pet.getName());
        } catch (ApiException e) {
            System.err.println("Exception when calling PetsApi");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
