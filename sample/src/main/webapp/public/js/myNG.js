angular.module('myApp', [ 'ngResource' ]).
factory('Car', function($resource) {
	return $resource('cars/:id', {
		id : '@id'
	});
});

function CarCtrl($scope, Car) {
	angular.extend($scope, {
		
		cars : Car.query(),
		newCar : new Car(),
		
		create : function() {
			this.newCar.$save(function() {
				$scope.cars = Car.query();
			});
		},

		remove : function(car) {
			car.$remove(function() {
				$scope.cars = Car.query();
			});
		}
		
	});
};

