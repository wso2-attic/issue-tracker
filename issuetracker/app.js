var caramel = require('caramel');

caramel.configs({
    context: '/issuetracker',
    cache: true,
    negotiation: true,
    themer: function () {
        return 'issuetracker';
    }
});
