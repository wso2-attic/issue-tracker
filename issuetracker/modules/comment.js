var log = new Log();


//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-2/comment
var addComment = function (issueKey, jsonString){
    var LOGGED_IN_USER = session.get("LOGGED_IN_USER");
    log.info("::::::::::::::::::::"+LOGGED_IN_USER);

    var jsonObj = parse(jsonString);

    jsonObj.creator =  LOGGED_IN_USER;

    var proj = new Object();
    proj.comment=jsonObj;

    jsonString = stringify(proj);


     var domain=session.get("DOMAIN");

    log.info("****************AA " + jsonString);
    var result;
    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/issue/"+issueKey+"/comment";
                            log.info(url);
    log.info(jsonString);
    result = post(url, jsonString, {
        "Content-Type": "application/json"
    }, 'json');
    return result;

}



// http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-2/comment/7
var editComment = function (issueKey, commentId, jsonString){
    log.info(">>>>>>>>>>>>>>>..getIssueById " );
    var LOGGED_IN_USER = session.get("LOGGED_IN_USER");

    var jsonObj = parse(jsonString);

    jsonObj.creator =  LOGGED_IN_USER;

    var proj = new Object();
    proj.comment=jsonObj;

    jsonString = stringify(proj);

    var domain=session.get("DOMAIN");
    log.info('jsonString '+jsonString);
    var result;

    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/issue/"+issueKey+"/comment/"+commentId;

    result = post(url, jsonString, {
        "Content-Type": "application/json"
    }, 'json');
    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result));

    return result;

}


//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/issue/IS-2/comment/7
var deleteComment = function (issueKey, commentId){
    var domain=session.get("DOMAIN");
    log.info("****************");
    var result;
    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/issue/"+issueKey+"/comment/"+commentId;
    log.info(url);
    var data = {};
    var headers = {};
    result = del(url, data, 'json');
    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result));

    return result;





}