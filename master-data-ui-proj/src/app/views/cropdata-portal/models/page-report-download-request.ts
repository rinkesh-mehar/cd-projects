import { ReportDownloadRequest } from "./report-download-request";

export class PageReportDownloadRequest {
    content: ReportDownloadRequest[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
