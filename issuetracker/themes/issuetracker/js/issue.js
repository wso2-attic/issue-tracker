$().ready(function() {

			$('#saveMe').click(function() {

                var projectId = $("#projectId").attr('value');
                var projectName =  $("#projectId :selected").text();

                var pkey = $("#key").attr('value');
                var json = new Object();
                json.projectId=projectId;
                json.key=pkey;
                json.summary=$("#summary").attr('value');
                json.description= $("#description").attr('value');
                json.type=$("#type").attr('value');
                json.priority=$("#priority").attr('value');
                json.status=$("#status").attr('value');
                json.assignee=$("#assignee").attr('value');
                json.version=$('#version').val();
                json.severity=$("#severity").attr('value');
                json.projectName = projectName;

                //var proj = new Object();
                //proj.issue=json;

                var myString = JSON.stringify(json);

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
                        isSuccess = result.data;

                        window.location.href = "get.jag?pkey="+isSuccess;



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
        jsonObj.status=$("#status").attr('value');
        jsonObj.assignee=$("#assignee").attr('value');
        jsonObj.version=$("#version").attr('value');
        jsonObj.severity=$("#severity").attr('value');


        //var proj = new Object();
        //proj.issue=jsonObj;

        var myString = JSON.stringify(jsonObj);
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
       // alert('dddd');
        if(isSuccess)  {
           // alert("Data successfully updated");
            window.location.href = "index.jag";
        }

    });


    $('#commentEdit').click(function() {

        var key = $("#ukey").attr('value');
        var id =  $("#comment_id").attr('value');
        //var reporter =  session.get("LOGGED_IN_USER");
        var jsonObj = new Object();
        jsonObj.commentDescription=$("#commentpopup").attr('value');
        //jsonObj.creator=reporter;  // TODO '
        jsonObj.issueId=id;

        //var proj = new Object();
        //proj.comment=jsonObj;
        var myString = JSON.stringify(jsonObj);
        var isSuccess = false;
        $.ajax({
            type: 'POST',
            url: "../comment/save.jag",
            data: {
                action:"editComment",
                jsonobj:myString,
                ukey: key,
                id:id

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

        //var proj = new Object();
        //proj.comment=jsonObj;

        var myString = JSON.stringify(jsonObj);
        var isSuccess = false;


        $.ajax({
            type: 'POST',
            url: "../comment/save.jag",
            data: {
                action:"addComment",
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
                id:id

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