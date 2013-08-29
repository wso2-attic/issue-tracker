var log = new Log();


var getProject = function () {
    log.info(">>>>>>>>>>>>>>>..getProject");
    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project";
    var data = {  };
    var projects = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1 "+projects);

    return projects;
};


var getAllVersionOfProject1 = function ( projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById"+projectId);

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project/"+projectId+"/version";
    //var project = get(url, {} ,"application/json");
    var project = get(url,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 2"+project);
    return project;

}

var getAllVersionOfProject = function ( projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById"+projectId);

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project/"+projectId+"/version";
    //var project = get(url, {} ,"application/json");
    var project = get(url,"application/json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aa2 " +parse(project.data));
    //log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< aa2 " +project.data.version);
    //log.info(parse(project.data).version);
    return parse(project.data);

}



var getProjectById = function (projectId) {
    log.info(">>>>>>>>>>>>>>>..getProjectById"+projectId);

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project/"+projectId;
    var project = get(url, {} ,"json");
    log.info("project>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
    return project;
};

var addProject = function (inputPara){
    var result;
        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project';
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
    return result;

}


var editProject = function (inputPara, projectId){
    log.info(">>>>>>>>>>>>>>>..getProjectById " + projectId);

    log.info('inputPara '+inputPara);
    var result;

        var url = 'http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/project/'+projectId;
        result = post(url, inputPara, {
            "Content-Type": "application/json"
        }, 'json');
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result).data);

    return result;

}





