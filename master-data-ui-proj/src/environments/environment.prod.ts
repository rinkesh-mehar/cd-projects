

export const baseAPI = {
  URL: 'https://api-ts.cropdata.in/'
}

export const environment = {
  production: true,
  apiUrl: baseAPI.URL + 'master-data',
  dashboardApiUrl : baseAPI.URL + 'dashboard-api',
  notificationApiUrl: baseAPI.URL + '',
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
  azureDevopsUrl: '',
  gstmModelURL: baseAPI.URL + 'gstm-model',

  pageSize: 20,
  allowOrigin: 'http://master-ts.cropdata.in',
  imageResizeWidth: 320,
  imageResizeHeight: 220,
  preserveImageAspectRatio: true
};
