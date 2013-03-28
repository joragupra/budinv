function BudgetCtrl($scope, $routeParams) {
    $scope.year = $routeParams.year;

    //TODO - use service to retrieve this data
    $scope.types = [
        {code: 'regular', name:'regular expenses', amount: '1100', percentage: '60', color1: 'error', color2: 'danger'},
        {code: 'irregular', name:'irregular expenses', amount: '200', percentage: '25', color1: 'warning', color2: 'warning'},
        {code: 'variable', name:'variable expenses', amount: '90', percentage: '15', color1: 'success', color2: 'success'}
    ];
}

function BudgetedExpensesCtrl($scope, $routeParams) {
    $scope.year = $routeParams.year;

    $scope.expenseType = $routeParams.type;

    //TODO - obtain calling service
    $scope.nextExpenseType = function(expenseType) {
        if(expenseType=='regular') return 'irregular';
        if(expenseType=='irregular') return 'variable';
        if(expenseType=='variable') return 'regular';
    }

    //TODO - obtain calling service
    $scope.previousExpenseType = function(expenseType) {
        if(expenseType=='regular') return 'variable';
        if(expenseType=='irregular') return 'regular';
        if(expenseType=='variable') return 'irregular';
    }
}
