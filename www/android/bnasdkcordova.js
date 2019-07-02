var exec = cordova.require('cordova/exec'); // eslint-disable-line no-undef
var utils = require('cordova/utils');

var pluginToNativeWatchMap = {};

module.exports = {
    executar: function (success, error, args) {
        exec(function(success) { alert("OK"); }, function(success) { alert("deunao"); }, 'BNASdkCordova', 'go', []);
    }
};