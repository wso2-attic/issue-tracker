var log = new Log();


//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/project/2/issue
var getAllIssue = function (projectId) {
    log.info(">>>>>>>>>>>>>>>..getIssue " + projectId);
    if(projectId === undefined || projectId === null){
        projectId = 0;
        log.info("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");

    }

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project/"+projectId+"/issue";
    var data = {  };
    var issues = get(url, {} ,"json");
    return issues;
};

// "http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/IS-1"
var getIssueById = function (issueKey) {
    log.info(">>>>>>>>>>>>>>>..getIssueById"+issueKey);

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/issue/"+issueKey;
    var result = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<000000000 "+stringify(result));


    return result;
};

//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/project/5/issue
var addIssue = function (projectId, inputPara){

    log.info("****************");
    var result;
        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project/'+projectId+'/issue';
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+stringify(result));

    return result;

}

//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/IS-2
var editIssue = function (issueKey, inputPara){
    log.info(">>>>>>>>>>>>>>>..getIssueById " );

    log.info('inputPara '+inputPara);
    var result;

        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/issue/'+issueKey;
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result).data);

    return result;

}