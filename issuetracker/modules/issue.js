var log = new Log();
include('/jagg/jagg.jag');


var url_prefix = context.get(ISSUE_TRACKER_URL)+context.get(DOMAIN);

//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/project/2/issue
var getAllIssue = function (projectId) {
    log.info(">>>>>>>>>>>>>>>..getIssue " + projectId);
    if(projectId === undefined || projectId === null){
        projectId = 0;
        log.info("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");

    }

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project/"+projectId+"/issue";
    var data = {  };
    var issues = get(url, {} ,"json");
    return issues;
};

// "http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-1"
var getIssueByKey = function (issueKey) {
    log.info("========================================================================================= getIssueByKey " );
    var url  = url_prefix+"/issue/"+issueKey;
    var result = get(url, {} ,"json");
    log.info(stringify(result.data.issueResponse));
    log.info("========================================================================================= getIssueByKey " );

    return result.data.issueResponse;
};

//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/project/5/issue
var addIssue = function (projectId, jsonString){
    var LOGGED_IN_USER = session.get("LOGGED_IN_USER");
    var jsonObj = parse(jsonString);

    jsonObj.reporter =  LOGGED_IN_USER;

    var proj = new Object();
    proj.issue=jsonObj;

    jsonString = stringify(proj);

    log.info("****************>>>> " + jsonString);
    var result;
        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project/'+projectId+'/issue';
        result = post(url, jsonString, {
            "Content-Type": "application/json"
        }, 'json');
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+stringify(result));

    return result;

}

//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-2
var editIssue = function (issueKey, jsonString){
    log.info(">>>>>>>>>>>>>>>aadddd..getIssueById " );

    var LOGGED_IN_USER = session.get("LOGGED_IN_USER");
    var jsonObj = parse(jsonString);

    jsonObj.reporter =  LOGGED_IN_USER;

    var proj = new Object();
    proj.issue=jsonObj;

    jsonString = stringify(proj);


    log.info('jsonString '+jsonString);
    var result;

        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/issue/'+issueKey;
        log.info("URL: " + url);
        result = post(url, jsonString, {
            "Content-Type": "application/json"
        }, 'json');
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result).data);

    return result;

}

//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/search
var searchIssue = function (searchType, searchValue) {
    log.info("??????????????????????????????????????");
    var jsonObj = new Object();
    jsonObj.searchType = searchType;
    jsonObj.searchValue = searchValue;

    var json = new Object();
    json.searchBean = jsonObj;



    var url  = url_prefix+"/issue/search";
    var jsonString = stringify(json);
    result = post(url, jsonString, {
        "Content-Type": "application/json"
    }, 'json');

    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result));

    return result.data.searchResponse;
};