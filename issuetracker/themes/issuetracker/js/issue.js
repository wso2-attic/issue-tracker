$().ready(function() {

			$('#saveMe').click(function() {

                var projectId = $("#projectId").attr('value');
                var pkey = $("#key").attr('value');
                var jsonObj = new Object();
                jsonObj.projectId=projectId;
                jsonObj.key=pkey;
                jsonObj.summary=$("#summary").attr('value');
                jsonObj.description= $("#description").attr('value');
                jsonObj.type=$("#type").attr('value');
                jsonObj.priority=$("#priority").attr('value');
                jsonObj.owner=$("#owner").attr('value');
                jsonObj.status=$("#status").attr('value');
                jsonObj.assignee=$("#assignee").attr('value');
                jsonObj.version=$('#version').val();
                jsonObj.severity=$("#severity").attr('value');


                var proj = new Object();
                proj.issue=jsonObj;

                var myString = JSON.stringify(proj);
                var isSuccess = false;
                var message = "";
                $.ajax({
                    type: 'POST',
                    url: "save.jag",
                    data: {
                        action:"addIssue",
                        jsonobj:myString,
                        id:projectId

                    },
                    success: function(result){
                        isSuccess = result.data.responseBean.success;

                        if(isSuccess)  {
                            //alert("Data successfully inserted");
                            window.location.href = "get.jag?pkey="+pkey;
                        } else {
                            alert(result.data.responseBean.message);
                        }

                    },
                    dataType: 'json',
                    async:false
                });


		});

    $('#editMe').click(function() {
        var key = $("#key").attr('value');

        var jsonObj = new Object();
        jsonObj.projectId=$("#projectId").attr('value');
        jsonObj.key=key;
        jsonObj.summary=$("#summary").attr('value');
        jsonObj.description= $("#description").attr('value');
        jsonObj.type=$("#type").attr('value');
        jsonObj.priority=$("#priority").attr('value');
        jsonObj.owner=$("#owner").attr('value');
        jsonObj.status=$("#status").attr('value');
        jsonObj.assignee=$("#assignee").attr('value');
        jsonObj.version=$("#version").attr('value');
        jsonObj.severity=$("#severity").attr('value');


        var proj = new Object();
        proj.issue=jsonObj;

        var myString = JSON.stringify(proj);
        var isSuccess = false;

        $.ajax({
            type: 'POST',
            url: "save.jag",
            data: {
                action:"editIssue",
                jsonobj:myString,
                ukey: key

            },
            success: function(result){
                isSuccess = result.data.responseBean.success;
            },
            dataType: 'json',
            async:false
        });
        if(isSuccess)  {
            //alert("Data successfully updated");
            window.location.href = "getAll.jag";
        }

    });


    $('#commentEdit').click(function() {

        var key = $("#ukey").attr('value');
        var id =  $("#comment_id").attr('value');
        var owner =  $("#owner").attr('value');
        var jsonObj = new Object();
        jsonObj.commentDescription=$("#commentpopup").attr('value');
        jsonObj.creator=owner;  // TODO '
        jsonObj.issueId=id;

        var proj = new Object();
        proj.comment=jsonObj;

        var myString = JSON.stringify(proj);
        var isSuccess = false;
        $.ajax({
            type: 'POST',
            url: "../comment/save.jag",
            data: {
                action:"editComment",
                jsonobj:myString,
                ukey: key,
                id:id,
                creator:owner //TODO

            },
            success: function(result){
                isSuccess = result.data.responseBean.success;
            },
            dataType: 'json',
            async:false
        });
        if(isSuccess)  {
            //alert("Data successfully updated");
            window.location.href = "get.jag?pkey="+key;
        }

    });

    $('#commentAdd').click(function() {

        var key = $("#ukey").attr('value');
        var jsonObj = new Object();
        jsonObj.commentDescription=$("#commentVal").attr('value');
        jsonObj.creator="nihanth";  // TODO

        var proj = new Object();
        proj.comment=jsonObj;

        var myString = JSON.stringify(proj);
        var isSuccess = false;

        $.ajax({
            type: 'POST',
            url: "../comment/save.jag",
            data: {
                action:"addComment",
                jsonobj:myString,
                ukey: key,
                creator:"nihanth" //TODO

            },
            success: function(result){
                isSuccess = result.data.responseBean.success;
            },
            dataType: 'json',
            async:false
        });
        if(isSuccess)  {
            //alert("Data successfully updated");
            window.location.href = "get.jag?pkey="+key;

        }

    });

    $( "#projectId" )
        .change(function () {
            $('#version').find('option').remove();

            $.ajax({
                type: 'GET',
                url: "getProjectVersion.jag",
                data: {
                    pid:this.value
                },
                success: function(result){
                   $.each(result.version, function(i, obj) {
                       $('#version')
                           .append($("<option></option>")
                               .attr("value",obj.projectVersionId)
                               .text(obj.projectVersion));

                    });
                },
                dataType: 'json',
                async:false
            });




            /*url = caramel.url('getProjectVersion.jag');
            caramel.data({
                body : ['projects', 'pagination']
            }, {
                url : url,
                success : function(data, status, xhr) {
                   alert("dddd");
                }
            });*/

        });



})

function deleteComment(id){

    var r=confirm("Do you want to delete comment?");
    if (r==true)
    {
        var key = $("#ukey").attr('value');
        $.ajax({
            type: 'POST',
            url: "../comment/save.jag",
            data: {
                action:"deleteComment",
                ukey: key,
                id:id,
                creator:"nihanth"

            },
            success: function(result){
                isSuccess = result.data.responseBean.success;
            },
            dataType: 'json',
            async:false
        });
        if(isSuccess)  {
            //alert("Data successfully updated");
            window.location.href = "get.jag?pkey="+key;
        }
    }



}