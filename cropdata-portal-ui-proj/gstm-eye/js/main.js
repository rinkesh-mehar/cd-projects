if (window.location.host == "dev.cropdata.in") {
    // var baseUrl = "https://api-dev.cropdata.in/gstm-eye/data";
    // var baseUrl = "https://api-ts.cropdata.in/gstm-eye/data"

    var baseUrl = "https://api-ts.cropdata.in/gstm-eye/data"
} else if (window.location.host == "uat.cropdata.in") {
    // var baseUrl = "https://api-tsuat.cropdata.in/gstm-studio/data"
    var baseUrl = "https://api-ts.cropdata.in/gstm-eye/data"
} else if (window.location.host == "www.cropdata.in" || window.location.host == "cropdata.in") {
    var baseUrl = "https://api-ts.cropdata.in/gstm-eye/data"
} else {
    // var baseUrl = "http://192.168.0.67:8081/data";
    // var baseUrl = "https://api-tsuat.cropdata.in/gstm-studio/data"
    var baseUrl = "https://api-ts.cropdata.in/gstm-eye/data"
}




console.log(window.location.host);
console.log(baseUrl);

var mLocation = baseUrl + '/st';
var dFilter = baseUrl + '/filters';
var forx = '';
var nine = '';
var region = '';
var regions = '';
var dabbaLayer = '';
var mapdata2 = '';
var mapTilesCache = [];
var analyticsCache = [];
var regionBoundaryCache = undefined;



if (window.location.host == "uat.cropdata.in") {
    var parameter = 88;

} else if (window.location.host == "www.cropdata.in" || window.location.host == "cropdata.in") {
    var parameter = 28;

} else {
    var parameter = 28;
}

var weekNum = '';
var polyline = undefined;
var reqVars = null;
var tileId = null;
var urlVars = {};
var current_lat;
var current_lng;
var stateId;
var districtId;
var regionId;
var pId
var dId
var cId
var scId
var paraId
var analyticData = false;



function prepareUrl(northWest, southEast) {
    urlVars['zl'] = zl;
    urlVars['p'] = parameter;
    urlVars['w'] = weekNum;
    urlVars['s'] = stateId == null ? '' : stateId;
    urlVars['d'] = districtId == null ? '' : districtId;
    urlVars['r'] = regionId == null ? '' : regionId;
    urlVars['tid'] = tileId == null ? '' : tileId;
    urlVars['lat'] = current_lat == undefined ? '' : current_lat;
    urlVars['lng'] = current_lng == undefined ? '' : current_lng;

    if (northWest != undefined) {
        urlVars['ltX'] = northWest.lng;
        urlVars['ltY'] = northWest.lat;
    }

    if (northWest != undefined) {
        urlVars['rbX'] = southEast.lng;
        urlVars['rbY'] = southEast.lat;
    }

    reqVars = getRequestVars();
    var newurl = window.location.protocol + "//" + window.location.host + window.location.pathname + '?' + reqVars;

    if (history.pushState) {
        window.history.pushState({ path: newurl }, '', newurl);
    }
}


function weekNumClick(event) {
    weekNum = $(event.target).text();
    prepareUrl();
    placeDataUrl = baseUrl + '/analytics/region?' + getRequestVars();

    $(".weekCalendar").find(".active").removeClass("active");
    $(event.target).addClass("active");

    if (analyticData == true) {
        $.ajax({
            url: placeDataUrl,
            dataType: "script",
            success: function() {
                let regionName = regionData.in;
                let regionDistName = regionData.district;
                $('.in').html(regionData.in).attr('title', regionData.in);
                var ploc = regionData.district + (regionData.district != '' ? ", " : '') + regionData.state;
                $('.address').html(ploc).attr('title', ploc);
                $('.regionpreload').removeClass('tspt-70');
            },
            error: function(jqXHR, textStatus, errorThrown) {}
        });
    }

    mapDataUrl = baseUrl + '/map?' + getRequestVars();

    if (parseInt(zl) < 10) {
        if (mapTilesCache[zl] == undefined) {
            // Fetch and cache the map data
            $.ajax({
                url: mapDataUrl,
                dataType: "script",
                success: function(respData) {
                    mapTilesCache[zl] = mapdata2;
                    renderMap(mapdata2);
                    $("#loading").fadeOut();
                }
            });
        } else {
            renderMap(mapTilesCache[zl]);
        }
    } else if (parseInt(zl) > 9) {
        // Call map after zl 9
        $.ajax({
            url: mapDataUrl,
            dataType: "script",
            success: function() {
                renderMap(mapdata2);
            }
        });
    }
}


