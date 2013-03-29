'use strict';

angular.module('myApp', [ 'ngResource' ]).
factory('Car', function($resource) {
	return $resource('cars/:id', {
		id : '@id'
	});
});

function CarCtrl($scope, Car) {
    
    function inputsEnabled(boo){
        var elems = document.newCarForm.elements;
        for(var e in elems){
            elems[e].disabled=!boo;
        }
    }
    
	angular.extend($scope, {
		
		cars : Car.query(),
		newCar : new Car(),
		
		create : function() {
            inputsEnabled(false);
			this.newCar.$save(function() {
                $scope.cars.push($scope.newCar);
                $scope.newCar = new Car();
                inputsEnabled(true);
			});
		},

		remove : function(car) {
            var id = car.id;
			car.$remove(function() {
                for (var i = 0; i < $scope.cars.length; ++i) {
                    if ($scope.cars[i].id === id) {
                        $scope.cars.splice(i--, 1);
                    }
                }
			});
		},
        
        reload : function(){
            this.cars=Car.query();
        }
		
	});
};

