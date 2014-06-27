var adminCtl = angular.module('AdminCtl', ['ngResource']);

/**
 * Sets up the application.
 */
adminCtl.run( function($rootScope)
{
    $rootScope.NEW_ID = -1;
    $rootScope.STATE_NEW = 'new';
    $rootScope.STATE_EDIT = 'edit';

    $rootScope.isPresent = function(variable)
    {
        return variable !== null && typeof variable !== 'undefined';
    };
});

/**
 * Controller for the menu.
 */
adminCtl.controller("MenuCtl", function($rootScope, $location)
{
    $rootScope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
});


/**
 * Controller for Decrypt page
 */
adminCtl.controller('DecryptCtl', function($scope, $rootScope, $http, $resource, $filter, $timeout) {

    $scope.getPlaceholderText = function(forInputItem)
    {
        if(forInputItem === 'decrypt')
            return "Type text to be decryped";
        if(forInputItem === 'decrypted')
            return "The decrypted result will be placed here";
        return "";
    };

    $scope.submit = function() {
        $http({
                url: '/api/decrypt',
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify({
                    text: $scope.decrypt.text
                })
            }
        ).success(function(data) {
                if(data.decrypted != null) {
                    $scope.decrypt.decrypted = data.decrypted
                } else {
                    $scope.decrypt.decrypted = data.error
                }
            }).error(function() {
                alert("An unknown error occurred. Contact your administrator.");
            });
    };
});

/**
 * Controller for the Encrypt page
 */
adminCtl.controller('EncryptCtl', function($scope, $rootScope, $http, $resource, $filter, $timeout)
{
    $scope.getPlaceholderText = function(forInputItem)
    {
        if(forInputItem === 'encrypt')
            return "Type text to be encrypted";
        if(forInputItem === 'encrypted')
            return "The encrypted result will be placed here";
        return "";
    };

    $scope.submit = function() {
        $http({
                url: '/api/encrypt',
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify({
                    text: $scope.encrypt.text
                })
            }
        ).success(function(data) {
                if(data.encrypted != null) {
                    $scope.encrypt.encrypted = data.encrypted
                } else {
                    $scope.encrypt.encrypted = data.error
                }
            }).error(function() {
                alert("An unknown error occurred. Contact your administrator.");
            });
    };
});