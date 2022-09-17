export interface KmlDetails{
    cropName:    string;
    correction:  number;
    area:        number;
    fileName:    string;
  kmlUrl:    string;
  coordinates: Coordinates;
}

export interface Coordinates {
    xMin: number;
    xMax: number;
    yMin: number;
    yMax: number;
}
export interface ResponseData {
    statusCode:string;
    message:string;
    data:KmlDetails;
}
export interface CoordinatesList {
  lng: string;
  lat: string;
}
