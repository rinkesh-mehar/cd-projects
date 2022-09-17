

export const baseAPI = {
  URL: 'http://api.cropdata.tk/'
}

export const environment = {
  production: true,
  apiUrl: baseAPI.URL + 'master-data',
  dashboardApiUrl : baseAPI.URL + 'dashboard-api',
  notificationApiUrl: baseAPI.URL + 'notification',
  ydcApiUrl: baseAPI.URL + 'ydc-api',
  // flipbookApiURL: baseAPI.URL + 'flipbook',
  flipbookApiURL: baseAPI.URL + 'drk-tools',
  warehouseApiUrl: baseAPI.URL + 'warehouse',
  weatherApiUrl: baseAPI.URL + 'weather',
  userKycImgUrl: baseAPI.URL + "warehouse/public/images/govt-doc/",
  warehouseImgUrl: baseAPI.URL + "warehouse/public/images/warehouse/",
  logisticHubApiUrl: '',
  benchmarkImagesURL: baseAPI.URL + 'file-manager/get-file?id=',
  // drkBugFixUrl: 'http://143.10.0.11:8099/get/drk-error-log?',
  azureDevopsUrl: '',

  pageSize: 20,
  allowOrigin: 'http://master.cropdatademo.tk',
  imageResizeWidth: 320,
  imageResizeHeight: 220,
  preserveImageAspectRatio: true
};
