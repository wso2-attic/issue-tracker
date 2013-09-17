
var site = config = require('/tracker.json');
var security =require("sso");

var isLogged = function(){
    var ssoRelyingParty = new security.SSORelyingParty(site.ssoConfiguration.issuer);
    var sessionId = session.getId();
    var isAuthenticated = ssoRelyingParty.isSessionAuthenticated(sessionId);
    if(isAuthenticated){
        return true;
    } else {
        include('/login.jag');
        return false;
    }
}


//})();

 /*var caramel;
 include('/jagg/jagg.jag');
 include('/jagg/config_reader.jag');

 var log = new Log(), configs = require('/tracker.json');



 var isLogged = function(){
     var userSSO = context.get(IS_LOGGED);

     caramel = require('caramel');
     context = caramel.configs().context;

     if (userSSO) {
        log.info("111111111111111111 " + context);
        //response.sendRedirect(context + '/home.jag');
        return true;
     } else {
        include('login.jag');
         return;
     }

 };
   */