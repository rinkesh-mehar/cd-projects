
export interface VechicleSchedule {
    villages: TileVillage[];
    scheduledate: string;
    vinNumber: string[];
}

export interface TileVillage{
	villageCode:number;
	villageName:string;
}

export interface VechicleScheduleForm{
    regionId: number;
    schedulevillages:TileVillage[];
    vinnumber:string;
    scheduledate: string;
}

export interface statusMessage{
    status:string;
    message:string;
}
