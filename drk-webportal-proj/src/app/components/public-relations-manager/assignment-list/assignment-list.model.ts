
export interface AssignMentListModel {

    "week": number,
    "weekrange": string,
    "month": number,
    "year": number,
    "useraccordion": Useraccordion[]
}


export interface Useraccordion {
    "prsId":number,
    "user": string,
    "stateId":number,
    "regionId":number,
    "villages": VillageData[]
}

interface Date {
  getWeek(start?: number): [Date, Date];
}

export interface VillageData {
    "villagename": string,
    "status": string
}


