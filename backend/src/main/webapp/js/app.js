var app = angular.module('app',
	["ngMaterial", "ngRoute"]);

app.config(function($routeProvider, $locationProvider) {
	$locationProvider.hashPrefix('');

    $routeProvider
    .when("/", {
    	redirectTo:'/index'
    }).when("/index", {
        templateUrl: 'componetes/inicio.html'
    }).when("/exemplo", {
        templateUrl: 'componetes/exemplo.html'
    }).when("/alunos", {
        controller: 'AlunoController',
        templateUrl: 'componetes/alunos.html'
    }).when("/alunos/:id", {
        controller: 'AlunoController',
        templateUrl: 'componetes/alunos-create.html'
    }).when("/alunos/criar", {
        controller: 'AlunoController',
        templateUrl: 'componetes/alunos-create.html'
    }).when("/materias", {
        controller: 'MateriaController',
        templateUrl: 'componetes/materias-create.html'
    }).when("/materias/criar", {
        templateUrl: 'componetes/materias-create.html'
    }).otherwise({
        redirectTo: '/index'
    });;
});

