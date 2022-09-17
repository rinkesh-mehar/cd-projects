import { GeneralWeatherParams } from './GeneralWeather-params';
import { GeneralPoi } from './GeneralPoi';
import { GeneralModeOfPayment } from './GeneralModeOfPayment';
import { GeneralUom } from './GeneralUom';
export class PageGeneralWeatherParams {
    content : GeneralWeatherParams[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
