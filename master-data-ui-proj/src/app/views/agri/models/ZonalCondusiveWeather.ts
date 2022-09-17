export class ZonalCondusiveWeather {
    id: number;
    name: string;
    stressId: number;
    stress: string;
    commodity: string;
    commodityId: number;
    stressType: string;
    stressTypeId: number;
    primaryWeatherParameterId: number;
    primaryWeatherParameter: number;
    primarySpecificationAverage: number;
    primarySpecificationLower: number;
    primarySpecificationUpper: number;
    // primaryStressDurationUpper: number;
    primaryStressDurationPast: number;
    primaryStressDurationFuture: number;
    secondaryWeatherParameterId: number;
    secondaryWeatherParameter: number;
    secondarySpecificationAverage: number;
    secondarySpecificationLower: number;
    secondarySpecificationUpper: number;
    // secondaryStressDurationUpper: number;
    secondaryStressDurationPast: number;
    secondaryStressDurationFuture: number;
    status: string;
    description: string;
    zonalCommodityId : number;
    zonalVarietyId: Number;
    zonalCommodity : string;
    zcSowingWeekStart : number;
    zcSowingWeekEnd : number;
    zonalVariety : string;
    zvSowingWeekStart : number;
    zvSowingWeekEnd : number;
    stateCode : number;
    stateName :string;
    aczName :string;
    state :string;
    phenophase :string;
    weatherParameter: string;;
	conduciveDuration: number;
	relaxingDuration: number;
	lower: string;
	upper: string;
}