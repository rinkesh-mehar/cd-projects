CropData Portal APIs
----------------------------------------------------------------------------
###For News:

- [GET] Get all news data with pagination -> http://localhost:8083/site/news/list?page=0&size=20&searchText=""
- [GET] Get news data with id -> http://localhost:8083/site/news/1
- [GET] Get all news data without pagination -> http://localhost:8083/site/news

###For Reports:

- [GET] Get all reports data with pagination -> http://localhost:8083/site/reports/list?page=0&size=20&searchText=""
- [GET] Get reports data with id -> http://localhost:8083/site/reports/1
- [GET] Get all reports data without pagination -> http://localhost:8083/site/reports

###For JobApplication:

- [GET] Get job-application data with pagination -> http://localhost:8083/site/job-application/paginatedList?page=0&size=20&searchText=""
- [GET] Get job-application without pagination -> http://localhost:8083/site/job-application/list
- [POST] post job-application -> http://localhost:8083/site/apply-job/5

###For Opportunities:

- [GET] Get job-application data with pagination -> http://localhost:8083/site/opportunities/paginatedList?page=0&size=20&searchText=""
- [GET] Get job-application without pagination -> http://localhost:8083/site/opportunities/list

###For Market Price:

- [GET] Get market price data with pagination -> http://localhost:8083/site/ticker/getMarketPriceListPaginated?page=0&size=20&searchText=""
- [GET] Get market price data without pagination -> http://localhost:8083/site/ticker/getMarketPriceList

###For Commodity Stress:

- [GET] Get commodity stress data with pagination -> http://localhost:8083/site/ticker/getCommodityStressListPaginated?page=0&size=20&searchText=""
- [GET] Get market price data without pagination -> http://localhost:8083/site/ticker/getCommodityStressList

###For Winner Price:

-[GET] Get Winner market price -> http://localhost:8080/site/market?Id=2

###For Market Records:

-[GET] Get market records -> http://localhost:8080/site/market-record?Id=3

###For Trading view:

-[GET] Get Treading view List -> http://localhost:8080/site/treading/view?stateCode=24&districtCode=5&commodityID=2&pricingAgriVarietyID=59&marketID=4742

###For RL Users:

-[GET] Get Get RL User Status Info -> http://localhost:8080/site/rluser/get-status/90
-[POST] Register RL User -> http://localhost:8080/site/rluser/register
