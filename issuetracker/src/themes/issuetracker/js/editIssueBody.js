
$(document).ready(function() {
    $("#type").select2();
    $("#priority").select2();
    $("#issue_status").select2();
    $("#severity").select2();
    $("#projectKey").select2();
    $("#version").select2();


    var type = document.getElementById('type');
    var project = document.getElementById('projectKey');
    var priority = document.getElementById('priority');
    var issue_status = document.getElementById('issue_status');
    var severity = document.getElementById('severity');
    var version= document.getElementById('version');

    type.value = "{{issue.type}}";
    project.value = "{{projectKey}}";
    priority.value = "{{issue.priority}}";
    issue_status.value = "{{issue.status}}";
    severity.value = "{{issue.severity}}";
    version.value = "{{issue.versionId}}";

});