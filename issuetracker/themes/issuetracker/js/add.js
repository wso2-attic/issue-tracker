$().ready(function() {

			$('#saveMe').click(function() {
                var jsonObj = new Object();
                jsonObj.name=$("#projectName").attr('value');
                jsonObj.owner=$("#owner").attr('value');
                jsonObj.description=$("#description").attr('value');

                var proj = new Object();
                proj.project=jsonObj;

                var myString = JSON.stringify(proj);
                var isSuccess = false;

                $.ajax({
                    type: 'POST',
                    url: "save.jag",
                    data: {
                        action:"addProject",
                        jsonobj:myString

                    },
                    success: function(result){
                        isSuccess = result.data.responseBean.success;
                    },
                    dataType: 'json',
                    async:false
                });
                if(isSuccess)  {
                    //alert("Data successfully inserted");
                    window.location.href = "get.jag";
                }

		});

    $('#editMe').click(function() {
        var jsonObj = new Object();
        jsonObj.name=$("#projectName").attr('value');
        jsonObj.owner=$("#owner").attr('value');
        jsonObj.description=$("#description").attr('value');
        jsonObj.organizationId= $("#organizationId").attr('value');
        var proj = new Object();
        proj.project=jsonObj;

        var myString = JSON.stringify(proj);
        var isSuccess = false;

        $.ajax({
            type: 'POST',
            url: "save.jag",
            data: {
                action:"editProject",
                jsonobj:myString,
                projectId: $("#projectId").attr('value')

            },
            success: function(result){
                isSuccess = result.data.responseBean.success;
            },
            dataType: 'json',
            async:false
        });
        if(isSuccess)  {
            //alert("Data successfully updated");
            window.location.href = "get.jag";
        }

    });


})
