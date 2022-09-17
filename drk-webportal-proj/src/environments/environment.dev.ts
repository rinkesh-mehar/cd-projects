// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
export const baseAPI = {
  URL: 'https://drkrishi-dev.cropdata.in/gateway/'
}
export const environment = {
  production: false,
  // baseURL: 'http://10.44.13.224:24089/usermanagement-dev/',
  baseURL: baseAPI.URL + 'usermanagement-dev/',
  prmURL: baseAPI.URL + 'prm/',
  // rltURL: 'http://14.99.175.107:24089/rlmrlt-dev/',
  rltURL: baseAPI.URL + 'rlt/',
  // cctcURL: 'http://14.99.175.107:24089/cctc-dev/',
  cctcURL: baseAPI.URL + 'cctc/',
  kmlQaURL: baseAPI.URL + 'clqa/',
  caseMoURL: baseAPI.URL + 'cm/',
  // kycQaURL: 'http://14.99.175.107:24089/clqa-dev/',
  kycQaURL:  baseAPI.URL + 'clqa/',
  ImageQaURL:  baseAPI.URL + 'clqa/',
  VehicleMasterURL: baseAPI.URL + 'vehicle/',
  rightInfoURL:  baseAPI.URL + 'fls/',
  // GSTMURL: baseAPI.URL + 'rlt/',
  GSTMURL: 'https://api-dev.cropdata.in/drk/v1.0/',
  QPB: 'https://api-dev.cropdata.in/drk/v1.0/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.



