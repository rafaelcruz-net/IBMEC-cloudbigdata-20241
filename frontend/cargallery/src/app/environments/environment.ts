import { HttpHeaders } from "@angular/common/http";

export const environment = {
    apiUrlBase: "http://localhost:8080",
    headers: new HttpHeaders({"ocp-apim-subscription-key":"ae6332ef980c482996f4da23decdcc90"})
}