var adminApp = angular.module('admin', ['ngRoute', 'AdminCtl']);

//Configure routes in the love-admin app
adminApp.config(function($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl:    'home.html'
        })
        .when("/encrypt", {
            controller:     'EncryptCtl',
            templateUrl:    'encrypt.html'
        })
        .when("/decrypt", {
            controller:     'DecryptCtl',
            templateUrl:    'decrypt.html'
        })
        .otherwise({
            redirectTo:     '/'
        });
});