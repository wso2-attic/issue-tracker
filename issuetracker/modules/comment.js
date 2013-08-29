var log = new Log();


//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/IS-2/comment
var addComment = function (issueKey, inputPara){

    log.info("****************");
    var result;
    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/issue/"+issueKey+"/comment";
                            log.info(url);
    log.info(inputPara);
    result = post(url, inputPara, {
        "Content-Type": "application/json"
    }, 'json');
    return result;

}



// http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/IS-2/comment/7
var editComment = function (issueKey, commentId, inputPara){
    log.info(">>>>>>>>>>>>>>>..getIssueById " );

    log.info('inputPara '+inputPara);
    var result;

    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/issue/"+issueKey+"/comment/"+commentId;

    result = post(url, inputPara, {
        "Content-Type": "application/json"
    }, 'json');
    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result));

    return result;

}


//http://10.100.0.120:9768/issuetracker-1.0.0/services/t/wso2.com/issue/IS-2/comment/7
var deleteComment = function (issueKey, commentId){

    log.info("****************");
    var result;
    var url  = "http://10.100.0.120:9765/issuetracker-1.0.0/services/t/wso2.com/issue/"+issueKey+"/comment/"+commentId;
    log.info(url);
    var data = {};
    var headers = {};
    result = del(url, data, 'json');
    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aa "+stringify(result));

    return result;





}