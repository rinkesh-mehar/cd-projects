import { AddUserModel } from '../../user-management/add-user/user.model';

export interface AssignVillagesModel{
  assigmentId:number;
  tileId: number;
  stateId: number;
  regionID :number;
  village:TileVillage[];
  month :number;
  year: number;
  weekNumber :number,
  drkrishiUser2:number;
  prscout:AddUserModel;
  datasid:string[];
}


export interface State{
	stateId:number;
	comment:string;
	stateName:string;
	status:number;
}

export interface District{
	district_Id:number;
	comment:string;
	district_Name:string;
	status:number;
	state:State;
}

export interface Region{
	regionID:number;
	tileId:number;
	state:State;
	latitude:number;
	longitude:number;
	regionname:string;
	description:string;
	comment:string;
	status:number;
	gstmRegionid:number;
}

export interface Village{
	village_Id:number;
	comment:string;
	village_Name:string;
	village_Status:number;
	district:District;
	taluka:Taluka;
	state:State;
}

export interface Taluka{
	taluka_Id:number;
	comment:string;
	taluka_Name:string;
	taluka_Status:number;
	district:District;
	state:State;
}


export interface Prscoutdetails{
  assigmentId:number;
  created_Date:Date;
  weekNumber:number;
  month:number;
  year:number;
  drkrishiUser1:number;
  prscout:AddUserModel;
  stateId: number;
  regionID:number;
  village:TileVillage[];
  datasid:string[];
}

export interface TileVillage{
	villageCode:number;
	villageName:string;
}

export interface Week{
	weekNo :number;
	range :string;
}
