function getRequestVars() {
    var reqArr = [];
    for (k in urlVars) {
        var v = urlVars[k]
        reqArr.push(k + "=" + v);
    }
    return (reqArr.join('&'));
}

function renderMap(data) {

    map.removeLayer(dabbaLayer);

    dabbaLayer = L.geoJson(data, {
        style: overlay_layer,
        onEachFeature: dabbasOnEachFeature,
    }).addTo(map);

    fetchAnalysis();

    zoom = map.getZoom();

    // Remove 25 region polyline
    if (polyline != undefined) {
        map.removeLayer(polyline);
    }

    // Draw 25 region polyline
    if (zoom >= 4 && zoom <= 10) {
        renderRegionBoundary();
    }
}

function highlightFeature(e) {
    resetHighlight(e)
    var dabbaLayer = e.target;
    dabbaLayer.setStyle({
        weight: 10,
        color: '#2935e8',
        dashArray: '',
        fillOpacity: 0.5
    });


    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        dabbaLayer.bringToFront();
    }
}


function resetHighlight(e) {
    dabbaLayer.resetStyle(e.target);
}

// function zoomToFeature(e) {
//     map.fitBounds(e.target.getBounds());
// }

function dabbasOnEachFeature(feature, layer) {
    layer.on({
        mouseover: highlightFeature,
        mouseout: resetHighlight,
        click: function(e) {
            // resetHighlight(e);
            brief_call(feature.properties.TileID);
            // highlightFeature(e)
        }
    });
}

// function onEachFeature(feature, layer) {
//     layer.on({
//         mouseout: resetHighlight,
//         click: highlightFeature
//     });
// }


function plolyStyle(feature) {
    return {
        fillColor: feature.properties.Color,
        weight: 1,
        opacity: 1,
        color: '#ff25f3',
        dashArray: 0,
        fillOpacity: 0.8
    }
}


function dabbaStyle(feature) {
    return {
        fillColor: feature.properties.Color,
        weight: 1,
        opacity: 1,
        color: '#2935e8',
        dashArray: 0,
        fillOpacity: 0.8
    }
}

function district_data(feature) {
    return {
        fillColor: feature.properties.Color,
        weight: 1,
        opacity: 1,
        color: '#2935e8',
        dashArray: 0,
        fillOpacity: 0.0
    }
}

function tehsil_data(feature) {
    return {
        fillColor: feature.properties.Color,
        weight: 0.5,
        opacity: 1,
        color: 'rgba(0,0,0, 0.8)',
        dashArray: 2,
        fillOpacity: 0.0
    }
}

function llStyle(feature) {
    return {
        fillColor: feature.properties.Color,
        weight: 1,
        opacity: 1,
        color: 'rgba(0,0,0, 0.1)',
        dashArray: 0,
        fillOpacity: 0.0
    }
}

function overlay_layer(feature) {
    return {
        fillColor: feature.properties.Color,
        weight: 1,
        opacity: 0.1,
        color: 'black',
        dashArray: 1,
        fillOpacity: 0.8
    }
}

function ZoomToIndia() {
    var corner1 = L.latLng(35.494009507787766, 97.40256147663614),
        corner2 = L.latLng(10.965534776232332, 58.1766451353734),
        bounds = L.latLngBounds(corner1, corner2);
    map.fitBounds(bounds);

}

function ZoomToRegion(region) {

    if (region == 1) {
        var corner1 = L.latLng(32.0232133942454, 76.35498046875),
            corner2 = L.latLng(31.37357161574468, 74.24972534179688),
            bounds = L.latLngBounds(corner1, corner2);
    } else if (region == 2) {
        var corner1 = L.latLng(maxy, maxx),
            corner2 = L.latLng(miny, minx),
            bounds = L.latLngBounds(corner1, corner2);
    }

    map.fitBounds(bounds);
}


