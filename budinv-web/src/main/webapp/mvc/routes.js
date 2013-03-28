angular.module('budinv', []).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/budget/:year/:type', {templateUrl: 'views/budgeted_expenses.html', controller: BudgetedExpensesCtrl}).
            when('/budget/:year', {templateUrl: 'views/budget.html',   controller: BudgetCtrl}).
            when('/', {templateUrl: 'views/initial.html'}).
            otherwise({redirectTo: '/'});
    }]);
