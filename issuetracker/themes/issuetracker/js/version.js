$().ready(function() {

			$('#saveMe').click(function() {

                var projectId = $("#projectId").attr('value');
                var version = $("#version").attr('value');
                var jsonObj = new Object();
                jsonObj.projectVersion=version;
                jsonObj.projectId=projectId;

                var proj = new Object();
                proj.version=jsonObj;

                var myString = JSON.stringify(proj);
                var isSuccess = false;
                var message = "";
                $.ajax({
                    type: 'POST',
                    url: "save.jag",
                    data: {
                        action:"addVersion",
                        jsonobj:myString,
                        id:projectId

                    },
                    success: function(result){
                        isSuccess = result.data.responseBean.success;

                        if(isSuccess)  {
                            alert("Version successfully inserted");
                            //window.location.href = "get.jag?pkey="+pkey;
                        } else {
                            alert(result.data.responseBean.message);
                        }

                    },
                    dataType: 'json',
                    async:false
                });


		});

});