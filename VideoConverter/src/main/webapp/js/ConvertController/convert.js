/**
 * Holds ConvertController methods.
 */
angular.module('videoConverterApp').controller('ConvertController', ['$scope', '$http', '$log', function ($scope, $http, $log) {
	// Holds the main part of the command, i.e. ffprobe or ffmpeg.
	$scope.commandToExecute = '';
	// Holds the options for the previously selected command.
	$scope.optionsToExecute = '';
	
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
                // Update the command that needs to be executed.
                $scope.commandToExecute = $scope.selectedCommand.commandValue + ' ';
                // Remove all the previously selected options, because the newly selected command has another options.
                $scope.optionsToExecute = '';
            }
        })
        .error(function (data, status) {
            $log.info("REST POST: rest/convert/getoptions -- request failed");
            alert("Ooops! It seems we have problem in our application. Please contact the administrator!");
        });
	}
	
	$scope.addOption = function(selectedOption, optionValue) {
		// Add the options.
		$scope.optionsToExecute += selectedOption.commandValue;
		
		if(selectedOption.acceptInput == 1) {
			// If the option requires input, add the input to the option.
			$scope.optionsToExecute += '=' + optionValue + ' ';
		}
		else {
			// Otherwise, add empty space for further options.
			$scope.optionsToExecute += ' ';
		}
	}
	
	// On module load, always run the functions below.
	$scope.getCommands();
}]);