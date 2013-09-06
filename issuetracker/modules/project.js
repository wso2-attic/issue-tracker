var log = new Log();


var getProject = function () {
    log.info(">>>>>>>>>>>>>>>..getProject");
    var domain=session.get("DOMAIN");
    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/project";
    var data = {  };
    var projects = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1 "+projects);

    return projects;
};


var getAllVersionOfProject1 = function ( projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById"+projectId);
    var domain=session.get("DOMAIN");

    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/project/"+projectId+"/version";
    //var project = get(url, {} ,"application/json");
    var project = get(url,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 2"+project);
    return project;

}

var getAllVersionOfProject = function ( projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById"+projectId);
    var domain=session.get("DOMAIN");
    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/project/"+projectId+"/version";
    //var project = get(url, {} ,"application/json");
    var project = get(url,"application/json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aa2 " +parse(project.data));
    //log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aa2 " +project.data.version);
    //log.info(parse(project.data).version);
    return parse(project.data);

}


//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/"+domain+"/project/2/version
var addVersion = function (projectId, inputPara){
    var domain=session.get("DOMAIN");
    log.info("**************** " +projectId);
    log.info(inputPara);

    var result;
    var url = session.get("ISSUE_TRACKER_URL")+domain+'/project/'+projectId+'/version';
    result = post(url, inputPara, {
        "Content-Type": "application/json"
    }, 'json');
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+stringify(result));

    return result;

}



var getProjectById = function (projectId) {
    log.info(">>>>>>>>>>>>>>>..getProjectById"+projectId);
    var domain=session.get("DOMAIN");
    var url  = session.get("ISSUE_TRACKER_URL")+domain+"/project/"+projectId;
    var project = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
    return project;
};

var addProject = function (inputPara){
    var domain=session.get("DOMAIN");
    var result;
        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/"+domain+"/project';
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
    return result;

}


var editProject = function (inputPara, projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById " + projectId);
    var domain=session.get("DOMAIN");
    log.info('inputPara '+inputPara);
    var result;

        var url = session.get("ISSUE_TRACKER_URL")+domain+'/project/'+projectId;
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result).data);

    return result;

}