function renderRegionBoundary() {

    if (regionBoundaryCache == undefined) {
        $.ajax({
            url: "data/25lines.geojson",
            dataType: "script",
            success: function() {
                regionBoundaryCache = polyline
                polyline = L.geoJson(polyline, {
                    style: plolyStyle
                }).addTo(map);
            },
            error: function(jqXHR, textStatus, errorThrown) {}
        });
    } else {
        polyline = L.geoJson(regionBoundaryCache, {
            style: plolyStyle,
        }).addTo(map);
    }
}

function fetchAnalysis() {

    analysisDataUrl = baseUrl + '/analytics?' + getRequestVars();

    // Fetch and cache the analytics data

    if (analyticData == true) {
        $.ajax({
            url: analysisDataUrl,
            dataType: "script",
            success: function() {
                renderAnalyticsData(analyticsData);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // alert(jqXHR.status);
                // alert(textStatus);
                // alert(errorThrown);

                // if (xhr.status === 417) {
                //     alert("gegregregege");
                // }
            }
        });
    }
}

function fetchPlaceNameUnderPointer() {
    placeDataUrl = baseUrl + '/analytics/region?' + getRequestVars();
    // Fetch and cache the analytics data

    if (analyticData == true) {
        $.ajax({
            url: placeDataUrl,
            dataType: "script",
            success: function() {
                let regionName = regionData.in;
                let regionDistName = regionData.district;
                $('.in').html(regionData.in).attr('title', regionData.in);
                var ploc = regionData.district + (regionData.district != '' ? ", " : '') + regionData.state;
                $('.address').html(ploc).attr('title', ploc);
                $('.regionpreload').removeClass('tspt-70');
            },
            error: function(jqXHR, textStatus, errorThrown) {}
        });
    }
}

