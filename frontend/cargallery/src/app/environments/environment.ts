import { HttpHeaders } from "@angular/common/http";

export const environment = {
    apiUrlBase: "https://cargallery-ibmec-202401.azure-api.net",
    headers: new HttpHeaders({"ocp-apim-subscription-key":"ae6332ef980c482996f4da23decdcc90"})
}