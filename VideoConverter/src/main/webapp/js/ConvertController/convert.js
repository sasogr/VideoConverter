/**
 * Holds ConvertController methods.
 */
angular.module('videoConverterApp').controller('ConvertController', ['$scope', '$http', '$log', function ($scope, $http, $log) {
	$scope.getCommands = function () {
		$http.get(
	            'rest/convert/getcommands'
	        )
	        .success(function (data, status) {
	            if (status == 200) {
	                $scope.commands = data;
	            }
	        })
	        .error(function (data, status) {
	            $log.info("REST GET: /rest/convert/getcommands -- request failed");
	            alert("Ooops! It seems we have problem in our application. Please contact the administrator!");
	        });
		
	}
	
	$scope.commandChange = function() {		
		$http.post(
				'rest/convert/getoptions/' + $scope.selectedCommand.id
		)
		.success(function (data, status) {
            if (status == 200) {
                $scope.options = data;
            }
        })
        .error(function (data, status) {
            $log.info("REST POST: rest/convert/getoptions -- request failed");
            alert("Ooops! It seems we have problem in our application. Please contact the administrator!");
        });
	}
	
	// On module load, always run the functions below.
	$scope.getCommands();
}]);