function brief_call(tId) {

    tileId = tId;
    prepareUrl(); // This will push new URL in history
    var briefUrl = baseUrl + "/analytics/brief?" + getRequestVars();

    $.ajax({
        url: briefUrl,
        dataType: "script",
        beforeSend: function(xhr) {
            $(".hover-result").hide();
        },
        success: function() {
            let avgIrrigation = briefAnalyticsData.regionProfile.values.avgIrrigation;
            let avgAverageLhSize = briefAnalyticsData.regionProfile.values.avgAverageLhSize;
            let focusCrops = briefAnalyticsData.focusCrops;
            $(".hover-result").show();
            $(".irrigatedValue, .avgLhSizeValue, #crops, #yarValue").empty();
            $(".irrigatedValue").html(avgIrrigation);
            $(".avgLhSizeValue").html(avgAverageLhSize);
            $("#yarValue").html(briefAnalyticsData.regionProfile.aggregate.value);
            yarValue
            $.each(focusCrops, function(index, value) {
                var cropName = `<div class="value-crop">${value.cropName}</div>`;
                $("#crops").html(cropName);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {}

    });
}

function sidePanelControl() {
    if ($(".analyticData").hasClass("active")) {
        analyticData = false;
        $(".analyticData").removeClass("active");
        $(".filters").removeClass("briefShift");
        $(".hover-result").addClass("briefShift");
        $(".nav-toggle .fas").toggleClass("fa-bars fa-times");
    } else {
        analyticData = true;
        $(".analyticData").addClass("active");
        $(".hover-result").removeClass("briefShift");
        $(".filters").addClass("briefShift");
        $(".nav-toggle .fas").toggleClass("fa-bars fa-times");
        fetchPlaceNameUnderPointer();
        fetchAnalysis();
    }
}

function briefPanelControl() {
    $(".hover-result").hide();
}

function renderAnalyticsData(analyticsData) {

    $('.preload').removeClass('tspt-70');

    if (analyticsData.template == 'production') {
        $('#left-side-bar').load('leftside-production.html');
        return false
    } else if (analyticsData.template == 'pricing') {
        $('#left-side-bar').load('leftside-pricing.html');
    }
    let focusCrops = analyticsData.focusCrops;
    let riskFreeCrops = analyticsData.riskFreeCrops;
    let region = analyticsData.region;
    let regionProfile = analyticsData.regionProfile;


    let totalProduction = regionProfile.values.totalProduction;
    $('.production-value').text(totalProduction);

    if (analyticsData.template != 'production' && analyticsData.template != 'pricing') {
        if (zl == '09' || zl == '10') {
            $(".production-value").attr("title", "Production value in million ton");
            $(".lh-size-value").attr("title", "Average land holding size in hectares");
        } else if (zl == '11' || zl == '12') {
            $(".production-value").attr("title", "Production value in kilo ton");
            $(".lh-size-value").attr("title", "Average land holding size in hectares");
        } else {
            $(".production-value").attr("title", "Production value tons");
            $(".lh-size-value").attr("title", "Average land holding size in hectares");
        }
    }

    var getFocus = '';
    var getVillages = '';

    var fid = 1;
    $.each(focusCrops, function(index, value) {
        if (index <= 5) {
            getFocus += `<li title="${value.cropName}"><span style="background-color:${value.color}">${fid}</span> ${value.cropName}</li>`
        }
        fid++;
    });

    $.each(riskFreeCrops, function(index, value) {

        getVillages += `<tr>
							<td>${value.cropName}</td>
							<td>${value.value}</td>
						</tr>`
    });

    $("#focus-crops").html(getFocus);
    $("#top-risk").html(getVillages);
    // end

    $('.season').html(analyticsData.season);
    $('.irrigated-area').html(regionProfile.fields.avgIrrigation);
    $('.nsa-ratio').html(regionProfile.fields.avgNsaTgaPer);
    $('.lh-size').html(regionProfile.fields.avgAverageLhSize);
    $('.production').html(regionProfile.fields.totalProduction);

    $('.irrigated-value').html(regionProfile.values.avgIrrigation);
    $('.nsa-ratio-value').html(regionProfile.values.avgNsaTgaPer);
    $('.lh-size-value').html(regionProfile.values.avgAverageLhSize);
    $('.year').html('<a href="#">2021</a>');
}





// Create the map
var map = L.map('cdt-map', {
    minZoom: 5,
    maxZoom: 14
})

googleStreets = L.tileLayer('http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
    maxZoom: 14,
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
});
map.addLayer(googleStreets);


$(document).ready(function() {
    //Redirect to maintenance page
    // window.location = "working.html";
    // $('#left-side-bar').load('leftside-default.html');
    $('#left-side-bar').load('leftside-default.html');
    map.addEventListener('mousemove', function(ev) {
        current_lat = ev.latlng.lat;
        current_lng = ev.latlng.lng;
    });

    $.ajax({
        url: "data/nine.geojson",
        dataType: "script",
        success: function() {
            dabbaLayer = L.geoJson(mapdata, {
                style: llStyle,
            }).addTo(map);

            $.ajax({
                url: "data/district.geojson",
                dataType: "script",
                success: function() {

                    district = L.geoJson(district, {
                        style: district_data
                    }).addTo(map);
                    ZoomToIndia();
                },
                error: function(jqXHR, textStatus, errorThrown) {}
            });
        }
    });

    // add a scale at at your map.
    var scale = L.control.scale().addTo(map);

    // To call the brief analytics
    map.on('click', function(e) {
        current_lat = e.latlng.lat;
        current_lng = e.latlng.lng;
    });


    map.on('moveend', function(e) {

        $('.preload').addClass('tspt-70');

        $('.regionpreload').addClass('tspt-70');

        zoom = map.getZoom();
        if (zoom > 4) {

            if (zoom <= 7) {
                zl = '09'
            } else if (zoom == 8) {
                zl = '09'
            } else if (zoom == 9) {
                zl = '09'
            } else if (zoom == 10) {
                zl = 10
            } else if (zoom == 11) {
                zl = 11
            } else if (zoom == 12) {
                zl = 12
            } else if (zoom == 13) {
                zl = 13
            } else if (zoom == 14) {
                zl = 14
            } else if (zoom == 15) {
                zl = 14
            } else if (zoom == 16) {
                zl = 14
            } else if (zoom == 17) {
                zl = 14
            } else if (zoom == 18) {
                zl = 14
            } else if (zoom == 19) {
                zl = 14
            } else if (zoom == 20) {
                zl = 14
            } else {
                zl = 20
            }

            bounds = map.getBounds();
            var northWest = bounds.getNorthWest(),
                southEast = bounds.getSouthEast();

            prepareUrl(northWest, southEast);
            var mapDataUrl = baseUrl + '/map?' + getRequestVars();

            console.log(urlVars);
            // console.log(mapdata2)
            var obje = mapdata2.features;
            var obj = {
                sample: []
            };
            var cLat = urlVars.ltY
            var cLng = urlVars.ltX


            var ArrayL = []
            if (obje != null) {
                for (let i = 0; i < obje.length; i++) {
                    obj.sample.push(obje[i].geometry.coordinates[0][0]);

                }

                console.log(obj.sample)
            }

            var stored = {};

            if (obj.sample != null) {
                for (let i = 0; i < obj.sample.length; i++) {

                    console.log('sample 0 = ' + obj.sample[i][0] + ',' + 'sample 1 = ' + obj.sample[i][1])
                    ArrayL.push(distance(cLng, cLat, obj.sample[i][1], obj.sample[i][0], "N"));




                    // add a item
                    var key = '' + obj.sample[i][0] + ',' + obj.sample[i][1];
                    stored[key] = distance(cLng, cLat, obj.sample[i][1], obj.sample[i][0], "N");

                }


                console.log(stored)
            }



            function distance(lat1, lon1, lat2, lon2, unit) {
                var radlat1 = Math.PI * lat1 / 180
                var radlat2 = Math.PI * lat2 / 180
                var theta = lon1 - lon2
                var radtheta = Math.PI * theta / 180
                var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
                if (dist > 1) {
                    dist = 1;
                }
                dist = Math.acos(dist)
                dist = dist * 180 / Math.PI
                dist = dist * 60 * 1.1515
                if (unit == "K") { dist = dist * 1.609344 }
                if (unit == "N") { dist = dist * 0.8684 }
                return dist
            }


            console.log('cLat = ' + cLat)
            console.log('cLng = ' + cLng)



            // console.log('Before ArrayL = ' + ArrayL)
            ArrayL.sort(function(a, b) {
                return a - b;
            });

            console.log('After ArrayL = ' + ArrayL)


            fetchPlaceNameUnderPointer();

            if (parseInt(zl) < 10) {
                if (mapTilesCache[zl] == undefined) {
                    // Fetch and cache the map data
                    $.ajax({
                        url: mapDataUrl,
                        dataType: "script",
                        success: function(respData) {
                            mapTilesCache[zl] = mapdata2;
                            renderMap(mapdata2);
                            $("#loading").fadeOut();
                        }
                    });
                } else {
                    renderMap(mapTilesCache[zl]);
                }
            } else if (parseInt(zl) > 9) {
                // Call map after zl 9
                $.ajax({
                    url: mapDataUrl,
                    dataType: "script",
                    success: function() {
                        renderMap(mapdata2);
                    }
                });
            }

            $("#zl-nav ul").find(".active").removeClass("active");

            if (zl == '6') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-6").addClass("active");
            }

            if (zl == '7') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-7").addClass("active");
            }

            if (zl == '8') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-8").addClass("active");
            }

            if (zl == '09') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-9").addClass("active");
            }

            if (zl == '10') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-10").addClass("active");
            }

            if (zl == '11') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-11").addClass("active");
            }

            if (zl == '12') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-12").addClass("active");
            }

            if (zl == '13') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-13").addClass("active");
            }

            if (zl == '14') {
                $("#zl-nav ul").find(".active").removeClass("active");
                $("#zl-nav ul").find(".class-14").addClass("active");
            }
            $("#zl-nav ul li").on('click', function() {
                var zoom_level = $(this).attr('id');
                $("#zl-nav ul").find(".active").removeClass("active");
                if (zoom_level == zoom_level) {
                    map.setZoom(zoom_level);
                    $(this).addClass("active");
                }
            });
        }
    });
});

