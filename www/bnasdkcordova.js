var argscheck = require('cordova/argscheck');
var utils = require('cordova/utils');
var exec = require('cordova/exec');

var bnasdk = {
    executar: function (successCallback, errorCallback, options) {
        exec(function(success) { alert("OK"); }, function(success) { alert("deunao"); }, 'BNASdkCordova', 'go', []);
    }
};

module.exports = bnasdk;