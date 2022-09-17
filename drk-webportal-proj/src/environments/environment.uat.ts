export const baseAPI = {
  URL: 'https://drkrishi-ts-uat.cropdata.tk/'
}

export const environment = {
  production: false,
 /* baseURL: '',
  prmURL: '',
  rltURL: '',
  cctcURL: '',
  kmlQaURL: '',
  caseMoURL: '',
  kycQaURL: '',
  ImageQaURL: '',
  VehicleMasterURL: '',
  rightInfoURL: '',
  GSTMURL: '',*/

  // baseURL: 'http://10.44.13.224:24089/usermanagement-dev/',
  baseURL: baseAPI.URL + 'usermanagement/',
  prmURL: baseAPI.URL + 'drk-prm/',
  // rltURL: 'http://14.99.175.107:24089/rlmrlt-dev/',
  rltURL: baseAPI.URL + 'rlt/',
  // cctcURL: 'http://14.99.175.107:24089/cctc-dev/',
  cctcURL: baseAPI.URL + 'cctc/',
  kmlQaURL: baseAPI.URL + 'clqa/',
  caseMoURL: baseAPI.URL + 'cm/',
  // kycQaURL: 'http://14.99.175.107:24089/clqa-dev/',
  /*  kycQaURL:  baseAPI.URL + 'clqa/',
    ImageQaURL:  baseAPI.URL + 'clqa/',*/
  kycQaURL:  baseAPI.URL + 'clqa/',
  ImageQaURL:  baseAPI.URL + 'clqa/',
  VehicleMasterURL: baseAPI.URL + 'vehicle/',
  rightInfoURL:  baseAPI.URL + 'fls/',
  GSTMURL: baseAPI.URL + 'rlt/',
  QPB: 'https://api-uat.cropdata.tk/drk/v1.0/'
}
