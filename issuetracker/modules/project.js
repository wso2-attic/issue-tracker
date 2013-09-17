var log = new Log();
include('/jagg/jagg.jag');
var url_prefix = context.get(ISSUE_TRACKER_URL)+context.get(DOMAIN)+"/project/";

//http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project            :: checked
var getProjectsOfDomain = function () {
    log.info("========================================================================================= getProjectsOfDomain " );

    log.info(">>>>>>>>>>>>>>>..getProject " );
    var url  = url_prefix;
    var data = {  };
    var projects = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1 "+stringify(projects.data.project));
    log.info("========================================================================================= getProjectsOfDomain " );

    return projects.data.project;
};

var getAllProject = function () {
    log.info(">>>>>>>>>>>>>>>..getAllProject");
    var domain=session.get("DOMAIN");
    var url  = session.get(ISSUE_TRACKER_URL)+domain+"/project";
    var data = {  };
    var projects = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1 "+projects);

    return projects;
};



var getAllVersionOfProject1 = function ( projectKey){
    log.info("========================================================================================= getAllVersionOfProject1 " );


    var url  = url_prefix+projectKey+"/version";
    //var project = get(url, {} ,"application/json");
    var project = get(url,{},"json");

    log.info(stringify(project.data.version));


    log.info("========================================================================================= getAllVersionOfProject1 " );

    return project.data.version;


}

var getAllVersionOfProject = function ( projectKey){
    log.info("========================================================================================= getAllVersionOfProject " );


    var url  = url_prefix+projectKey+"/version";
    //var project = get(url, {} ,"application/json");
    var project = get(url,{},"json");
    log.info(stringify(project.data.version));
    log.info("========================================================================================= getAllVersionOfProject " );

    return project.data.version;

}


//http://10.100.0.120:9768/issuetracker-1.0.0/services/tenant/wso2.com/project/2/version
var addVersion = function (projectId, inputPara){

    log.info("**************** " +projectId);
    log.info(inputPara);

    var result;
    var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project/'+projectId+'/version';
    result = post(url, inputPara, {
        "Content-Type": "application/json"
    }, 'json');
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+stringify(result));

    return result;

}


//  http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project/"+projectKey;
var getProjectByKey = function (projectKey) {
    log.info("========================================================================================= getProjectByKey " );

    var url  = url_prefix+projectKey;
    var project = get(url, {} ,"json");
    log.info(stringify(project.data.project));
    log.info("========================================================================================= getProjectByKey " );

    return project.data.project;
};

var addProject = function (inputPara){
    var result;
        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project';
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
    return result;

}


var editProject = function (inputPara, projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById " + projectId);

    log.info('inputPara '+inputPara);
    var result;

        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/tenant/wso2.com/project/'+projectId;
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result).data);

    return result;

}





