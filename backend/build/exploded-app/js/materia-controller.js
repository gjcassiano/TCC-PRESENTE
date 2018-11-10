
app.controller('MateriaController', ['AppService', '$scope', '$location','$mdPanel', '$mdDialog', '$routeParams',
	function(AppService, $scope, $location, $mdPanel, $mdDialog, $routeParams) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        AppService.GetRequest('GET','users/list').then(
            function(response) {
                console.log(response);
                $scope.professores = [];
                $scope.alunos = [];
                 response.data.items.forEach(function(entry) {
                       if(entry.userType == 1){
                           $scope.professores.push(entry);
                           //console.log(entry);
                       }else {
                           $scope.alunos.push(entry);
                       }

                   });
//                'materia.professor'
//                 if($routeParams.id){
//                    $scope.user = $scope.todos[$routeParams.id];
//
//                    console.log($scope.user);
//                }
            }
        );
    }
    $scope.updateList();



    $scope.showDialog = function(tag_input) {
      var position = $mdPanel.newPanelPosition()
          .absolute()
          .center();

      var config = {
        attachTo: angular.element(document.body),
        controller: function ctrl($scope) {

            $scope.saveTag = function(tag) {
                TagService.saveTag(tag).then(
                    function(response) {
                        ToastService.showToast("Tag salva.");
                        $mdPanel.close();
                    }
                );
            };
        },
        controllerAs: 'ctrl',
        disableParentScroll: this.disableParentScroll,
        templateUrl: 'static/dialog_tag.html',
        hasBackdrop: true,
        panelClass: 'demo-dialog-example',
        position: position,
        locals: {
          tag: tag_input
        },
        trapFocus: true,
        zIndex: 150,
        clickOutsideToClose: true,
        escapeToClose: true,
        focusOnOpen: true
      };

      var mdpanel = $mdPanel.open(config);
    };

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

//        $mdDialog.show(confirm).then(function() {
//          TagService.deleteTag(tag).then(
//                function(response) {
//                    $scope.updateList();
//                }
//            );
//            ToastService.showToast("Tag " + tag.name+ " deletada");
//        }, function() {});
    }

    $scope.onClickTag = function(index){
        $location.path('alunos/'  + index);
        //$scope.showDialog($scope.todos[index]);
    }

    $scope.navigate = function(path){
        console.log(path);
        $location.path(path);
    }
    $scope.criar = function(materia){
        var ma = materia;
        ma.professor = materia.professor.id;

        var alunosSelected = [];
        $scope.alunos.forEach(function(entry) {
            if(entry.selected){
                alunosSelected.push(entry.id);
            }
        });
        materia.alunos = alunosSelected;

        AppService.GetRequest('POST','materias/create', ma);
        console.log(alunosSelected);
    }

}]);

app.filter('findTag', function () {

    return function (input, text) {
        if (!angular.isString(text) || text === '') {
            return input;
        }
        return input.filter(function (item) {
            return  (item.description.toLocaleLowerCase().indexOf(text.toLocaleLowerCase())!=-1) || (item.name.indexOf(text) > -1);
        });
    };

});