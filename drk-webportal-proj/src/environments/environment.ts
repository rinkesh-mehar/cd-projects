// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
export const baseAPI = {
  URL: 'https://drkrishi-ts-uat.cropdata.tk/'
}
export const environment = {
  production: false,
  // baseURL: 'http://10.44.13.224:24089/usermanagement-dev/',
  baseURL: baseAPI.URL + 'usermanagement/',
  prmURL: baseAPI.URL + 'prm/',
  // rltURL: 'http://14.99.175.107:24089/rlmrlt-dev/',
  rltURL:  baseAPI.URL + 'rlt/',
  // cctcURL: 'http://14.99.175.107:24089/cctc-dev/',
  cctcURL: baseAPI.URL + 'cctc/',
//   kmlQaURL: 'http://14.99.175.107:24089/clqa-dev/',
  kmlQaURL: baseAPI.URL + 'clqa/',
  caseMoURL: 'http://14.99.175.107:24089/monitorcash-dev/',
  // kycQaURL: 'http://14.99.175.107:24089/clqa-dev/',
  kycQaURL: baseAPI.URL + 'clqa/',
  // ImageQaURL: 'http://14.99.175.107:24089/clqa-dev/',
  ImageQaURL: baseAPI.URL + 'clqa/',
  // VehicleMasterURL: 'http://14.99.175.107:24089/vehiclemonitoring-dev/',
  VehicleMasterURL: baseAPI.URL + 'vehicle/',
  rightInfoURL: 'http://14.99.175.107:24089/fls-dev/',
  GSTMURL: 'http://192.168.0.43:9129/drk-rlt/',
  QPB: 'https://api-uat.cropdata.tk/drk/v1.0/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.