function hide_st(id, name) {
    stateId = id;
    prepareUrl()
    $(".district-parent").show();
    $(".region-parent").show();
    $(".state-parent .dropdown-toggle").html(name.toLowerCase());
    $(".district-parent a").text('District');
    // $(".state-parent").hide();

    $.ajax({
        url: mLocation + '?st=' + id + '&f=1',
        dataType: "json",
        type: "GET",
        success: function(data) {
            var vData = '';

            data
            $.each(data, function(index, value) {
                vData += `<a class="dropdown-item" href="#" onclick="filterByDistrict(${id}, ${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".district-parent .dropdown-menu").html(vData);
        },
        error: function(jqXHR, textStatus, errorThrown) {}
    });

    $.ajax({
        type: 'GET',
        url: '/gstm-eye/regionData.json',
        dataType: 'json',
        success: function(data) {
            console.log(data)
            var vData = '';
            $.each(data, function(index, value) {
                vData += `<a class="dropdown-item" href="#" onclick="filterByRegionData(${id}, ${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".region-parent .dropdown-menu").html(vData);
        },
        error: function(jqXHR, textStatus, errorThrown) {}
    });
}

function filterByDistrict(stateId, id, name) {
    console.log(stateId, id, name);
    districtId = id;
    regionId = '';
    prepareUrl()
        // https: //api-ts.cropdata.in/gstm-eye/data/st?st=3&d=2&f=2
    $(".district-parent .dropdown-toggle").html(name.toLowerCase());

    $.ajax({
        url: mLocation + '?st=' + stateId + '&d=' + id + '&f=2',
        dataType: "json",
        type: "GET",
        success: function(data) {},
        error: function(jqXHR, textStatus, errorThrown) {}
    });
}

function filterByRegionData(stateId, id, name) {
    console.log(stateId, id, name);
    regionId = id;
    districtId = '';
    prepareUrl()
    $(".region-parent .dropdown-toggle").html(name.toLowerCase());
    $.ajax({
        url: mLocation + '?st=' + stateId + '&r=' + id,
        dataType: "json",
        type: "GET",
        success: function(data) {},
        error: function(jqXHR, textStatus, errorThrown) {}
    });
}

function get_dType(id, platformName) {
    pId = id;
    $(".data_type-parent").show();
    $(".Platforms-parent .dropdown-toggle").html(platformName.toLowerCase());
    $(".data_type-parent a").text('Data Type');
    // $(".Platforms-parent").hide();

    $.ajax({
        url: dFilter + '?plf=' + pId + '&dt=' + 0 + '&ct=' + 0 + '&sct=' + 0 + '&f=' + 1,
        dataType: "json",
        type: "GET",
        success: function(data) {
            var fData = '';
            $.each(data, function(index, value) {
                fData += `<a class="dropdown-item" href="#" onclick="get_c(${pId}, ${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".data_type-parent .dropdown-menu").html(fData);
        },
        error: function(jqXHR, textStatus, errorThrown) {

        }
    });
}

function get_c(pId, id, dateTypeName) {
    dId = id;
    $(".category-parent").show();
    $(".data_type-parent .dropdown-toggle").html(dateTypeName.toLowerCase());
    $(".category-parent a").text('Category');
    // $(".data_type-parent").hide();

    $.ajax({
        url: dFilter + '?plf=' + pId + '&dt=' + dId + '&ct=' + 0 + '&sct=' + 0 + '&f=' + 2,
        dataType: "json",
        type: "GET",
        success: function(data) {
            var fData = '';
            $.each(data, function(index, value) {
                fData += `<a class="dropdown-item" href="#" onclick="get_sc(${pId}, ${dId}, ${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".category-parent .dropdown-menu").html(fData);
        },
        error: function(jqXHR, textStatus, errorThrown) {

        }
    });
}

function get_sc(pId, dId, id, categoryName) {
    cId = id;
    $(".sub_category-parent").show();
    $(".category-parent .dropdown-toggle").html(categoryName.toLowerCase());
    $(".sub_category-parent a").text('Sub Category');
    // $(".category-parent").hide();

    $.ajax({
        url: dFilter + '?plf=' + pId + '&dt=' + dId + '&ct=' + cId + '&sct=' + 0 + '&f=' + 3,
        dataType: "json",
        type: "GET",
        success: function(data) {

            if (data == '') {
                $(".parameter-parent").show();
                $(".sub_category-parent").hide();
                $.ajax({
                    url: dFilter + '?plf=' + pId + '&dt=' + dId + '&ct=' + cId + '&sct=' + 0 + '&f=' + 4,
                    dataType: "json",
                    type: "GET",
                    success: function(data) {
                        var fData = '';
                        $.each(data, function(index, value) {
                            fData += `<a class="dropdown-item" href="#" onclick="get_p(${pId}, ${dId}, ${cId}, ${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`;
                        });
                        $(".parameter-parent .dropdown-menu").html(fData);

                    },
                    error: function(jqXHR, textStatus, errorThrown) {

                    }
                });
            } else {
                var fData = '';
                $.each(data, function(index, value) {
                    fData += `<a class="dropdown-item" href="#" onclick="get_p(${pId}, ${dId}, ${cId}, ${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`;
                });
                $(".sub_category-parent .dropdown-menu").html(fData);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {

        }
    });
}

function get_p(pId, dId, cId, id, subCategory) {
    scId = id;
    $(".parameter-parent").show();
    $(".sub_category-parent .dropdown-toggle").html(subCategory.toLowerCase());
    $(".parameter-parent a").text('Parameter');
    // $(".sub_category-parent").hide();

    $.ajax({
        url: dFilter + '?plf=' + pId + '&dt=' + dId + '&ct=' + cId + '&sct=' + scId + '&f=' + 4,
        dataType: "json",
        type: "GET",
        success: function(data) {
            var fData = '';
            $.each(data, function(index, value) {
                fData += `<a class="dropdown-item" href="#" onclick="get_filter(${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".parameter-parent .dropdown-menu").html(fData);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR + textStatus + errorThrown);
        }
    });
}


function get_filter(id, param) {
    parameter = id;
    $(".parameter-parent .dropdown-toggle").html(param.toLowerCase());
    prepareUrl();
    placeDataUrl = baseUrl + '/analytics/region?' + getRequestVars();
    $.ajax({
        url: placeDataUrl,
        dataType: "script",
        success: function() {
            let regionName = regionData.in;
            let regionDistName = regionData.district;
            $('.in').html(regionData.in).attr('title', regionData.in);
            var ploc = regionData.district + (regionData.district != '' ? ", " : '') + regionData.state;
            $('.address').html(ploc).attr('title', ploc);
            $('.regionpreload').removeClass('tspt-70');
        },
        error: function(jqXHR, textStatus, errorThrown) {}
    });

    mapDataUrl = baseUrl + '/map?' + getRequestVars();

    if (parseInt(zl) < 10) {
        if (mapTilesCache[zl] == undefined) {
            // Fetch and cache the map data
            $.ajax({
                url: mapDataUrl,
                dataType: "script",
                success: function(respData) {
                    mapTilesCache[zl] = mapdata2;
                    renderMap(mapdata2);
                    $("#loading").fadeOut();
                }
            });
        } else {
            renderMap(mapTilesCache[zl]);
        }
    } else if (parseInt(zl) > 9) {
        // Call map after zl 9
        $.ajax({
            url: mapDataUrl,
            dataType: "script",
            success: function() {
                renderMap(mapdata2);
            }
        });
    }
}

function weekFilter(e) {
    console.log((e.target).text());
}


$(function() {
    $(".filterBtn").on('click', function() {
        $(".filterContainer").show();
        $(".filterBtn").hide();
    });
    $.ajax({
        url: mLocation + '?st=0&f=0',
        dataType: "json",
        type: "GET",
        success: function(data) {
            var vData = '';
            $.each(data, function(index, value) {
                vData += `<a class="dropdown-item" href="#" onclick="hide_st(${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".state-parent .dropdown-menu").html(vData);
        },
        error: function(jqXHR, textStatus, errorThrown) {}
    });

    //get Platforms values
    $.ajax({
        url: dFilter + '?plf=' + 0 + '&dt=' + 0 + '&ct=' + 0 + '&sct=' + 0 + '&f=' + 0,
        dataType: "json",
        type: "GET",
        success: function(data) {
            var fData = '';
            $.each(data, function(index, value) {
                fData += `<a class="dropdown-item" href="#" onclick="get_dType(${value.id}, '${value.name}')" data-id="${value.id}">${value.name}</a>`
            });
            $(".Platforms-parent .dropdown-menu").html(fData);
        },
        error: function(jqXHR, textStatus, errorThrown) {}
    });


    $('.dropdown .dropdown-item').on('click', function() {
        console.log($(this).text());
    });
});