/**
 *
 */
package com.drk.tools.repository;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.drk.tools.model.*;
import com.drk.tools.service.SyncDBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.drk.tools.config.SqliteConfig;
import com.drk.tools.drkrishi.repository.VillageRepository;
import com.drk.tools.exceptions.DbException;

/**
 * @author cropdata-kunal
 *
 */
@Repository
public class SyncDBDao {
    private static final Logger logger = LoggerFactory.getLogger(SyncDBDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SqliteConfig sqliteConfig;

    @Autowired
    VillageRepository villageRepository;
    
    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SyncDBDao.class);

    public boolean sqliteSchemaCreator(String type) {
        boolean isDone = false;
        try {
            Connection con = sqliteConfig.sqliteConnection(type);
            String SQL_CREATE = "";
            Statement psDDL = con.createStatement();

            if (type.equals("quantycalc")) {

                SQL_CREATE = "CREATE TABLE agri_standard_quantity_chart ( ID INT PRIMARY KEY  NOT NULL, AczID INT, SowingWeekStart INT, SowingWeekEnd INT, StateCode INT DEFAULT NULL, CommodityID INT(11) NOT NULL,"
                        + "VarietyID INT(11) NOT NULL, StandardQuantityPerAcre FLOAT, StandardPositiveVariancePerAcre FLOAT,StandardPositiveVariancePercent FLOAT,StandardNegativeVariancePerAcre FLOAT,StandardNegativeVariancePercent FLOAT,"
                        + "Status VARCHAR(50), CreatedAt timestamp,UpdatedAt timestamp )";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_quantity_loss_chart ( ID INT PRIMARY KEY  NOT NULL ,CommodityID INT NOT NULL DEFAULT '0', /*PhenophaseID INT NOT NULL DEFAULT '0',*/ StressID INT DEFAULT '0',"
                        + "MinBandValue FLOAT, MaxBandValue FLOAT,MinQuantityCorrectionPercent FLOAT,MaxQuantityCorrectionPercent FLOAT, Status VARCHAR(50),CreatedAt timestamp,UpdatedAt timestamp)";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_commodity_stress (ID INT PRIMARY KEY  NOT NULL ,CommodityID INT NOT NULL,StressID INT NOT NULL) ";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_commodity (ID INT PRIMARY KEY  NOT NULL, Name VARCHAR(45),ScientificName varchar(100) DEFAULT NULL)";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE="CREATE TABLE  agri_variety(ID INT PRIMARY KEY  NOT NULL ,CommodityID INT, Name varchar(100),VarietyCode varchar(20))";
                psDDL.addBatch(SQL_CREATE);
         
//			SQL_CREATE = "CREATE TABLE agri_commodity_phenophase ( ID INT PRIMARY KEY  NOT NULL , CommodityID INT NOT NULL DEFAULT '0', PhenophaseID INT DEFAULT '0',"
//					+ " Status VARCHAR(50),CreatedAt timestamp,UpdatedAt timestamp)";
//			psDDL.addBatch(SQL_CREATE); 

            } else if (type.equals("flipbook")) {

				SQL_CREATE = "CREATE TABLE flipbook_search_temp ( ID INT PRIMARY KEY  NOT NULL, StateCode INT NOT NULL, RegionID INT NOT NULL, CommodityID INT NOT NULL, StressTypeID INT NOT NULL, PlantPartID INT NOT NULL, SymptomID INT NOT NULL)";
				psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE geo_state ( ID INT PRIMARY KEY  NOT NULL,StateCode INT DEFAULT NULL, Name CHAR(100) NOT NULL )";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE geo_region ( RegionID INT PRIMARY KEY  NOT NULL , StateCode INT DEFAULT '0', Name CHAR(100) NOT NULL, Description CHAR(512) DEFAULT NULL)";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_commodity (ID INT PRIMARY KEY  NOT NULL ,Name CHAR(100) NOT NULL, ScientificName CHAR(100) DEFAULT NULL )";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_phenophase (ID INT PRIMARY KEY  NOT NULL ,Name CHAR(100) NOT NULL )";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_stress_type (ID INT PRIMARY KEY  NOT NULL , Name CHAR(100) NOT NULL )";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_commodity_stress (ID INT PRIMARY KEY  NOT NULL ,CommodityID INT NOT NULL,StartDas INT NOT NULL,EndDas INT NOT NULL,StressTypeID INT NOT NULL,Name CHAR(45) NOT NULL) ";
                psDDL.addBatch(SQL_CREATE);
//                SQL_CREATE = "CREATE TABLE agri_stress_stage (ID INT PRIMARY KEY  NOT NULL ,CommodityID INT NOT NULL,StartPhenophaseID INT NOT NULL,EndPhenophaseID INT NOT NULL,StressTypeID INT NOT NULL,StressID INT NOT NULL,Name CHAR(45) NOT NULL,Description CHAR(512) DEFAULT NULL) ";
//                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE agri_plant_part ( ID INT PRIMARY KEY  NOT NULL , Name CHAR(45) NOT NULL )";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE flipbook_symptom_images (ID INT PRIMARY KEY  NOT NULL ,SymptomID INT NOT NULL,ImageID CHAR(100) NOT NULL,Diagnosed INT DEFAULT '0')";
                psDDL.addBatch(SQL_CREATE);
                SQL_CREATE = "CREATE TABLE flipbook_symptoms (ID INT PRIMARY KEY  NOT NULL ,CommodityID INT NOT NULL,PlantPartID INT NOT NULL, StressTypeID INT NOT NULL, StressID INT NOT NULL, Symptom CHAR(1000) NOT NULL,GenericImage CHAR(1000) NOT NULL, StressName CHAR(1000) NOT NULL)";
                psDDL.addBatch(SQL_CREATE);

            }

            psDDL.executeBatch();
            psDDL.close();
            isDone = true;
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            isDone = false;
        }
        return isDone;
    }

    public boolean insertData(String type) {
        boolean isInsert = false;
        try {

            if (type.equals("quantycalc")) {
                try {
                    isInsert = sqliteInsert(jdbcTemplate.query(
                            "select zsqc.ID,zv.AczID,zv.SowingWeekStart,zv.SowingWeekEnd,StateCode,CommodityID,VarietyID,StandardQuantityPerAcre,\n" +
                                    "       StandardPositiveVariancePerAcre,StandardPositiveVariancePercent,\n" +
                                    "       StandardNegativeVariancePerAcre,StandardNegativeVariancePercent,\n" +
                                    "       zsqc.Status, zsqc.CreatedAt, zsqc.UpdatedAt\n" +
                                    "from zonal_standard_quantity_chart zsqc\n" +
                                    "inner join zonal_variety zv on zv.ID = zsqc.ZonalVarietyID\n" +
                                    "inner join geo_acz a on a.ID = zv.AczID",
                            (rs, rowNum) -> new AgriStandardQuantityChart(rs.getInt("ID"), rs.getInt("AczID"), rs.getInt("SowingWeekStart"),
                                    rs.getInt("SowingWeekEnd"), rs.getInt("StateCode"),
                                    rs.getInt("CommodityID"), rs.getInt("VarietyID"),
                                    rs.getFloat("StandardQuantityPerAcre"), rs.getFloat("StandardPositiveVariancePerAcre"),
                                    rs.getFloat("StandardPositiveVariancePercent"),
                                    rs.getFloat("StandardNegativeVariancePerAcre"),
                                    rs.getFloat("StandardNegativeVariancePercent"), rs.getString("Status"),
                                    rs.getDate("CreatedAt"), rs.getDate("UpdatedAt"))),
                            "agri_standard_quantity_chart", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_standard_quantity_chart");
                }

                try {
                    isInsert = sqliteInsert(jdbcTemplate.query("select ID,CommodityID,/*PhenophaseID,*/StressID,"
                                    + "MinBandValue,MaxBandValue,MinQuantityCorrectionPercent,MaxQuantityCorrectionPercent, Status, CreatedAt, UpdatedAt from agri_quantity_loss_chart",
                            (rs, rowNum) -> new AgriQuantityLossChart(rs.getInt("ID"), rs.getInt("CommodityID"),
                                    /*rs.getInt("PhenophaseID"),*/ rs.getInt("StressID"),
                                    rs.getFloat("MinBandValue"), rs.getFloat("MaxBandValue"),
                                    rs.getFloat("MinQuantityCorrectionPercent"),
                                    rs.getFloat("MaxQuantityCorrectionPercent"), rs.getString("Status"),
                                    rs.getDate("CreatedAt"), rs.getDate("UpdatedAt"))),
                            "agri_quantity_loss_chart", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in Agri Quantity Loss Chart");
                }

                try {    // StreeName
                    isInsert = sqliteInsert(jdbcTemplate.query(
                            "select ID, CommodityID, StressID from agri_commodity_stress where Status = 'Active'",
                            (rs, rowNum) -> new AgriCommodityStressQuantityC(rs.getInt("ID"), rs.getInt("CommodityID"),
                                    rs.getInt("StressID"))),
                            "agri_commodity_stress", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_commodity_stress");
                }
                
                
                
                try { //agri commodity
                    isInsert = sqliteInsert(jdbcTemplate.query("select ID,Name,ScientificName from agri_commodity where Status='Active'",
                            (rs, rowNum) -> new AgriCommodity(rs.getInt("ID"),rs.getString("Name"),rs.getString("ScientificName"))),
                    		"agri_commodity", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_commodity");
                }
                
                            
          
                try { //agri variety
                    isInsert = sqliteInsert(jdbcTemplate.query("SELECT ID,CommodityID,Name,VarietyCode FROM cdt_master_data.agri_variety where Status='Active'",
                            (rs, rowNum) -> new AgriVariety(rs.getInt("ID"),rs.getInt("CommodityID"),rs.getString("Name"),rs.getString("VarietyCode"))),
                    		"agri_variety", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_variety");
                
                }                
//			isInsert = sqliteInsert(jdbcTemplate.query(
//					"select ID,CommodityID,PhenophaseID,Status,"
//							+ " CreatedAt, UpdatedAt from agri_commodity_phenophase",
//					(rs, rowNum) -> new AgriCommodityPhenophase(rs.getInt("ID"), rs.getInt("CommodityID"),
//							rs.getInt("PhenophaseID"), rs.getString("Status"), rs.getDate("CreatedAt"),
//							rs.getDate("UpdatedAt"))),
//					"agri_commodity_phenophase");

            } else if (type.equals("flipbook")) {

                try {
                    isInsert = sqliteInsert(jdbcTemplate.query(
                            "select ID,StateCode,RegionID,CommodityID, StressTypeID, PlantPartID, SymptomID from flipbook_search_temp",
                            (rs, rowNum) -> new FlipbookSearchTemp(rs.getInt("ID"), rs.getInt("StateCode"),
                                    rs.getInt("RegionID"), rs.getInt("CommodityID"),
                                    rs.getInt("StressTypeID"), rs.getInt("PlantPartID"),
                                    rs.getInt("SymptomID"))),
                            "flipbook_search_temp", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in flipbook_search_temp");
                }
                try {
                    isInsert = sqliteInsert(jdbcTemplate.query("select ID,StateCode,Name from geo_state",
                            (rs, rowNum) -> new GeoState(rs.getInt("ID"), rs.getInt("StateCode"), rs.getString("Name"))),
                            "geo_state", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in geo_state");
                }
                try {
                    // Region
                    isInsert = sqliteInsert(
                            jdbcTemplate
                                    .query("select RegionID,StateCode, Name, Description from geo_region",
                                            (rs, rowNum) -> new GeoRegion(rs.getInt("RegionID"), rs.getInt("StateCode"),
                                                    rs.getString("Name"), rs.getString("Description"))),
                            "geo_region", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in geo_region");
                }
                // Commodity
                try {
                    isInsert = sqliteInsert(jdbcTemplate.query("select ID,Name,ScientificName from agri_commodity",
                            (rs, rowNum) -> new AgriCommodity(rs.getInt("ID"), rs.getString("Name"),
                                    rs.getString("ScientificName"))),
                            "agri_commodity", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_commodity");
                }
                // Phenophase
                try {
                    isInsert = sqliteInsert(
                            jdbcTemplate.query("select ID,Name from agri_phenophase",
                                    (rs, rowNum) -> new AgriPhenophase(rs.getInt("ID"), rs.getString("Name"))),
                            "agri_phenophase", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_phenophase");
                }
                try {    // StressType
                    isInsert = sqliteInsert(
                            jdbcTemplate.query("select ID,Name from agri_stress_type",
                                    (rs, rowNum) -> new AgriStressType(rs.getInt("ID"), rs.getString("Name"))),
                            "agri_stress_type", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_stress_type");
                }
                try {    // StreeName
                    isInsert = sqliteInsert(jdbcTemplate.query(
                            "select any_value(zsd.ID) as ID, zc.CommodityID,zsd.StartDas,zsd.EndDas ,ass.StressTypeID,ass.Name as Name\n" +
                                    "       from cdt_master_data.zonal_stress_duration zsd\n" +
                                    "inner join cdt_master_data.agri_stress ass on zsd.StressID=ass.ID\n" +
                                    "inner join cdt_master_data.zonal_commodity zc on zsd.ZonalCommodityID=zc.ID\n" +
                                    "group by zc.CommodityID,ass.StressTypeID,ass.Name,zsd.StartDas,zsd.EndDas;",
                            (rs, rowNum) -> new AgriStressName(rs.getInt("ID"), rs.getInt("CommodityID"),
                                    rs.getInt("StartDas"), rs.getInt("EndDas"), rs.getInt("StressTypeID"),
                                    rs.getString("Name"))),
                            "agri_commodity_stress", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_commodity_stress");
                }
//                try {
//                    // StreeStage
//                    isInsert = sqliteInsert(jdbcTemplate.query(
//                            "SELECT Id,CommodityID,StartPhenophaseID,EndPhenophaseID,StressTypeID,StressID,Name,Description FROM agri_stress_stage ",
//                            (rs, rowNum) -> new AgriStressStage(rs.getInt("ID"), rs.getInt("CommodityID"),
//                                    rs.getInt("StartPhenophaseID"), rs.getInt("EndPhenophaseID"), rs.getInt("StressTypeID"),
//                                    rs.getInt("StressID"), rs.getString("Name"), rs.getString("Description"))),
//                            "agri_stress_stage", type);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    logger.info("Exception in agri_stress_stage");
//                }
                try {    // PlantPart
                    isInsert = sqliteInsert(
                            jdbcTemplate.query("select id, name from agri_plant_part",
                                    (rs, rowNum) -> new AgriPlantPart(rs.getInt("ID"), rs.getString("name"))),
                            "agri_plant_part", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in agri_plant_part");
                }        // Question
                try {
                    isInsert = sqliteInsert(jdbcTemplate.query(
                            "select fs.ID, fs.CommodityID, fs.PlantPartID, fs.StressTypeID, fs.StressID,  fs.Symptom , fs.GenericImage, ass.Name as StressName\n" +
                                    "from cdt_master_data.flipbook_symptoms fs\n" +
                                    "inner join cdt_master_data.agri_stress ass on fs.StressID=ass.ID",
                            (rs, rowNum) -> new FlipbookSymptom(rs.getInt("ID"), rs.getInt("CommodityID"),
                                    rs.getInt("PlantPartID"), rs.getInt("StressTypeID"),
                                    rs.getInt("StressID"), /*rs.getInt("StressStageID"),*/ rs.getString("Symptom"),
                                    rs.getString("GenericImage"), rs.getString("StressName"))),
                            "flipbook_symptoms", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in flipbook_symptoms");
                }
                try {
                    // ImageMapping
                    isInsert = sqliteInsert(
                            jdbcTemplate.query("select id, symptomid, imageid, diagnosed from flipbook_symptom_images",
                                    (rs, rowNum) -> new FlipbookImage(rs.getInt("id"), rs.getInt("symptomid"),
                                            rs.getString("imageid"), rs.getInt("diagnosed"))),
                            "flipbook_symptom_images", type);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Exception in flipbook_symptom_images");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            // throw new DbException(e.getMessage());
        }
        return isInsert;
    }

    public boolean sqliteInsert(List<?> InsertList, String _tableName, String type) {
        boolean isInsert = true;
        Connection con = sqliteConfig.sqliteConnection(type);
        try {

            Statement psDML = con.createStatement();
            StringBuilder SQL_INSERT = new StringBuilder();
            if (InsertList.size() >= 1) {
                SQL_INSERT = new StringBuilder(" Insert into " + _tableName + " values ");
                for (Object _data : InsertList) {
                    SQL_INSERT.append("( ").append(_data).append(" ),");

                }
                
              
                System.out.println(SQL_INSERT);
                
                SQL_INSERT = new StringBuilder(SQL_INSERT.substring(0, SQL_INSERT.length() - 1) + ";");
                psDML.execute(SQL_INSERT.toString());
                isInsert = true;
                con.close();
            }
        } catch (Exception e) {
            isInsert = false;
            throw new DbException("Data Insertion Failed");
        }
        return isInsert;
    }

    public boolean isUpdateFound(String _syncTime, String type) {

        String[] _tables = null;

        if (type.equals("quantycalc")) {

            _tables = new String[]{"zonal_standard_quantity_chart", "agri_quantity_loss_chart","agri_commodity","agri_variety"};

        } else if (type.equals("flipbook")) {

            _tables = new String[]{"geo_state", "geo_region", "agri_commodity", "agri_phenophase", "agri_stress_type",
                    "agri_commodity_stress", "agri_plant_part", "agri_stress_stage"};

        } else if (type.equals("flipbook-images")) {

            _tables = new String[]{"flipbook_symptoms", "flipbook_symptom_images"};
        }

        boolean _updateFound = false;
        try {
            for (String _tab : _tables) {
                int isInsert = 0;
                String _SQL = "select count(*) _count from " + _tab + " where CreatedAt > from_unixtime('" + _syncTime
                        + "') or UpdatedAt >  from_unixtime('" + _syncTime + "')";
                System.out.println(_SQL);
                isInsert = jdbcTemplate.queryForObject(_SQL, Integer.class);
                if (isInsert > 0) {
                    _updateFound = true;
                    break;
                }
            }
        } catch (Exception e) {
            throw new DbException("Unable To find Update " + e.getMessage());
        }

        return _updateFound;
    }

    public List<String> getAllImages() {

        List<String> list = null;

        try {
            list = jdbcTemplate.queryForList("select distinct ImageID from cdt_master_data.flipbook_symptom_images fsi\n" +
                            "where fsi.Status = 'active'",
                    String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Map<String, Object>> getAllRegion() {

        List<Map<String, Object>> list = null;

        try {

            list = villageRepository.getAllRegion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<>();
    }

    public List<Map<String, Object>> getAllVillagesByRegion(Integer regionId) {

        List<Map<String, Object>> villageList = null;

        try {

            villageList = villageRepository.findAllByRegionId(regionId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return villageList != null ? villageList : new ArrayList<>();
    }

    public List<Map<String, Object>> getAllRoles() {

        List<Map<String, Object>> list = null;

        try {
            list = jdbcTemplate.queryForList("select id as ID,code,name from drkrishi_ts.roles order by name");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<>();
    }

    public List<Map<String, Object>> getScoutByRole(Integer roleId) {

        List<Map<String, Object>> list = null;

        try {
            list = jdbcTemplate.queryForList(
                    "select distinct u.id as ID,u.first_name,u.last_name,u.middle_name,u.mobile_number,r.code\n"
                            + "from drkrishi_ts.user_roles ur, drkrishi_ts.users u, drkrishi_ts.roles r where ur.user_id=u.id and ur.role_id=r.id and r.id ="
                            + roleId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<>();
    }

    public List<Map<String, Object>> getAllFarmersByVillage(Integer villageId) {

        List<Map<String, Object>> list = null;

        try {

            list = villageRepository.getAllFarmersByVillage(villageId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<Map<String, Object>>();
    }

    public List<Map<String, Object>> getAllPointOfInterestByVillage(Integer villageId) {

        List<Map<String, Object>> list = null;

        try {
            list = jdbcTemplate.queryForList(
                    "select p.id as ID,p.name,p.lattitude as latitude,p.longitude from drkrishi_ts.poi p where village_id="
                            + villageId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<Map<String, Object>>();
    }

    public String getAppKey() {
        String appKey = null;
        try {
            appKey = jdbcTemplate.queryForObject(
                    "SELECT AppKey FROM cdt_master_data.app_version_control where AppName = 'DrKrishiTool'",
                    String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appKey;
    }

    public List<Map<String, Object>> villagesByScout(Integer scoutId, Integer regionId) {

        List<Map<String, Object>> list = null;

        String query = "select distinct u.id as ID,u.first_name,u.middle_name,u.last_name ,v.id as villageID\n"
                + ",v.name as villageName,v.latitude,v.longitude,teh.name as tehsil,dis.name as district\n"
                + "from drkrishi_ts.prs_task t, drkrishi_ts.prs_assignment pa,drkrishi_ts.users u,drkrishi_ts.village v\n"
                + ",drkrishi_ts.panchayat p,drkrishi_ts.tehsil teh,drkrishi_ts.district dis\n"
                + "where t.prs_assignment_id=pa.id and pa.prs_id=u.id and v.id = t.village_id\n"
                + "and v.panchayat_id=p.id and p.tehsil_id=teh.id and dis.id=teh.district_id and u.id=" + scoutId;

        if (regionId != null) {

            query += " and u.region_id=" + regionId;

        }

        try {
            list = jdbcTemplate.queryForList(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<Map<String, Object>>();
    }

    public Map<String, Object> getFarmerLatLong(String farmerId) {

        try {

            String sql = "SELECT distinct fl.FarmerID as farmerID,fl.Latitude as latitude,fl.Longitude as longitude FROM drkrishi_ts.farmer_location fl where fl.FarmerID="
                    + "'" + farmerId + "'";

            List<Map<String, Object>> farmerLatLong = jdbcTemplate.queryForList(sql);

            return farmerLatLong.size() > 0 ? farmerLatLong.get(0) : new HashMap<>();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public List<Map<String, Object>> getPanchayatByScout(Integer scoutId) {

        List<Map<String, Object>> list = null;

        String query = "select distinct u.id as ID,u.first_name,u.middle_name,u.last_name ,v.id as villageID\n"
                + ",v.name as villageName,v.latitude,v.longitude,p.name as panchayat,teh.name as tehsil,dis.name as district\n"
                + "from drkrishi_ts.prs_task t,drkrishi_ts.prs_assignment pa,drkrishi_ts.users u,drkrishi_ts.village v\n"
                + ",cdt_master_data.regional_panchayat_map map\n"
                + ",drkrishi_ts.panchayat p,drkrishi_ts.tehsil teh,drkrishi_ts.district dis\n"
                + "where t.prs_assignment_id=pa.id and pa.prs_id=u.id and v.id = t.village_id and v.id=map.VillageCode\n"
                + "and v.panchayat_id=p.id and p.tehsil_id=teh.id and dis.id=teh.district_id and u.id=" + scoutId;

        try {
            list = jdbcTemplate.queryForList(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list != null ? list : new ArrayList<Map<String, Object>>();
    }

    public Map<String, Object> getRoleByScoutId(Integer scoutId) {
        try {

            String sql = "select distinct r.code\n"
                    + "	from drkrishi_ts.user_roles ur, drkrishi_ts.users u, drkrishi_ts.roles r where ur.user_id=u.id and ur.role_id=r.id and u.id="
                    + "'" + scoutId + "'";

            List<Map<String, Object>> farmerLatLong = jdbcTemplate.queryForList(sql);

            return farmerLatLong.size() > 0 ? farmerLatLong.get(0) : new HashMap<>();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    
    public List<Map<String, Object>> getFarmarLatLongListByBeforeTime(Timestamp beforeTime) {

        try {

            String sql = "SELECT fl.ID,fl.FarmerID,fl.Latitude,fl.Longitude,fl.CreatedAt FROM drkrishi_ts.farmer_location fl where timestamp(fl.CreatedAt) < '" + beforeTime + "'";

            List<Map<String, Object>> farmarLatLongList = jdbcTemplate.queryForList(sql);

            return farmarLatLongList.size() > 0 ? farmarLatLongList : new ArrayList<>();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    
    public void deleteFarmarLatLongById(Integer id){
        String qyery = "delete from drkrishi_ts.farmer_location where ID = ?";
        jdbcTemplate.update(qyery, id);
        LOGGER.info("Deleted Record with ID = " + id);
        return;
     }

}
