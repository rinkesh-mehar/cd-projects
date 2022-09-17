

export const baseAPI = {
  URL: 'https://api-uat.cropdata.in/',
}


export const environment = {
  production: true,
  apiUrl: baseAPI.URL + 'master-data',
  dashboardApiUrl : baseAPI.URL + 'dashboard-api',
  // notificationApiUrl: baseAPI.URL + 'notification',
  notificationApiUrl: '',
  ydcApiUrl: baseAPI.URL + 'ydc-api',
  // flipbookApiURL: baseAPI.URL + 'flipbook',
  flipbookApiURL: baseAPI.URL + 'drk-tools',
  warehouseApiUrl: baseAPI.URL + 'warehouse',
  weatherApiUrl: baseAPI.URL + 'weather',
  userKycImgUrl: baseAPI.URL + "warehouse/public/images/govt-doc/",
  warehouseImgUrl: baseAPI.URL + "warehouse/public/images/warehouse/",
  apkVersionApiURL: baseAPI.URL + 'mobile-api/apk-versioning',
  benchmarkImagesURL: baseAPI.URL + 'file-manager/get-file?id=',
  logisticHubApiUrl: baseAPI.URL + 'logistic',
  // drkBugFixUrl: 'http://143.10.0.11:8099/get/drk-error-log?',
  azureDevopsUrl: '',
  gstmModelURL: baseAPI.URL + 'gstm-model',

  pageSize: 20,
  allowOrigin: 'https://master-uat.cropdata.in',
  imageResizeWidth: 320,
  imageResizeHeight: 220,
  preserveImageAspectRatio: true
};
