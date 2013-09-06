var log = new Log();


//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/project/2/issue
var getAllIssue = function (projectId) {
    log.info(">>>>>>>>>>>>>>>..getIssue " + projectId);
    var domain=session.get("DOMAIN");

    if(projectId === undefined || projectId === null){
        projectId = 0;
        log.info("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");

    }

    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/project/"+projectId+"/issue";
    var data = {  };
    var issues = get(url, {} ,"json");
    return issues;
};

// "http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-1"
var getIssueById = function (issueKey, user) {
    log.info(">>>>>>>>>>>>>>>..getIssueById"+user);
    var domain=session.get("DOMAIN");
    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/issue/"+issueKey;
    var result = get(url, {} ,"json");
    var commentList = result.data.issueResponse.comments;
    for(var i in commentList){
        if(user == commentList[i].creator) {
            commentList[i].isowner=true
        } else
            commentList[i].isowner=false
    }

    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<000000000****-- "+stringify(result));

    return result;
};

//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/project/5/issue
var addIssue = function (projectId, jsonString){
    var LOGGED_IN_USER = session.get("LOGGED_IN_USER");
    var jsonObj = parse(jsonString);
    var domain=session.get("DOMAIN");
    jsonObj.reporter =  LOGGED_IN_USER;

    var proj = new Object();
    proj.issue=jsonObj;

    jsonString = stringify(proj);

    log.info("****************>>>> " + jsonString);
    var result;
        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/'+domain+'/project/'+projectId+'/issue';
        result = post(url, jsonString, {
            "Content-Type": "application/json"
        }, 'json');
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+stringify(result));

    return result;

}

//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-2
var editIssue = function (issueKey, jsonString){
    log.info(">>>>>>>>>>>>>>>aadddd..getIssueById " );
    var domain=session.get("DOMAIN");
    var LOGGED_IN_USER = session.get("LOGGED_IN_USER");
    var jsonObj = parse(jsonString);

    jsonObj.reporter =  LOGGED_IN_USER;

    var proj = new Object();
    proj.issue=jsonObj;

    jsonString = stringify(proj);


    log.info('jsonString '+jsonString);
    var result;

        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/'+domain+'/issue/'+issueKey;
        log.info("URL: " + url);
        result = post(url, jsonString, {
            "Content-Type": "application/json"
        }, 'json');
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result).data);

    return result;

}

//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/search
var searchIssue = function (searchType, searchValue) {
    var jsonObj = new Object();
    jsonObj.searchType = searchType;
    jsonObj.searchValue = searchValue;
    var domain=session.get("DOMAIN");
    var json = new Object();
    json.searchBean = jsonObj;
    //log.info("???????????????????? " + getProperty(ISSUE_TRACKER_URL));
    //log.info(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<< " + ;

    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/issue/search";
    var jsonString = stringify(json);
    log.info("INPUT PATA++++ " + jsonString);
    result = post(url, jsonString, {
        "Content-Type": "application/json"
    }, 'json');

    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result));

    return result;
};