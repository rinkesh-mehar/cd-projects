// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environmentAPI = {
    // baseAPIURL: 'http://192.168.0.49:8082'
    // baseAPIURL: 'http://api.cropdata.tk/'

    baseAPIURL: 'http://localhost:7085'
    // baseAPIURL: 'https://api-tsuat.cropdata.in/master-data'

}
export const environment = {
    production: false,
    // apiUrl: environmentAPI.baseAPIURL+'master-data',
    apiUrl: environmentAPI.baseAPIURL,
    //apiUrl: 'http://192.168.0.48:8080',
    // notificationApiUrl: environmentAPI.baseAPIURL + '',
    notificationApiUrl: '',
    ydcApiUrl: environmentAPI.baseAPIURL + 'ydc',
    warehouseApiUrl: 'http://192.168.0.252:8762/',
    weatherApiUrl: 'http://192.168.0.252:8762/',
    dashboardApiUrl: environmentAPI.baseAPIURL + 'dashboard-api',
    logisticHubApiUrl: 'http://localhost:8083',
    // flipbookApiURL: environmentAPI.baseAPIURL + 'drk-tools',
    flipbookApiURL: 'http://localhost:7078',
    apkVersionApiURL: environmentAPI.baseAPIURL + 'mobile-api/apk-versioning',

    benchmarkImagesURL: environmentAPI.baseAPIURL + 'file-manager/get-file?id=',

    userKycImgUrl: 'http://192.168.0.252:8762/' + "/public/images/govt-doc/",
    warehouseImgUrl: 'http://192.168.0.252:8762/' + "/public/images/warehouse/",

    // drkBugFixUrl: 'http://192.168.0.64:8099/' + 'get/drk-error-log?',
    azureDevopsUrl: 'http://192.168.0.50:8086/azure',
    gstmModelURL: 'http://192.168.0.64:8099',

    pageSize: 20,
    allowOrigin: 'http://localhost:4200',
    imageResizeWidth: 320,
    imageResizeHeight: 220,
    preserveImageAspectRatio: true
};
